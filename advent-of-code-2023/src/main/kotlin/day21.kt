import util.pow

fun day21a(input: List<String>, nrOfSteps: Int = 64): Int {
    val (garden, start) = parseInput(input)
    return garden.findReachablePositions(start, nrOfSteps).count()
}

fun day21b(input: List<String>): Long {
    val steps = 26501365

    val (garden, startPos) = parseInput(input)
    val (startX, startY) = startPos

    val gridSize = steps / garden.width - 1

    val nrOfOddTiles = garden.findReachablePositions(startPos, garden.width * 2 + 1).count().toLong()
    val nrOfEvenTiles = garden.findReachablePositions(startPos, garden.width * 2).count().toLong()

    val nrOfPositionsCompletelyInside =
        nrOfOddTiles * (gridSize / 2 * 2 + 1L).pow(2) +
        nrOfEvenTiles * ((gridSize + 1) / 2 * 2L).pow(2)

    val top = startX to (garden.width - 1L)
    val right = (garden.width - 1L) to startY
    val bottom = startX to 0L
    val left = 0L to startY

    val nrOfCornerPositions =
        listOf(top, right, bottom, left)
            .sumOf { garden.findReachablePositions(it, garden.width - 1).count().toLong() }

    val topRight = (garden.width - 1L) to (garden.width - 1L)
    val topLeft = 0L to (garden.width - 1L)
    val bottomRight = (garden.width - 1L) to 0L
    val bottomLeft = 0L to 0L

    val nrOfInnerTrianglePositions =
        listOf(topRight, bottomRight, bottomLeft, topLeft)
            .sumOf { garden.findReachablePositions(it, garden.width / 2 - 1).count().toLong() }
            .times(gridSize + 1)

    val nrOfOuterTrianglePositions =
        listOf(topRight, bottomRight, bottomLeft, topLeft)
            .sumOf { garden.findReachablePositions(it, garden.width * 3 / 2 - 1).count().toLong() }
            .times(gridSize)

    return nrOfPositionsCompletelyInside + nrOfCornerPositions + nrOfInnerTrianglePositions + nrOfOuterTrianglePositions
}

private class GardenGrid(
    val width: Int,
    val height: Int,
    val wallPositions: Set<Pair<Long, Long>>
) {
    private fun getReachableNeighbours(pos: Pair<Long, Long>): List<Pair<Long, Long>> =
        listOf(
            -1L to 0L, // Left
            1L to 0L,  // Right
            0L to -1L, // Up
            0L to 1L   // Down
        )
        .map { (dx, dy) -> pos.first + dx to pos.second + dy }
        .filter { neighbourPos -> neighbourPos.first in 0 until width && neighbourPos.second in 0 until height && neighbourPos !in wallPositions }


    fun findReachablePositions(startPos: Pair<Long, Long>, steps: Int): Set<Pair<Long, Long>> {
        val reachablePositions = mutableSetOf<Pair<Long, Long>>()

        val toCheck = mutableListOf<Pair<Pair<Long, Long>, Int>>()
        val visited = mutableSetOf<Pair<Long, Long>>()

        toCheck.add(startPos to steps)
        while (toCheck.isNotEmpty()) {
            val (position, remainingSteps) = toCheck.removeFirst()

            if (!visited.add(position)) continue

            if (remainingSteps % 2 == 0) reachablePositions.add(position)

            if (remainingSteps > 0) {
                toCheck.addAll(
                    getReachableNeighbours(position)
                        .map { it to remainingSteps - 1 }
                )
            }
        }

        return reachablePositions
    }
}

private fun parseInput(input: List<String>): Pair<GardenGrid, Pair<Long, Long>> {
    val width = input[0].length
    val height = input.size
    val startPos = input.withIndex().firstNotNullOf { (y, line) -> line.withIndex().firstNotNullOfOrNull { (x, char) -> if (char == 'S') x.toLong() to y.toLong() else null } }
    val wallPositions =
        input
            .withIndex()
            .flatMap { (y, line) ->
                line.withIndex().mapNotNull { (x, char) ->
                    if (char == '#') x.toLong() to y.toLong() else null
                }
            }
            .toSet()

    return GardenGrid(width, height, wallPositions) to startPos
}
