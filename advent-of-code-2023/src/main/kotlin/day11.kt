import kotlin.math.max
import kotlin.math.min
import kotlin.text.all
import kotlin.text.indices

fun day11a(input: List<String>) =
    sumDistanceInExpandedUniverse(input, emptyLineDistance = 1)

fun day11b(input: List<String>, emptyLineDistance: Long) =
    sumDistanceInExpandedUniverse(input, emptyLineDistance)

private fun sumDistanceInExpandedUniverse(input: List<String>, emptyLineDistance: Long): Long {
    val emptyRows = input.indices.filter { y -> input[y].all { it == '.' } }
    val emptyColumns = input[0].indices.filter { x -> input.indices.all { y -> input[y][x] == '.' } }

    val galaxyPositions = input.indices.flatMap { y ->
        input[y].indices
            .filter { x -> input[y][x] == '#' }
            .map { x -> x to y }
    }

    var totalDistance = 0L
    for (i in 0 until galaxyPositions.size - 1) {
        val (x1, y1) = galaxyPositions[i]
        for (j in i + 1 until galaxyPositions.size) {
            val (x2, y2) = galaxyPositions[j]

            val minX = min(x1, x2)
            val maxX = max(x1, x2)
            val minY = min(y1, y2)
            val maxY = max(y1, y2)

            val emptyRowsInBetween = emptyRows.count { y -> y in minY..maxY }
            val emptyColumnsInBetween = emptyColumns.count { x -> x in minX..maxX }

            totalDistance += (maxX - minX) + (maxY - minY) + (emptyRowsInBetween + emptyColumnsInBetween) * emptyLineDistance
        }
    }

    return totalDistance
}
