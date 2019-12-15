package v2019.days.day15

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes
import v2019.util.manhattanDistance
import java.util.PriorityQueue
import kotlin.math.min

private enum class Direction {
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

private const val EMPTY = -1L
private const val WALL = 0L
private const val HALL = 1L
private const val OXYGEN = 2L

private data class Pos(val x: Long, val y: Long) {
    fun north() = Pos(x, y - 1)
    fun east()  = Pos(x + 1, y)
    fun south() = Pos(x, y + 1)
    fun west()  = Pos(x - 1, y)
    fun neighbours(): List<Pos> = listOf(north(), east(), south(), west())
}

private val ORIGIN = Pos(0, 0)

private data class Step(val pos: Pos, val distanceToTarget: Long, val stepsToReach: Long)

private fun exploreGrid(intCodes: MutableMap<Long, Long>): MutableMap<Long, MutableMap<Long, Long>> {
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

    val target = grid.cellsOfType(OXYGEN).first()

    fun distanceToTarget(pos: Pos) = manhattanDistance(pos.x, pos.y, target.x, target.y)

    val checked = mutableMapOf<Pos, Long>()
    val toCheck = PriorityQueue<Step> { a, b -> 10 * a.distanceToTarget.compareTo(b.distanceToTarget) + a.stepsToReach.compareTo(b.stepsToReach) }
    toCheck.add(Step(ORIGIN, distanceToTarget(ORIGIN), 0))

    fun addIfAccessibleAndLessSteps(pos: Pos, stepsToReach: Long) {
        if (grid.get(pos) != WALL
            && (checked[pos] ?: Long.MAX_VALUE) > stepsToReach
            && toCheck.none { it.pos == pos && it.stepsToReach <= stepsToReach }
        ) {
            toCheck.add(Step(pos, distanceToTarget(pos), stepsToReach))
        }
    }

    while (true) {
        val (pos, _, stepsToReach) = toCheck.poll()
        if (pos == target) {
            return stepsToReach
        }

        checked[pos] = stepsToReach

        pos.neighbours().forEach { addIfAccessibleAndLessSteps(it, stepsToReach + 1) }
    }
}

fun day15b(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid = exploreGrid(intCodes)

    val target = grid.cellsOfType(OXYGEN).first()

    val distances = mutableMapOf<Pos, Long>()
    fun getDistance(pos: Pos) = distances[pos] ?: Long.MAX_VALUE

    val toVisit = PriorityQueue<Pair<Pos, Long>> { a, b -> a.second.compareTo(b.second) }
    toVisit.add(target to 0L)

    while (toVisit.isNotEmpty()) {
        val (pos, distance) = toVisit.poll()
        distances[pos] = min(getDistance(pos), distance)

        toVisit.addAll(
            pos.neighbours()
                .filter { grid.get(it) == HALL && getDistance(it) > distance }
                .map { it to distance + 1}
        )
    }

    return distances.values.max()!!
}
