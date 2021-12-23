package v2021

import util.DoNotAutoExecute
import util.priorityQueueBy

private fun List<String>.parseInitialState() =
    BurrowState(
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
        ).joinToString(""),
        0
    )

private fun List<String>.parseExtendedInitialState() =
    ExtendedBurrowState(
        listOf(
            this[3][3],
            'D', 'D',
            this[2][3],

            this[3][5],
            'B', 'C',
            this[2][5],

            this[3][7],
            'A', 'B',
            this[2][7],

            this[3][9],
            'C', 'A',
            this[2][9],

            "..x.x.x.x.."
        ).joinToString(""),
        0
    )

private val targetRooms =
    mapOf(
        'A' to 0..1,
        'B' to 2..3,
        'C' to 4..5,
        'D' to 6..7
    )

private val extendedTargetRooms =
    mapOf(
        'A' to 0..3,
        'B' to 4..7,
        'C' to 8..11,
        'D' to 12..15
    )

private val positionLinks =
    mapOf(
        0 to 1,
        1 to 10,
        2 to 3,
        3 to 12,
        4 to 5,
        5 to 14,
        6 to 7,
        7 to 16,
        8 to 9,
        9 to 10,
        10 to 11,
        11 to 12,
        12 to 13,
        13 to 14,
        14 to 15,
        15 to 16,
        16 to 17,
        17 to 18
    ).flatMap { (a, b) ->
        listOf(a to b, b to a)
    }.groupBy { it.first }

private val extendedTargetRoomExitMoves =
    mapOf(
        0 to 1,
        1 to 2,
        2 to 3,
        3 to 18,

        4 to 5,
        5 to 6,
        6 to 7,
        7 to 20,

        8 to 9,
        9 to 10,
        10 to 11,
        11 to 22,

        12 to 13,
        13 to 14,
        14 to 15,
        15 to 24
    )

private val extendedHallwayMoves =
    listOf(
        16 to 17,
        17 to 16,
        17 to 18,
        18 to 17,
        18 to 19,
        19 to 20,
        19 to 18,
        20 to 21,
        20 to 19,
        21 to 22,
        21 to 20,
        22 to 23,
        22 to 21,
        23 to 24,
        23 to 22,
        24 to 25,
        24 to 23,
        25 to 26,
        25 to 24,
        26 to 25
    ).groupBy { it.first }

private val extendedValidMovesIntoTargetRooms =
    mapOf(
        'A' to mapOf(
            18 to 3,
            3 to 2,
            2 to 1,
            1 to 0
        ),
        'B' to mapOf(
            20 to 7,
            7 to 6,
            6 to 5,
            5 to 4
        ),
        'C' to mapOf(
            22 to 11,
            11 to 10,
            10 to 9,
            9 to 8
        ),
        'D' to mapOf(
            24 to 15,
            15 to 14,
            14 to 13,
            13 to 12
        )
    )


private val validHallwayPositions =
    setOf(8, 9, 11, 13, 15, 17, 18)

private val extendedValidHallwayPositions =
    setOf(16, 17, 19, 21, 23, 25, 26)


private val costs =
    mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)

private val allowedToMoveIntoChars = setOf('.', 'x')

private data class BurrowState(val positions: String, val energyCost: Long) {
    val hash = positions

    val isFinished get() = this.positions == "AABBCCDD..x.x.x.x.."

    fun getValidNextStates(amphipod: Char, from: Int): List<BurrowState> {
        if (positions[from] != amphipod) return emptyList()

        val checked = mutableSetOf<Int>()

        fun explore(pos: Int, costSoFar: Long): Map<Int, Long> {
            if (checked.contains(pos)) return mapOf()
            checked.add(pos)

            val cur = mapOf(pos to costSoFar)

            return cur.plus(
                positionLinks[pos]!!
                    .map { it.second }
                    .filter { !checked.contains(it) && positions[it] in allowedToMoveIntoChars }
                    .fold(emptyMap()) { acc, nextPos ->
                        val destinations = explore(nextPos, costSoFar + costs[amphipod]!!)
                        acc.plus(destinations)
                    }
            )
        }


        val destinations = explore(from, 0)

        return destinations.filter { (pos) ->
            val isHallwayPos = pos in validHallwayPositions
            val isValidTargetPos = pos in targetRooms[amphipod]!!
            isHallwayPos || isValidTargetPos
        }.map { (to, cost) ->
            val newPositions = positions.toMutableList()
            newPositions[from] = '.'
            newPositions[to] = amphipod
            BurrowState(newPositions.joinToString(""), this.energyCost + cost)
        }
    }

