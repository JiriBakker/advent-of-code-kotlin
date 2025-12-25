import util.countDistinct

fun day16a(input: List<String>): Int {
    return countEnergizedTiles((0 to 0) to (1 to 0), input)
}

fun day16b(input: List<String>): Int {
    return listOf(
        input.indices.maxOf { y -> countEnergizedTiles((0 to y) to (1 to 0), input) },
        input.indices.maxOf { y -> countEnergizedTiles((input.size - 1 to y) to (-1 to 0), input) },
        input[0].indices.maxOf { x -> countEnergizedTiles((x to 0) to (0 to 1), input) },
        input[0].indices.maxOf { x -> countEnergizedTiles((x to input.size - 1) to (0 to -1), input) }
    ).max()
}

private fun countEnergizedTiles(startState: Pair<Pair<Int, Int>, Pair<Int, Int>>, grid: List<String>): Int {
    val toVisit = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    toVisit.add(startState)

    val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    while (toVisit.isNotEmpty()) {
        val (pos, delta) = toVisit.removeFirst()
        val (x, y) = pos
        val (dx, dy) = delta

        if (y !in grid.indices || x !in grid[0].indices) continue

        if (!visited.add(pos to delta)) continue

        val cur = grid[y][x]

        when (cur) {
            '|' -> {
                if (dx == 0) {
                    toVisit.add((x to (y + dy)) to delta)
                } else {
                    toVisit.add((x to (y - 1)) to (0 to -1))
                    toVisit.add((x to (y + 1)) to (0 to 1))
                }
            }

            '-' -> {
                if (dx == 0) {
                    toVisit.add(((x - 1) to y) to (-1 to 0))
                    toVisit.add(((x + 1) to y) to (1 to 0))
                } else {
                    toVisit.add(((x + dx) to y) to delta)
                }
            }

            '/' -> {
                val dx2 = -dy
                val dy2 = -dx
                toVisit.add(((x + dx2) to (y + dy2)) to (dx2 to dy2))
            }

            '\\' -> {
                val dx2 = dy
                val dy2 = dx
                toVisit.add(((x + dx2) to (y + dy2)) to (dx2 to dy2))
            }

            else -> {
                toVisit.add(((x + dx) to (y + dy)) to delta)
            }

        }
    }

    return visited.map { it.first }.countDistinct()
}