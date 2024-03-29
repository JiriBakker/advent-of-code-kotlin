import util.manhattanDistanceLong
import java.util.PriorityQueue
import kotlin.math.min

import Day15.Direction
import Day15.Pos

private object Day15 {

    enum class Direction {
        NORTH, EAST, SOUTH, WEST;

        fun toInstruction(): Long {
            return when (this) {
                NORTH -> 1L
                SOUTH -> 2L
                WEST -> 3L
                EAST -> 4L
            }
        }
    }

    data class Pos(val x: Long, val y: Long) {
        fun north() = Pos(x, y - 1)
        fun east()  = Pos(x + 1, y)
        fun south() = Pos(x, y + 1)
        fun west()  = Pos(x - 1, y)
        fun neighbours(): List<Pos> = listOf(north(), east(), south(), west())
    }

}

private const val EMPTY = -1L
private const val WALL = 0L
private const val HALL = 1L
private const val OXYGEN = 2L

private val ORIGIN = Pos(0, 0)

private data class Step(val pos: Pos, val stepsToReach: Long, val distanceToTarget: Long)

private fun exploreGrid(intCodes: MutableMap<Long, Long>): Map<Long, Map<Long, Long>> {
    var curPos = ORIGIN
    var curDirection = Direction.NORTH

    fun stepForward() {
        curPos = when (curDirection) {
            Direction.NORTH -> curPos.north()
            Direction.EAST  -> curPos.east()
            Direction.SOUTH -> curPos.south()
            Direction.WEST  -> curPos.west()
        }
    }

    fun stepBack() {
        curPos = when (curDirection) {
            Direction.NORTH -> curPos.south()
            Direction.EAST  -> curPos.west()
            Direction.SOUTH -> curPos.north()
            Direction.WEST  -> curPos.east()
        }
    }

    fun turnRight() {
        curDirection = when (curDirection) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST  -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST  -> Direction.NORTH
        }
    }

    fun turnLeft() {
        curDirection = when (curDirection) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST  -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            Direction.WEST  -> Direction.SOUTH
        }
    }

    val grid = mutableMapOf(ORIGIN.y to mutableMapOf(ORIGIN.x to HALL))

    var hasMoved = false
    generateProgramOutput(intCodes) { curDirection.toInstruction() }
        .takeWhile { !hasMoved || curPos != ORIGIN }
        .forEach { output ->
            stepForward()
            grid.set(curPos, output)
            if (output == WALL) {
                stepBack()
                turnRight()
            } else {
                hasMoved = true
                turnLeft()
            }
        }

    return grid
}

private fun Map<Long, Map<Long, Long>>.cellsOfType(type: Long): Sequence<Pos> {
    val grid = this
    return sequence {
        grid.forEach { (y, row) ->
            row.forEach { (x, cell) ->
                if (cell == type) yield(Pos(x, y))
            }
        }
    }
}

private fun Map<Long, Map<Long, Long>>.get(pos: Pos, default: Long = EMPTY): Long {
    return this.getOrElse(pos.y, { mutableMapOf() }).getOrDefault(pos.x, default)
}

private fun MutableMap<Long, MutableMap<Long, Long>>.set(pos: Pos, value: Long) {
    this.getOrPut(pos.y, { mutableMapOf() })[pos.x] = value
}

fun day15a(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid = exploreGrid(intCodes)

    val oxygenSystem = grid.cellsOfType(OXYGEN).first()

    fun distanceToTarget(pos: Pos) = manhattanDistanceLong(pos.x, pos.y, oxygenSystem.x, oxygenSystem.y)

    val checked = mutableMapOf<Pos, Long>()
    fun stepsToReachFound(pos: Pos) = checked[pos] ?: Long.MAX_VALUE

    val toCheck = PriorityQueue<Step> { a, b -> 10 * a.distanceToTarget.compareTo(b.distanceToTarget) + a.stepsToReach.compareTo(b.stepsToReach) }
    toCheck.add(Step(ORIGIN, 0, distanceToTarget(ORIGIN)))

    fun addIfViable(pos: Pos, stepsToReach: Long) {
        if (grid.get(pos) != WALL
            && stepsToReachFound(pos) > stepsToReach
            && toCheck.none { it.pos == pos && it.stepsToReach <= stepsToReach }
        ) {
            toCheck.add(Step(pos, stepsToReach, distanceToTarget(pos)))
        }
    }

    while (true) {
        val (pos, stepsToReach) = toCheck.poll()
        if (pos == oxygenSystem) {
            return stepsToReach
        }

        checked[pos] = stepsToReach

        pos.neighbours().forEach { neighbour -> addIfViable(neighbour, stepsToReach + 1) }
    }
}

fun day15b(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid = exploreGrid(intCodes)

    val oxygenSystem = grid.cellsOfType(OXYGEN).first()

    val distances = mutableMapOf<Pos, Long>()
    fun distanceFound(pos: Pos) = distances[pos] ?: Long.MAX_VALUE

    val toVisit = PriorityQueue<Pair<Pos, Long>> { a, b -> a.second.compareTo(b.second) }
    toVisit.add(oxygenSystem to 0L)

    while (toVisit.isNotEmpty()) {
        val (pos, distance) = toVisit.poll()
        distances[pos] = min(distanceFound(pos), distance)

        pos.neighbours()
            .filter { neighbour -> grid.get(neighbour) == HALL && distanceFound(neighbour) > distance }
            .forEach { neighbour -> toVisit.add(neighbour to distance + 1) }
    }

    return distances.values.maxOrNull()!!
}
