
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

private fun canMove(x: Int, y: Int, dx: Int, dy: Int, input: List<String>): Boolean {
    if (y + dy !in input.indices || x + dx !in input[0].indices) return false

    val char = input[y + dy][x + dx]

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
        listOf(
            listOf(startX, startY, -1, 0),
            listOf(startX, startY, 1, 0),
            listOf(startX, startY, 0, 1),
            listOf(startX, startY, 0, -1),
        ).filter { (x, y, dx, dy) ->
            canMove(x, y, dx, dy, input)
        }.map { (x, y, dx, dy) ->
            (x + dx to y + dy) to 1
        }
    )

    val up = 0 to -1
    val down = 0 to 1
    val left = -1 to 0
    val right = 1 to 0

    val distances = mutableMapOf((startX to startY) to 0)

    while (toVisit.isNotEmpty()) {
        val (pos, distance) = toVisit.removeFirst()

        if (pos in distances) continue

        distances[pos] = distance

        val (x, y) = pos
        val char = input[y][x]

        val moves = when (char) {
            '|' -> listOf(up, down)
            '-' -> listOf(left, right)
            'L' -> listOf(up, right)
            'J' -> listOf(up, left)
            '7' -> listOf(down, left)
            'F' -> listOf(down, right)
            else -> emptyList()
        }

        toVisit.addAll(
            moves
                .filter { (dx, dy) -> canMove(x, y, dx, dy, input) }
                .map { (dx, dy) -> (x + dx to y + dy) to distance + 1 }
                .filter { (pos, _) -> pos !in distances }
        )
    }

    return distances
}

private fun Set<Pair<Int, Int>>.zoomLoop(input: List<String>): Set<Pair<Int, Int>> {
    // Zooms in by multiplying all positions by two and filling in connections between the gaps
    val expandedLoop = mutableSetOf<Pair<Int, Int>>()
    this.forEach { (x, y) ->
        val expandedX = x * 2
        val expandedY = y * 2

        val left = expandedX - 1 to expandedY
        val right = expandedX + 1 to expandedY
        val up = expandedX to expandedY - 1
        val down = expandedX to expandedY + 1

        expandedLoop.add(expandedX to expandedY)
        expandedLoop.addAll(
            when (input[y][x]) {
                '|' -> listOf(up, down)
                '-' -> listOf(left, right)
                'L' -> listOf(up, right)
                'J' -> listOf(up, left)
                '7' -> listOf(down, left)
                'F' -> listOf(down, right)
                else -> emptyList()
            }
        )
    }
    return expandedLoop
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