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

    // Determine positions of border tiles
    val border = mutableSetOf<Pair<Int, Int>>()
    var prevPos = redTilePositions.first()
    border.add(prevPos)
    for (pos in redTilePositions.drop(1).plus(prevPos)) {
        val dx = if (prevPos.first > pos.first) -1 else if (prevPos.first < pos.first) 1 else 0
        val dy = if (prevPos.second > pos.second) -1 else if (prevPos.second < pos.second) 1 else 0

        var curPos = prevPos
        while (curPos != pos) {
            curPos = (curPos.first + dx) to (curPos.second + dy)
            border.add(curPos)
        }

        border.add(pos)
        prevPos = pos
    }

    // Get a sorted (by size) list of rectangles
    val rectangleSizes = redTilePositions.indices.flatMap { i ->
        ((i + 1) until redTilePositions.size).map { j ->
            val width = abs(redTilePositions[i].first - redTilePositions[j].first) + 1
            val height = abs(redTilePositions[i].second - redTilePositions[j].second) + 1
            val rectangleSize = width * height
            (redTilePositions[i] to redTilePositions[j]) to rectangleSize
        }
    }.sortedByDescending { (_, size) -> size }

    // Find rectangle that fully lies within borders
    for (entry in rectangleSizes) {
        val (corners, _) = entry
        val (corner1, corner2) = corners
        val (x1, y1) = corner1
        val (x2, y2) = corner2

        // Get 'inner' rectangle without its border
        val minX = min(x1, x2) + 1L
        val maxX = max(x1, x2) - 1L
        val minY = min(y1, y2) + 1L
        val maxY = max(y1, y2) - 1L

        // Check if 'inner' rectangle intersects with the shape border at any position
        val intersectsBorder = border.any { (x, y) ->
            x in minX..maxX && y in minY..maxY
        }

        // If no intersection
        if (!intersectsBorder) {
            val width = maxX - minX + 3
            val height = maxY - minY + 3
            return width * height
        }
    }

    throw Exception("No valid rectangle found")
}
