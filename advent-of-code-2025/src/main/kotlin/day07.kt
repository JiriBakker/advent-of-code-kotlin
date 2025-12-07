fun day07a(input: List<String>): Int {
    val startX = input.first().indexOfFirst { it == 'S' }

    val toVisit = mutableListOf<Pair<Int, Int>>()
    toVisit.add(startX to 1)

    val visited = mutableSetOf<Pair<Int, Int>>()

    var splitCount = 0

    while (toVisit.isNotEmpty()) {
        val (curX, curY) = toVisit.removeFirst()

        if (!visited.add(curX to curY) || curY !in input.indices || curX !in input[curY].indices) {
            continue
        }

        if (input[curY][curX] == '.') {
            toVisit.add(curX to curY + 1)
        } else {
            splitCount++
            toVisit.add(curX - 1 to curY + 1)
            toVisit.add(curX + 1 to curY + 1)
        }
    }

    return splitCount
}

fun day07b(input: List<String>): Long {
    val toVisit = mutableListOf<Pair<Int, Int>>()
    val pathCounts = mutableMapOf<Pair<Int, Int>, Long>()

    for (x in input[input.lastIndex].indices) {
        // Start at each bottom position
        toVisit.add(x to input.lastIndex - 1)
        pathCounts[x to input.lastIndex] = 1
    }

    val visited = mutableSetOf<Pair<Int, Int>>()

    while (toVisit.isNotEmpty()) {
        val (curX, curY) = toVisit.removeFirst()
        val pathCount = pathCounts[(curX) to (curY + 1)]!!

        if (!visited.add(curX to curY)      // Already visited
            || curY !in input.indices       // Out of bounds
            || curX !in input[curY].indices // Out of bounds
            || input[curY][curX] == '^') {  // If we hit a splitter from below, it is not a valid path, so skip
            continue
        }

        if (input[curY][curX] == 'S') {
            return pathCount
        }

        fun branchPath(x: Int, y: Int) {
            toVisit.add(x to (curY - 1))
            pathCounts[x to y] = pathCounts.getOrDefault(x to y, 0) + pathCount // Make sure to not overwrite other paths that also ended up here
        }

        // If we pass a splitter, combine the pathCounts and start a new path above the splitter
        if (curX - 1 in input[curY].indices && input[curY][curX - 1] == '^') {
            branchPath(curX - 1, curY)
        }
        if (curX + 1 in input[curY].indices && input[curY][curX + 1] == '^') {
            branchPath(curX + 1, curY)
        }

        // Add path straight up (will dissolve if we hit upper boundary)
        toVisit.add(curX to curY - 1)
        pathCounts[curX to curY] = pathCount
    }

    throw Exception("Should not happen")
}