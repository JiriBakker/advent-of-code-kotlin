package v2019

import util.DoNotAutoExecute
import util.min
import java.util.ArrayDeque

import v2019.Day17.Direction
import v2019.Day17.Pos

private object Day17 {
    enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }

    data class Pos(var x: Int, var y: Int)
}

private fun findIntersections(grid: List<CharArray>): Sequence<Pair<Int, Int>> {
    return sequence {
        for (y in 1 until grid.size - 1) {
            for (x in 1 until grid[0].size - 1) {
                if (grid[y][x] == '#' &&
                    grid[y - 1][x] == '#' &&
                    grid[y + 1][x] == '#' &&
                    grid[y][x - 1] == '#' &&
                    grid[y][x + 1] == '#'
                ) {
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
            .map { it.toInt().toChar() }
            .joinToString("")
            .trim()
            .split('\n')
            .map { it.toCharArray() }

    val intersections = findIntersections(grid)

    return intersections.sumOf { it.first * it.second }.toLong()
}

private fun vacuumWithInput(movementInput: String, intCodes: MutableMap<Long, Long>): Sequence<Long> {
    val movementQueue = ArrayDeque<Long>(movementInput.map { it.code.toLong() })

    intCodes[0] = 2

    return generateProgramOutput(intCodes) { movementQueue.poll() }
}

fun day17b_hardcoded(input: String): Long {
    // Optimal path (manually determined):
    // R,8,R,10,R,10,R,4,R,8,R,10,R,12,R,8,R,10,R,10,R,12,R,4,L,12,L,12,R,8,R,10,R,10,R,4,R,8,R,10,R,12,R,12,R,4,L,12,L,12,R,8,R,10,R,10,R,4,R,8,R,10,R,12,R,12,R,4,L,12,L,12
    val movementInput =
        "B,C,B,A,B,C,A,B,C,A\n" +
        "R,12,R,4,L,12,L,12\n" + // A
        "R,8,R,10,R,10\n" + // B
        "R,4,R,8,R,10,R,12\n" + // C
        "n\n"

    val intCodes = parseIntCodes(input)

    return vacuumWithInput(movementInput, intCodes).last()
}

private val vacuumChars =
    mapOf('^' to Direction.NORTH, 'v' to Direction.SOUTH, '<' to Direction.WEST, '>' to Direction.EAST)

fun day17b_compute(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid =
        generateProgramOutput(intCodes.toMutableMap()) { 0 }
            .map { it.toInt().toChar() }
            .joinToString("")
            .trim()
            .split('\n')
            .map { it.toCharArray() }

    val gridHeight = grid.size
    val gridWidth = grid[0].size

    val nrOfIntersections = findIntersections(grid)
    var nrOfScaffolds = grid.sumOf { row -> row.count { it == '#' } } + nrOfIntersections.count()

    val vacuumPos = grid.withIndex().flatMap { (y, row) ->
        row.withIndex().mapNotNull { (x, char) -> if (vacuumChars.keys.contains(char)) Pos(x, y) else null }
    }.first()

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

    val movementsString = movements.joinToString(",")

    fun findFunctions(maxFunctionLength: Int = 20): Triple<String, String, String> {
        val replacementChars = setOf('A', 'B', 'C')
        fun isReplacementChar(char: Char): Boolean = replacementChars.contains(char)
        fun isComma(char: Char): Boolean = char == ','

        for (aLength in (maxFunctionLength + 2) downTo 3) {
            for (bLength in (maxFunctionLength + 2) downTo 3) {
                val a = movementsString.take(aLength).trim(',')
                val b = movementsString.takeLast(bLength).trim(',')

                if (a.length > maxFunctionLength || b.length > maxFunctionLength) {
                    continue
                }

                val withoutAB = movementsString.replace(a, "A".repeat(a.length)).replace(b, "B".repeat(b.length))

                val cStart = withoutAB.indexOfFirst { !isReplacementChar(it) && !isComma(it) }
                val cEnd = cStart +
                    min(
                        withoutAB.drop(cStart).indexOfFirst(::isReplacementChar),
                        (maxFunctionLength + 2)
                    )

                for (cLength in cEnd - cStart downTo 3) {
                    val c = movementsString.drop(cStart).take(cLength).trim(',')
                    if (c.any(::isReplacementChar) || c.length > maxFunctionLength) {
                        continue
                    }

                    val withoutABC = withoutAB.replace(c, "C".repeat(c.length))
                    if (withoutABC.all { isReplacementChar(it) || isComma(it) }) {
                        return Triple(a, b, c)
                    }
                }
            }
        }

        error("No valid A, B, C combination found")
    }

    val (a, b, c) = findFunctions()

    val mainRoutine =
        movementsString
            .replace(a, "A")
            .replace(b, "B")
            .replace(c, "C")
            .replace(",", "")
            .split("")
            .joinToString(",")
            .trim(',')

    return vacuumWithInput("$mainRoutine\n$a\n$b\n$c\nn\n", intCodes).last()
}
