import util.toPair
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun day09a(input: List<String>): Long {
    val redTilePositions = input.map { it.split(",").map(String::toLong).toPair() }
    val rectangleSizes = redTilePositions.indices.flatMap { i ->
        ((i + 1) until redTilePositions.size).map { j ->
            val rectangleSize = (abs(redTilePositions[i].first - redTilePositions[j].first) + 1) * (abs(redTilePositions[i].second - redTilePositions[j].second) + 1)
            (redTilePositions[i] to redTilePositions[j]) to rectangleSize
        }
    }.associate { it.first to it.second }

    return rectangleSizes.maxOf { it.value }
}

fun day09b(input: List<String>): Long {
    val redTilePositions = input.map { it.split(",").map(String::toInt).toPair() }

    val grid = mutableSetOf<Pair<Int, Int>>()

    var prevPos = redTilePositions.first()
    grid.add(prevPos)
    for (pos in redTilePositions.drop(1).plus(prevPos)) {
        val dx = if (prevPos.first > pos.first) -1 else if (prevPos.first < pos.first) 1 else 0
        val dy = if (prevPos.second > pos.second) -1 else if (prevPos.second < pos.second) 1 else 0

        var curPos = prevPos
        while (curPos != pos) {
            curPos = (curPos.first + dx) to (curPos.second + dy)
            grid.add(curPos)
        }

        grid.add(pos)

        prevPos = pos
    }

    val rectangleSizes = redTilePositions.indices.flatMap { i ->
        ((i + 1) until redTilePositions.size).map { j ->
            val rectangleSize = (abs(redTilePositions[i].first - redTilePositions[j].first) + 1) * (abs(redTilePositions[i].second - redTilePositions[j].second) + 1)
            (redTilePositions[i] to redTilePositions[j]) to rectangleSize
        }
    }.associate { it.first to it.second }

    for (entry in rectangleSizes.entries.sortedByDescending { it.value }) {
        val minX = min(entry.key.first.first, entry.key.second.first) + 1L
        val minY = min(entry.key.first.second, entry.key.second.second) + 1L
        val maxX = max(entry.key.first.first, entry.key.second.first) - 1L
        val maxY = max(entry.key.first.second, entry.key.second.second) - 1L

        var valid = true
        for ((x, y) in grid) {
            if (x in minX..maxX && y in minY..maxY) {
                valid = false
                break
            }
        }

        if (valid) {
            val width = maxX - minX + 3
            val height = maxY - minY + 3
            return width * height
        }
    }

    throw Exception("No valid rectangle found")
}
