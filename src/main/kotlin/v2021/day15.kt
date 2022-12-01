package v2021

import util.priorityQueueBy
import util.wrap

private fun List<String>.parseRiskLevels() =
    map { row -> row.map { riskLevel -> riskLevel.toString().toInt() } }

private fun List<List<Int>>.findShortestPathRiskLevel(): Int {
    data class Path(val x: Int, val y: Int, val riskLevel: Int)

    val maxX = this[0].size - 1
    val maxY = this.size - 1

    val checked = mutableSetOf<Pair<Int, Int>>()
    val toCheck = priorityQueueBy<Path> { it.riskLevel }

    toCheck.add(Path(0, 0, 0))

    while (toCheck.isNotEmpty()) {
        val (x, y, riskLevel) = toCheck.poll()

        if (x == maxX && y == maxY) {
            return riskLevel
        }

        if (!checked.add(x to y)) {
            continue
        }

        listOfNotNull(
            if (x > 0)    x - 1 to y     else null,
            if (x < maxX) x + 1 to y     else null,
            if (y > 0)    x     to y - 1 else null,
            if (y < maxY) x     to y + 1 else null
        ).forEach { neighbour ->
            if (!checked.contains(neighbour)) {
                val (neighbourX, neighbourY) = neighbour
                toCheck.add(Path(neighbourX, neighbourY, riskLevel + this[neighbourY][neighbourX]))
            }
        }
    }

    throw Error("No path found")
}

fun day15a(input: List<String>) =
    input
        .parseRiskLevels()
        .findShortestPathRiskLevel()


private fun List<List<Int>>.extrapolate(times: Int): List<List<Int>> {
    val width = get(0).size
    val height = size

    return (0 until height * times)
        .map { y ->
            val yDelta = y / height
            val yMod   = y % height

            (0 until width * times)
                .map { x ->
                    val xDelta = x / width
                    val xMod   = x % width

                    val riskLevel = this[yMod][xMod] + yDelta + xDelta
                    riskLevel.wrap(9)
                }
        }
}

fun day15b(input: List<String>) =
    input
        .parseRiskLevels()
        .extrapolate(5)
        .findShortestPathRiskLevel()
