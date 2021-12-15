package v2021

import util.priorityQueueBy

private fun List<String>.parseRiskLevels() =
    map { row -> row.map { riskLevel -> riskLevel.toString().toInt() } }

private fun List<List<Int>>.findShortestPathRiskLevel(): Int {
    data class Path(val x: Int, val y: Int, val riskLevel: Int)

    val riskLevels = this

    val checked = mutableSetOf<Pair<Int, Int>>()
    val toCheck = priorityQueueBy<Path> { it.riskLevel }

    toCheck.add(Path(0, 0, 0))

    while (toCheck.isNotEmpty()) {
        val path = toCheck.poll()

        if (path.x == riskLevels[0].size - 1 && path.y == riskLevels.size - 1) {
            return path.riskLevel
        }

        if (checked.contains(path.x to path.y)) {
            continue
        }
        checked.add(path.x to path.y)

        listOfNotNull(
            if (path.x > 0) path.x - 1 to path.y else null,
            if (path.x < riskLevels[0].size - 1) path.x + 1 to path.y else null,
            if (path.y > 0) path.x to path.y - 1 else null,
            if (path.y < riskLevels.size - 1) path.x to path.y + 1 else null
        ).forEach { (x, y) ->
            toCheck.add(Path(x, y, path.riskLevel + riskLevels[y][x]))
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

    return (0 until height * times).map { y ->
        val yDelta = y / height
        val yMod = y % height
        (0 until width * times).map { x ->
            val xDelta = x / width
            val xMod = x % width
            val riskLevel = this[yMod][xMod] + yDelta + xDelta
            if (riskLevel > 9) riskLevel - 9
            else riskLevel
        }
    }
}

fun day15b(input: List<String>) =
    input
        .parseRiskLevels()
        .extrapolate(5)
        .findShortestPathRiskLevel()
