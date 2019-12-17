package v2019.days.day17

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes
import java.util.ArrayDeque

private fun findIntersections(grid: List<String>): Sequence<Pair<Int, Int>> {
    return sequence {
        for (y in 1 until grid.size - 1) {
            for (x in 1 until grid[0].length - 1) {
                if (grid[y][x] == '#' &&
                    grid[y - 1][x] == '#' &&
                    grid[y + 1][x] == '#' &&
                    grid[y][x - 1] == '#' &&
                    grid[y][x + 1] == '#') {
                    yield(x to y)
                }
            }
        }
    }
}

fun day17a(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid =
        generateProgramOutput(intCodes) { 0 }
            .map { it.toChar() }
            .joinToString("")
            .trim()
            .split('\n')

    val intersections = findIntersections(grid)

    return intersections.sumBy { it.first * it.second }.toLong()
}

fun day17b_hardcoded(input: String): Long {
    // Optimal path (manually determined):
    // R,8,R,10,R,10,R,4,R,8,R,10,R,12,R,8,R,10,R,10,R,12,R,4,L,12,L,12,R,8,R,10,R,10,R,4,R,8,R,10,R,12,R,12,R,4,L,12,L,12,R,8,R,10,R,10,R,4,R,8,R,10,R,12,R,12,R,4,L,12,L,12
    val movementInput = ArrayDeque<Long>(
        ("B,C,B,A,B,C,A,B,C,A\n" +
        "R,12,R,4,L,12,L,12\n" + // A
        "R,8,R,10,R,10\n" + // B
        "R,4,R,8,R,10,R,12\n" + // C
        "n\n").map { it.toLong() }
    )

    val intCodes = parseIntCodes(input)
    intCodes[0] = 2

    return generateProgramOutput(intCodes) { movementInput.poll() }
        .last()
}

private enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

private data class Pos(var x: Int, var y: Int)

private val vacuumChars = mapOf('^' to Direction.NORTH,'v' to Direction.SOUTH,'<' to Direction.WEST,'>' to Direction.EAST)

fun day17b_compute(input: String): Long {
    val intCodes = parseIntCodes(input)

    val staticGrid =
        generateProgramOutput(intCodes.toMutableMap()) { 0 }
            .map { it.toChar() }
            .joinToString("")
            .trim()
            .split('\n')

    val grid =
        staticGrid
            .map { it.toCharArray() }
            .toMutableList()

    val gridHeight = grid.size
    val gridWidth = grid[0].size

    val nrOfIntersections = findIntersections(staticGrid)
    var nrOfScaffolds = grid.sumBy { row -> row.count { it == '#' }} + nrOfIntersections.count()

    val vacuumPos = grid.withIndex().flatMap { (y, row) -> row.withIndex().mapNotNull { (x, char) -> if (vacuumChars.keys.contains(char)) Pos(x, y) else null }}.first()
    var vacuumDirection = when (grid[vacuumPos.y][vacuumPos.x]) {
        '^' -> Direction.NORTH
        'v' -> Direction.SOUTH
        '<' -> Direction.WEST
        else -> Direction.EAST
    }

    fun canGoEast(): Boolean = vacuumPos.x < gridWidth - 1 && grid[vacuumPos.y][vacuumPos.x + 1] == '#'
    fun canGoWest(): Boolean = vacuumPos.x > 0 && grid[vacuumPos.y][vacuumPos.x - 1] == '#'
    fun canGoSouth(): Boolean = vacuumPos.y < gridHeight - 1 && grid[vacuumPos.y + 1][vacuumPos.x] == '#'
    fun canGoNorth(): Boolean = vacuumPos.y > 0 && grid[vacuumPos.y - 1][vacuumPos.x] == '#'

    val movements = mutableListOf<String>()

    fun turn() {
        when (vacuumDirection) {
            Direction.NORTH -> {
                if (canGoEast()) {
                    vacuumDirection = Direction.EAST
                    movements.add("R")
                } else if (canGoWest()) {
                    vacuumDirection = Direction.WEST
                    movements.add("L")
                }
            }
            Direction.SOUTH -> {
                if (canGoEast()) {
                    vacuumDirection = Direction.EAST
                    movements.add("L")
                } else if (canGoWest()) {
                    vacuumDirection = Direction.WEST
                    movements.add("R")
                }
            }
            Direction.EAST -> {
                if (canGoNorth()) {
                    vacuumDirection = Direction.NORTH
                    movements.add("L")
                } else if (canGoSouth()) {
                    vacuumDirection = Direction.SOUTH
                    movements.add("R")
                }
            }
            Direction.WEST -> {
                if (canGoNorth()) {
                    vacuumDirection = Direction.NORTH
                    movements.add("R")
                } else if (canGoSouth()) {
                    vacuumDirection = Direction.SOUTH
                    movements.add("L")
                }
            }
        }
    }

    fun stepWhilePossible() {
        var steps = 0
        when (vacuumDirection) {
            Direction.NORTH -> {
                while (canGoNorth()) {
                    vacuumPos.y--
                    steps++
                }
            }
            Direction.SOUTH -> {
                while (canGoSouth()) {
                    vacuumPos.y++
                    steps++
                }
            }
            Direction.EAST -> {
                while (canGoEast()) {
                    vacuumPos.x++
                    steps++
                }
            }
            Direction.WEST -> {
                while (canGoWest()) {
                    vacuumPos.x--
                    steps++
                }
            }
        }
        nrOfScaffolds -= steps
        movements.add(steps.toString())
    }

    do {
        turn()
        stepWhilePossible()
    } while (nrOfScaffolds > 0)

    error("TODO generate A, B and C from movement instructions: " + movements.joinToString(","))
}