    fun getMovableAmphipodPositions(): List<Pair<Char, Int>> {
        return positions.mapIndexedNotNull { pos, char ->
            val isAmphipod = char in setOf('A', 'B', 'C', 'D')

            if (!isAmphipod) {
                null
            } else {
                val isPosFurthestTargetPos = pos == targetRooms[char]!!.first
                val isPosClosestTargetPos = pos == targetRooms[char]!!.last
                val isFurthestTagetPosFulfilled = positions[targetRooms[char]!!.first] == char

                if (!isPosFurthestTargetPos && !(isPosClosestTargetPos && isFurthestTagetPosFulfilled)) {
                    char to pos
                } else {
                    null
                }
            }
        }
    }
}

fun day23a(input: List<String>): Long {
    val initialState = input.parseInitialState()

    val checked = mutableSetOf<String>()
    val toCheck = priorityQueueBy<BurrowState> { it.energyCost }

    toCheck.add(initialState)

    while (toCheck.isNotEmpty()) {
        val state = toCheck.poll()

        if (!checked.add(state.hash)) {
            continue
        }
        if (state.isFinished) {
            return state.energyCost
        }

        val nextStates =
            state
                .getMovableAmphipodPositions()
                .flatMap { (amphipod, pos) ->
                    state.getValidNextStates(amphipod, pos)
                }
                .filter { !checked.contains(it.hash) }

        toCheck.addAll(nextStates)
    }

    throw Error("No path found")
}

private data class ExtendedBurrowState(val positions: String, val energyCost: Long) {
    val hash = positions

    val isFinished get() = this.positions == "AAAABBBBCCCCDDDD..x.x.x.x.."

    fun isTargetRoomOpen(amphipod: Char) =
        extendedTargetRooms[amphipod]!!.all { positions[it] == '.' || positions[it] == amphipod}

    fun isLowestAvailableSpotInTargetRoom(amphipod: Char, pos: Int) =
        (extendedTargetRooms[amphipod]!!.first until pos).all { positions[it] == amphipod }

    fun isHallway(pos: Int) =
        pos in 16 .. 26

    fun getValidNextStates(amphipod: Char, from: Int): List<ExtendedBurrowState> {
        if (positions[from] != amphipod) return emptyList()

        val checked = mutableSetOf<Int>()

        val startedInHallway = isHallway(from)

        fun explore(pos: Int, costSoFar: Long): Map<Int, Long> {
            if (checked.contains(pos)) return mapOf()
            checked.add(pos)

            val cur = mapOf(pos to costSoFar)

            val validMoves =
                listOfNotNull(
                    if (!isHallway(pos)) listOf(extendedTargetRoomExitMoves[pos]!!) else null,
                    if (extendedValidMovesIntoTargetRooms[amphipod]!!.containsKey(pos) && isTargetRoomOpen(amphipod)) listOf(extendedValidMovesIntoTargetRooms[amphipod]!![pos]!!) else null,
                    if (isHallway(pos)) extendedHallwayMoves[pos]!!.map { it.second } else null
                ).flatten()

            return validMoves
                .filter {
                    !checked.contains(it) && positions[it] in allowedToMoveIntoChars
                }
                .fold(cur) { acc, nextPos ->
                    val destinations = explore(nextPos, costSoFar + costs[amphipod]!!)
                    acc.plus(destinations)
                }
        }


        val destinations = explore(from, 0, )

        return destinations
            .filter { (pos) ->
                positions[pos] == '.'
                    && (pos in extendedValidHallwayPositions ||
                        (pos in extendedTargetRooms[amphipod]!! && isTargetRoomOpen(amphipod) && isLowestAvailableSpotInTargetRoom(amphipod, pos))
                    )
                    && (!startedInHallway || !isHallway(pos))
            }
            .map { (to, cost) ->
                val newPositions = positions.toMutableList()
                newPositions[from] = '.'
                newPositions[to] = amphipod
                ExtendedBurrowState(newPositions.joinToString(""), this.energyCost + cost)
            }
    }

