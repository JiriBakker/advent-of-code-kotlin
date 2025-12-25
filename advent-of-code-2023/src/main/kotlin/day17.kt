import util.allEqual
import util.countDistinct
import java.util.PriorityQueue

private data class PathState(
    val x: Int,
    val y: Int,
    val heatLoss: Int,
    val deltas: List<Pair<Int, Int>>
)

fun day17a(input: List<String>): Int {
    val grid = input.map { line -> line.map { char -> char.digitToInt() } }

    val toVisit = PriorityQueue<PathState> { a, b ->
        val compareResult = a.heatLoss.compareTo(b.heatLoss)
        if (compareResult != 0) compareResult
        else {
            val aSameDirection = countStraightSteps(a.deltas)
            val bSameDirection = countStraightSteps(b.deltas)
            aSameDirection.compareTo(bSameDirection)
        }

    }
    toVisit.add(PathState(0, 0, 0, emptyList()))

    val visited = mutableSetOf<Triple<Pair<Int, Int>, Int, Pair<Int, Int>?>>()
    val target = input[0].indices.last to input.indices.last

    while (toVisit.isNotEmpty()) {
        val (x, y, heatLoss, deltas) = toVisit.remove()

        if (x == 5 && y == 0) {
            val a = 1
        }

        if (!visited.add(Triple((x to y), countStraightSteps(deltas), deltas.lastOrNull()))) continue

        if (x to y == target) {
            return heatLoss
        }

        val toAdd =
            listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
            )
                .filter { (dx, dy) ->
                    // Stay within bounds
                    y + dy in grid.indices && x + dx in grid[0].indices
                }
                .filter { (dx, dy) ->
                    // Cannot turn around
                    deltas.isEmpty() || deltas.last() != -dx to -dy
                }
                .filter { (dx, dy) ->
                    // No more than three steps in same direction
                    deltas.size < 3 || !deltas.takeLast(3).allEqual() || deltas.last() != dx to dy
                }
                .map { (dx, dy) ->
                    PathState(x + dx, y + dy, heatLoss + grid[y + dy][x + dx], deltas.plus(dx to dy))
                }

            toVisit.addAll(toAdd)

    }

    throw Exception("No valid path found")
}

fun day17b(input: List<String>): Int {
    val grid = input.map { line -> line.map { char -> char.digitToInt() } }

    val toVisit = PriorityQueue<PathState> { a, b ->
        val compareResult = a.heatLoss.compareTo(b.heatLoss)
        if (compareResult != 0) compareResult
        else {
            val aSameDirection = countStraightSteps(a.deltas)
            val bSameDirection = countStraightSteps(b.deltas)
            aSameDirection.compareTo(bSameDirection)
        }

    }
    toVisit.add(PathState(0, 0, 0, emptyList()))

    val visited = mutableSetOf<Triple<Pair<Int, Int>, Int, Pair<Int, Int>?>>()
    val target = input[0].indices.last to input.indices.last

    while (toVisit.isNotEmpty()) {
        val (x, y, heatLoss, deltas) = toVisit.remove()

        if (x == 5 && y == 0) {
            val a = 1
        }

        if (!visited.add(Triple((x to y), countStraightSteps(deltas, 10), deltas.lastOrNull()))) continue

        if (x to y == target && countStraightSteps(deltas, 10) >= 4) {
            return heatLoss
        }

        val toAdd =
            listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
            )
                .filter { (dx, dy) ->
                    // Stay within bounds
                    y + dy in grid.indices && x + dx in grid[0].indices
                }
                .filter { (dx, dy) ->
                    // Cannot turn around
                    deltas.isEmpty() || deltas.last() != -dx to -dy
                }
                .filter { (dx, dy) ->
                    // No more than three steps in same direction
                    deltas.size < 10 || countStraightSteps(deltas, 10) < 10 || deltas.last() != dx to dy
                }
                .filter { (dx, dy) ->
                    deltas.isEmpty() || dx to dy == deltas.last() || countStraightSteps(deltas, 10) >= 4
                }
                .map { (dx, dy) ->
                    PathState(x + dx, y + dy, heatLoss + grid[y + dy][x + dx], deltas.plus(dx to dy))
                }

        toVisit.addAll(toAdd)

    }

    throw Exception("No valid path found")
}

private fun countStraightSteps2(deltas: List<Pair<Int, Int>>, nrToCheck: Int = 3): Int {
    if (deltas.isEmpty()) return 0

    val last = deltas.last()
    if (deltas.size >= 2 && deltas[deltas.size - 2] == last) {
        if (deltas.size >= 3 && deltas[deltas.size - 3] == last) {
            return 3
        }
        return 2
    }
    return 1
}

private fun countStraightSteps(deltas: List<Pair<Int, Int>>, nrToCheck: Int = 3): Int {
    if (deltas.isEmpty()) return 0

    val last = deltas.last()
    for (i in 0 until nrToCheck) {
        if (i >= deltas.size || deltas[deltas.size - 1 - i] != last) {
            return i
        }
    }

    return nrToCheck
}
