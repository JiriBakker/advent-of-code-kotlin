import kotlin.math.abs

fun day20a(input: List<String>, minSave: Int = 100) =
    input.parseGrid()
        .findPath()
        ?.getPathPositions()
        ?.countPossibleCheats(maxCheatDistance = 2, minSave = minSave)

fun day20b(input: List<String>, minSave: Int = 100) =
       input.parseGrid()
        .findPath()
        ?.getPathPositions()
        ?.countPossibleCheats(maxCheatDistance = 20, minSave = minSave)

private fun List<String>.parseGrid(): List<CharArray> {
    return this.map { it.toCharArray() }
}

private fun List<CharArray>.findPos(char: Char): Pair<Int, Int> {
    for (y in this.indices) {
        for (x in this[y].indices) {
            if (this[y][x] == char) {
                return x to y
            }
        }
    }

    error("No start position found")
}

private fun List<CharArray>.findPath(maxLength: Int = Int.MAX_VALUE): PathState? {
    val grid = this
    val (startX, startY) = grid.findPos('S')
    val (endX, endY) = grid.findPos('E')

    val queue = mutableListOf(PathState(startX, startY, null))
    val visited = mutableSetOf<Pair<Int, Int>>()


    while (queue.isNotEmpty()) {
        val state = queue.removeFirst()
        val (x, y) = state

        fun addToQueue(nextX: Int, nextY: Int) {
            if (nextX < 0 || nextX >= grid[0].size || nextY < 0 || nextY >= grid.size) {
                return
            }

            if (state.length + 1 >= maxLength) {
                return
            }

            if (visited.contains(nextX to nextY)) {
                return
            }

            if (grid[nextY][nextX] == '#') {
                return
            }

            queue.add(PathState(nextX, nextY, state))
        }

        if (x == endX && y == endY) {
            println("Found path with length ${state.length}")
            return state
        }

        if (!visited.add(x to y)) {
            continue
        }

        addToQueue(x + 1, y)
        addToQueue(x - 1, y)
        addToQueue(x, y + 1)
        addToQueue(x, y - 1)
    }

    return null
}

private fun List<Pair<Int,Int>>.countPossibleCheats(maxCheatDistance: Int, minSave: Int): Int {
    var possibleCheatCount = 0

    for (i in indices) {
        val (x1, y1) = this[i]
        for (j in i until this.size) {
            val (x2, y2) = this[j]
            val cheatManhattanDistance = abs(x1 - x2) + abs(y1 - y2)
            if (cheatManhattanDistance > maxCheatDistance) {
                continue
            }

            val distanceOnNormalPath = j - i
            val distanceSaved = distanceOnNormalPath - cheatManhattanDistance
            if (distanceSaved >= minSave) {
                possibleCheatCount++
            }
        }
    }

    return possibleCheatCount
}

private data class PathState(val x: Int, val y: Int, val parent: PathState?) {
    val length: Int by lazy { parent?.length?.plus(1) ?: 0 }

    fun getPathPositions(): List<Pair<Int,Int>> {
        val positions = mutableListOf<Pair<Int,Int>>()
        var cur: PathState? = this
        while (cur != null) {
            positions.add(cur.x to cur.y)
            cur = cur.parent
        }
        return positions
    }
}