    fun getMovableAmphipodPositions(): List<Pair<Char, Int>> {
        return positions.mapIndexedNotNull { pos, char ->
            val isAmphipod = char in setOf('A', 'B', 'C', 'D')

            if (!isAmphipod) {
                null
            } else {
                val isPosInTargetRoom = pos in extendedTargetRooms[char]!!

                if (!isPosInTargetRoom) {
                    char to pos
                } else {
                    val hasReachedDestination = positions.subSequence(extendedTargetRooms[char]!!.first, pos + 1).all { it == char }
                    if (!hasReachedDestination) {
                        char to pos
                    } else {
                        null
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "[${energyCost}] " + positions.substring(0, 4) + " " + positions.substring(4, 8) + " " + positions.substring(8, 12) + " " + positions.substring(12, 16) + " " + positions.substring(16, 27)
    }
}

private val expected =
    setOf(
        // "BDDDDBCBAABACCAC..x.x.x.x..",
        // "BDDDDBC.AABACCACB.x.x.x.x..",
        // "BDDDDB..AABACCACBCx.x.x.x..",
        // "BDDDD...AABACCACBCxBx.x.x..",
        // "BDDD....AABACCACBCxBx.x.xD.",
        // "BDDDB...AABACCACBCx.x.x.xD.",
        // "BDDDB...AABACCACB.x.x.xCxD.",
        // "BDDDBB..AABACCAC..x.x.xCxD.",
        // "BDDDBB..AAB.CCACA.x.x.xCxD.",
        // "BDDDBBB.AA..CCACA.x.x.xCxD.",
        // "BDDDBBB.A...CCACAAx.x.xCxD.",
        // "BDDDBBB.....CCACAAxAx.xCxD.",
        // "BDDDBBB.C...CCACAAxAx.x.xD.",
        // "BDDDBBB.CC..CCA.AAxAx.x.xD.",
        // "BDDDBBB.CC..CC..AAxAxAx.xD.",
        // "BDDDBBB.CCC.C...AAxAxAx.xD.",
        // "BDDDBBB.CCCC....AAxAxAx.xD.",
        // "BDDDBBB.CCCCD...AAxAxAx.x..",
        // "BDDDBBB.CCCCD...AAxAx.x.x.A",
        // "BDDDBBB.CCCCD...AAx.x.x.xAA",
        // "BDD.BBB.CCCCDD..AAx.x.x.xAA",
        "BD..BBB.CCCCDDD.AAx.x.x.xAA",
        "B...BBB.CCCCDDDDAAx.x.x.xAA",
        "....BBBBCCCCDDDDAAx.x.x.xAA",
        "A...BBBBCCCCDDDDAAx.x.x.x.A",
        "AA..BBBBCCCCDDDDAAx.x.x.x..",
        "AAA.BBBBCCCCDDDDA.x.x.x.x..",
        "AAAABBBBCCCCDDDDAAx.x.x.x..",
    )

@DoNotAutoExecute // Takes about 7 minutes :(
fun day23b(input: List<String>): Long {
    val initialState = input.parseExtendedInitialState()

    val checked = mutableSetOf<String>()
    val toCheck = priorityQueueBy<Pair<ExtendedBurrowState,List<ExtendedBurrowState>>> { it.first.energyCost }

    toCheck.add(initialState to emptyList())
    var lastEnergyMilestone = 0L

    while (toCheck.isNotEmpty()) {
        val (state, prevStates) = toCheck.poll()

        if (!checked.add(state.hash)) {
            continue
        }
        if (state.isFinished) {
            return state.energyCost
        }

        if (expected.contains(state.hash)){
            val a = 1
        }

        if (state.energyCost % 100L == 0L && state.energyCost > lastEnergyMilestone) {
            println("Reached energy ${state.energyCost}: ${state}")
            lastEnergyMilestone = state.energyCost
        }

        val nextStates =
            state
                .getMovableAmphipodPositions()
                .flatMap { (amphipod, pos) ->
                    state.getValidNextStates(amphipod, pos)
                }
                .filter { !checked.contains(it.hash) }
                .map { it to prevStates.plus(state) }

        toCheck.addAll(nextStates)

        if (expected.contains(state.hash)){
            val a = 1
        }
    }

    throw Error("No path found")
}