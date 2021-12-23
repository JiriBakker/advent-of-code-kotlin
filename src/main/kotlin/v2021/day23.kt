package v2021

import util.priorityQueueBy

private fun List<String>.parsePositions() =
    listOf(
        this[3][3],
        this[2][3],
        this[3][5],
        this[2][5],
        this[3][7],
        this[2][7],
        this[3][9],
        this[2][9],
        "..x.x.x.x.."
    ).joinToString("")

private fun String.fold() =
    listOf(
        this[0],
        'D', 'D',
        this[1],

        this[2],
        'B', 'C',
        this[3],

        this[4],
        'A', 'B',
        this[5],

        this[6],
        'C', 'A',
        this[7],

        "..x.x.x.x.."
    ).joinToString("")

private val costs =
    mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)

private val allowedToMoveIntoChars = setOf('.', 'x')

private data class BurrowProperties(val roomSize: Int) {
    private val targetRoomPositions =
        ('A' .. 'D').associateWith { amphipod ->
            val amphipodStartIndex = (amphipod - 'A') * roomSize
            amphipodStartIndex until (amphipodStartIndex + roomSize)
        }

    private val hallWayPositions =
        (4 * roomSize) until (4 * roomSize + 11)

    fun isHallwayPos(pos: Int) =
        pos in hallWayPositions

    private val leavingRoomMoves =
        (0 until 4).flatMap { roomNr ->
            val roomStartIndex = roomNr * roomSize
            (1 until roomSize)
                .map { roomStartIndex + it }
                .plus(roomSize * 4 + 2 + (roomNr * 2))
        }

    private val enterRoomMoves =
        leavingRoomMoves.withIndex().associate { (from, to) ->
            to to from
        }

    fun getTargetRoomPositions(amphipod: Char) =
        targetRoomPositions[amphipod]!!

    fun getMoveHeadingOutOfRoom(pos: Int) =
        when {
            !isHallwayPos(pos) -> leavingRoomMoves[pos]
            else -> null
        }

    fun getMoveEnteringTargetRoom(amphipod: Char, pos: Int, isRoomOpen: Boolean) =
        when {
            !isRoomOpen -> null
            !enterRoomMoves.containsKey(pos) -> null
            enterRoomMoves[pos] in getTargetRoomPositions(amphipod) -> enterRoomMoves[pos]
            else -> null
        }

    fun getMoveInHallway(pos: Int) =
        when {
            !isHallwayPos(pos) -> emptyList()
            pos == hallWayPositions.first -> listOf(pos + 1)
            pos == hallWayPositions.last -> listOf(pos - 1)
            else -> listOf(pos - 1, pos + 1)
        }
}

private fun String.update(vararg updates: Pair<Int, Char>): String {
    val updated = toMutableList()
    updates.forEach { (index, newChar) ->
        updated[index] = newChar
    }
    return updated.joinToString("")
}

private data class BurrowState(
    val positions: String,
    val energySpent: Long,
    private val burrow: BurrowProperties
) {

    private fun isTargetRoomOpen(amphipod: Char) =
        burrow.getTargetRoomPositions(amphipod).all { positions[it] == '.' || positions[it] == amphipod }

    private fun isLowestAvailableSpotInTargetRoom(amphipod: Char, pos: Int) =
        (burrow.getTargetRoomPositions(amphipod).first until pos).all { positions[it] == amphipod }

    private fun isAvailablePos(pos: Int) =
        positions[pos] == '.'

    private fun canMoveInto(pos: Int) =
        positions[pos] == '.' || positions[pos] == 'x'

    fun getValidNextStates(amphipod: Char, startPos: Int): List<BurrowState> {
        if (positions[startPos] != amphipod) return emptyList()

        val startedInHallway = burrow.isHallwayPos(startPos)

        fun isEnteringHallway(pos: Int) =
            !startedInHallway && burrow.isHallwayPos(pos)

        fun isEnteringRoom(pos: Int) =
            startedInHallway && !burrow.isHallwayPos(pos)

        fun isValidDestination(pos: Int): Boolean {
            val isAvailable = isAvailablePos(pos)
            val isEnteringHallway = isEnteringHallway(pos)
            val isEnteringRoom = isEnteringRoom(pos)
            val isAmphipodsTargetRoom = pos in burrow.getTargetRoomPositions(amphipod)
            val isTargetRoomOpen = isTargetRoomOpen(amphipod)
            val isLowestSpotInTargetRoom = isLowestAvailableSpotInTargetRoom(amphipod, pos)

            return when {
                !isAvailable      -> false
                isEnteringHallway -> true
                isEnteringRoom    -> when {
                    !isAmphipodsTargetRoom   -> false
                    !isTargetRoomOpen        -> false
                    isLowestSpotInTargetRoom -> true
                    else                     -> false
                }
                else -> false
            }
        }

        val exploredPositions = mutableSetOf<Int>()

        fun explore(pos: Int, costSoFar: Long): Map<Int, Long> {
            if (exploredPositions.contains(pos)) return mapOf()
            exploredPositions.add(pos)

            val cur = mapOf(pos to costSoFar)

            val validMoves =
                listOf(
                    listOfNotNull(burrow.getMoveHeadingOutOfRoom(pos)),
                    listOfNotNull(burrow.getMoveEnteringTargetRoom(amphipod, pos, isTargetRoomOpen(amphipod))),
                    burrow.getMoveInHallway(pos)
                ).flatten()

            return validMoves
                .filter { pos -> !exploredPositions.contains(pos) && canMoveInto(pos) }
                .fold(cur) { acc, nextPos ->
                    val destinations = explore(nextPos, costSoFar + costs[amphipod]!!)
                    acc.plus(destinations)
                }
        }

        val possibleDestinations = explore(startPos, 0)

        return possibleDestinations
            .filter { (destination) -> isValidDestination(destination) }
            .map { (destination, cost) ->
                val newPositions =
                    positions.update(
                        startPos to '.',
                        destination to amphipod
                    )

                BurrowState(newPositions, this.energySpent + cost, burrow)
            }
    }

    private fun isAmphipodFinished(amphipod: Char, pos: Int) =
        pos in burrow.getTargetRoomPositions(amphipod)
            && isTargetRoomOpen(amphipod)

    fun getUnfinishedAmphipods() =
        positions
            .withIndex()
            .filter { (pos, char) ->
                char in setOf('A', 'B', 'C', 'D')
                    && !isAmphipodFinished(char, pos)
            }
            .map { (pos, char) -> char to pos }
}

private fun findPath(initialPositions: String, target: String, roomSize: Int): Long {
    val initialState = BurrowState(initialPositions, 0, BurrowProperties(roomSize))

    val checked = mutableSetOf<String>()
    val toCheck = priorityQueueBy<BurrowState> { it.energySpent }

    toCheck.add(initialState)

    while (toCheck.isNotEmpty()) {
        val state = toCheck.poll()

        if (!checked.add(state.positions)) {
            continue
        }
        if (state.positions == target) {
            return state.energySpent
        }

        val nextStates =
            state
                .getUnfinishedAmphipods()
                .flatMap { (amphipod, pos) ->
                    state.getValidNextStates(amphipod, pos)
                }
                .filter { !checked.contains(it.positions) }

        toCheck.addAll(nextStates)
    }

    throw Error("No path found")
}

fun day23a(input: List<String>) =
    findPath(
        input.parsePositions(),
        "AABBCCDD..x.x.x.x..",
        roomSize = 2
    )

fun day23b(input: List<String>) =
    findPath(
        input.parsePositions().fold(),
        "AAAABBBBCCCCDDDD..x.x.x.x..",
        roomSize = 4
    )