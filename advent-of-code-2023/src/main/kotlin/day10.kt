
fun day10a(input: List<String>): Int {
    val (startX, startY) = findStartPos(input)

    val loop = findLoop(startX, startY, input)

    return loop.values.max()
}

fun day10b(input: List<String>): Int {
    val (startX, startY) = findStartPos(input)

    val loop = findLoop(startX, startY, input)

    val zoomedLoop = loop.keys.zoomLoop(input)

    val outsidePositionsZoomed = findZoomedOutsidePositions(zoomedLoop, input)

    val nrOfTotalPositions = input.size * input[0].length
    val nrOfLoopPositions = loop.size

    val nrOfOutsidePositions =
        outsidePositionsZoomed
            .count { (x, y) ->
                x % 2 == 0 && y % 2 == 0                                    // Zoom out (only count positions on even coordinates)
                    && x / 2 in input[0].indices && y / 2 in input.indices  // Ignore positions that are outside of the input grid
            }

    return nrOfTotalPositions - nrOfOutsidePositions - nrOfLoopPositions
}

private fun List<String>.canMove(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    if (y2 !in this.indices || x2 !in this[0].indices) return false

    val char = this[y2][x2]
    val dx = x2 - x1
    val dy = y2 - y1

    return when (char) {
        '|' -> dx == 0 && dy != 0
        '-' -> dx != 0 && dy == 0
        'L' -> (dx == -1 && dy == 0) || (dx == 0 && dy == 1)
        'J' -> (dx == 1 && dy == 0) || (dx == 0 && dy == 1)
        '7' -> (dx == 1 && dy == 0) || (dx == 0 && dy == -1)
        'F' -> (dx == -1 && dy == 0) || (dx == 0 && dy == -1)
        else -> false
    }
}

private fun findStartPos(input: List<String>): Pair<Int, Int> {
    for (y in input.indices) {
        for (x in input[0].indices) {
            if (input[y][x] == 'S') {
                return x to y
            }
        }
    }
    throw Exception("Start position not found")
}

private fun findLoop(startX: Int, startY: Int, input: List<String>): Map<Pair<Int, Int>, Int> {
    // Traverse pipes from start position and return positions and distances to those positions
    val toVisit = mutableListOf<Pair<Pair<Int, Int>, Int>>()
    toVisit.addAll(
        input[startY][startX]
            .getNeighbours()
            .applyDeltas(startX, startY)
            .filter { (x, y) -> input.canMove(startX, startY, x, y) }
            .map { pos -> pos to 1 }
    )

    val distances = mutableMapOf((startX to startY) to 0)

    while (toVisit.isNotEmpty()) {
        val (pos, distance) = toVisit.removeFirst()

        if (pos in distances) continue

        distances[pos] = distance

        val (x, y) = pos

        toVisit.addAll(
            input[y][x]
                .getNeighbours()
                .applyDeltas(x, y)
                .filter { (x2, y2) -> input.canMove(x, y, x2, y2) }
                .map { (x2, y2) -> (x2 to y2) to distance + 1 }
                .filter { (pos2, _) -> pos2 !in distances }
        )
    }

    return distances
}

private fun Set<Pair<Int, Int>>.zoomLoop(input: List<String>): Set<Pair<Int, Int>> {
    // Zooms in by multiplying all positions by two and filling in connections between the gaps
    val zoomedLoopPositions = mutableSetOf<Pair<Int, Int>>()
    this.forEach { (originalX, originalY) ->
        val zoomedX = originalX * 2
        val zoomedY = originalY * 2

        zoomedLoopPositions.add(zoomedX to zoomedY)
        zoomedLoopPositions.addAll(
            input[originalY][originalX].getNeighbours()
                .applyDeltas(zoomedX, zoomedY)

        )
    }
    return zoomedLoopPositions
}

private fun findZoomedOutsidePositions(expandedLoop: Set<Pair<Int, Int>>, input: List<String>): Set<Pair<Int, Int>> {
    // Search positions (in a zoomed grid) to find all positions that are outside the loop
    val toVisit = mutableListOf<Pair<Int, Int>>()
    toVisit.add(-1 to -1)

    val outsidePositions = mutableSetOf<Pair<Int, Int>>()

    while (toVisit.isNotEmpty()) {
        val pos = toVisit.removeFirst()
        val (x, y) = pos

        if (y !in -1 .. input.size * 2 || x !in -1 .. input[0].length * 2) continue
        if (pos in expandedLoop) continue

        if (!outsidePositions.add(pos)) continue

        toVisit.addAll(
            listOf(
                x - 1 to y,
                x + 1 to y,
                x to y - 1,
                x to y + 1
            )
        )
    }

    return outsidePositions
}

private fun Char.getNeighbours(): List<Pair<Int, Int>> {
    val up = 0 to -1
    val down = 0 to 1
    val left = -1 to 0
    val right = 1 to 0

    return when (this) {
        '|' -> listOf(up, down)
        '-' -> listOf(left, right)
        'L' -> listOf(up, right)
        'J' -> listOf(up, left)
        '7' -> listOf(down, left)
        'F' -> listOf(down, right)
        'S' -> listOf(up, down, left, right)
        else -> emptyList()
    }
}

private fun List<Pair<Int, Int>>.applyDeltas(x: Int, y: Int) =
    this.map { (dx, dy) -> x + dx to y + dy }