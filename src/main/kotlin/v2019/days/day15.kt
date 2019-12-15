package v2019.days.day15

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes
import v2019.util.manhattanDistance
import java.util.PriorityQueue

private enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun toLong(): Long {
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
private const val EXPANDING_OXYGEN = 3L

private data class Pos(val x: Long, val y: Long) {
    fun left(): Pos = Pos(x - 1, y)
    fun right(): Pos = Pos(x + 1, y)
    fun above(): Pos = Pos(x, y - 1)
    fun below(): Pos = Pos(x, y + 1)
    fun neighbours(): List<Pos> = listOf(right(), left(), above(), below())
}

private val ORIGIN = Pos(0, 0)

private data class Step(val pos: Pos, val distanceToTarget: Long, val stepsToReach: Long)

private fun exploreGrid(intCodes: MutableMap<Long, Long>): MutableMap<Long, MutableMap<Long, Long>> {
    var curX = ORIGIN.x
    var curY = ORIGIN.y
    var curDirection = Direction.NORTH

    fun stepBack() {
        when (curDirection) {
            Direction.NORTH -> curY++
            Direction.EAST  -> curX--
            Direction.SOUTH -> curY--
            Direction.WEST  -> curX++
        }
    }

    fun turnRight(): Direction {
        return when (curDirection) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST  -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST  -> Direction.NORTH
        }
    }

    fun turnLeft(): Direction {
        return when (curDirection) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST  -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            Direction.WEST  -> Direction.SOUTH
        }
    }

    fun stepForward() {
        when (curDirection) {
            Direction.NORTH -> curY--
            Direction.EAST  -> curX++
            Direction.SOUTH -> curY++
            Direction.WEST  -> curX--
        }
    }

    val grid = mutableMapOf(ORIGIN.y to mutableMapOf(ORIGIN.x to HALL))

    var hasMoved = false
    generateProgramOutput(intCodes) { curDirection.toLong() }
        .takeWhile { !hasMoved || !(curX == ORIGIN.x && curY == ORIGIN.y) }
        .forEach { output ->
            stepForward()
            grid.set(curX, curY, output)
            if (output == WALL) {
                stepBack()
                curDirection = turnRight()
            } else {
                hasMoved = true
                curDirection = turnLeft()
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

private fun MutableMap<Long, MutableMap<Long, Long>>.set(x: Long, y: Long, value: Long) {
    this.getOrPut(y, { mutableMapOf() })[x] = value
}

fun day15a(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid = exploreGrid(intCodes)

    val target = grid.cellsOfType(OXYGEN).first()

    fun distanceToTarget(pos: Pos): Long = manhattanDistance(pos.x, pos.y, target.x, target.y)

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

    fun hasOxygenNeighbour(pos: Pos): Boolean {
        return pos.neighbours().any { grid.get(it) == OXYGEN }
    }

    var minutesPassed = 0L
    do {
        grid.cellsOfType(EXPANDING_OXYGEN)
            .forEach { pos -> grid.set(pos.x, pos.y, OXYGEN) }

        grid.cellsOfType(HALL)
            .forEach { pos ->
                if (hasOxygenNeighbour(pos)) {
                    grid.set(pos.x, pos.y, EXPANDING_OXYGEN)
                }
            }

        minutesPassed++
    } while (grid.cellsOfType(HALL).any())

    return minutesPassed
}
