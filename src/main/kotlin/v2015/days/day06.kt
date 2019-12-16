package v2015.days.day06

import kotlin.math.max

private fun parseGrid(
    input: List<String>,
    toggleFunc: (Int) -> Int,
    turnOffFunc: (Int) -> Int,
    turnOnFunc: (Int) -> Int
): List<IntArray> {
    val grid = List(1000) { IntArray(1000) { 0 } }

    input.forEach {
        val segments = it.split(" ")
        val (coordinateOffset, operation: (Int) -> Int) = when {
            segments[0] == "toggle" -> 1 to toggleFunc
            segments[1] == "off" -> 2 to turnOffFunc
            else -> 2 to turnOnFunc
        }

        val (topLeftX, topLeftY) = segments[coordinateOffset].split(',').map(String::toInt)
        val (bottomRightX, bottomRightY) = segments[coordinateOffset + 2].split(',').map(String::toInt)

        (topLeftY .. bottomRightY).forEach { y ->
            (topLeftX .. bottomRightX).forEach { x ->
                grid[y][x] = max(0, operation(grid[y][x]))
            }
        }
    }

    return grid
}

fun day06a(input: List<String>): Int {
    val grid = parseGrid(
        input,
        { if (it == 1) 0 else 1 },
        { 0 },
        { 1 }
    )

    return grid.sumBy { row -> row.count { it == 1 } }
}

fun day06b(input: List<String>): Int {
    val grid = parseGrid(
        input,
        { it + 2 },
        { it + -1 },
        { it + 1 }
    )

    return grid.sumBy { row -> row.sum() }
}
