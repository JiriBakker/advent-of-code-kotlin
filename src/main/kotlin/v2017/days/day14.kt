package v2017.days.day14

import v2017.days.day10.knotHash
import java.math.BigInteger
import java.util.ArrayDeque

private fun getGrid(input: String): List<String> {
    return (0..127)
        .map { row -> knotHash("$input-$row") }
        .map { hash -> BigInteger(hash, 16).toString(2).padStart(128, '0') }
}

fun day14a(input: String): Int {
    return getGrid(input)
        .sumBy { row -> row.count { it == '1' } }
}

fun day14b(input: String): Int {
    val grid = getGrid(input).map { row -> row.map { it.toString().toInt() }.toMutableList() }

    fun fillGroup(startX: Int, startY: Int, groupId: Int) {
        val toVisit = ArrayDeque<Pair<Int, Int>>()
        toVisit.add(startX to startY)
        while (toVisit.isNotEmpty()) {
            val (x, y) = toVisit.poll()
            if (grid[y][x] == 1) {
                grid[y][x] = groupId
                toVisit.addAll(
                    listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)
                        .filter { (x, y) -> y in grid.indices && x in grid[y].indices }
                )
            }
        }
    }

    var nextGroupId = 2
    grid.indices.forEach { y ->
        grid[y].indices.forEach { x ->
            if (grid[y][x] == 1) {
                fillGroup(x, y, nextGroupId++)
            }
        }
    }

    return nextGroupId - 2
}
