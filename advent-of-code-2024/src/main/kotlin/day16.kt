import java.util.PriorityQueue

fun day16a(input: List<String>) =
    input
        .map { it.toCharArray() }
        .findPaths()
        .first()
        .score

fun day16b(input: List<String>) =
    input
        .map { it.toCharArray() }
        .findPaths()
        .toList()
        .countUniqueTiles()

private fun List<CharArray>.findPaths(): Sequence<State> {
    val grid = this
    val start = grid.findStart()
    val end = grid.findEnd()

    val queue = PriorityQueue<State> { a, b -> a.score.compareTo(b.score) }
    queue.add(State(start.first, start.second, Direction.EAST, 0))

    var lowestScore = Int.MAX_VALUE

    fun canMove(x: Int, y: Int, score: Int): Boolean {
        return y in grid.indices && x in grid[y].indices && grid[y][x] != '#' && score <= lowestScore
    }

    val visited = mutableMapOf<Triple<Int, Int, Direction>, Int>()

    return sequence {
        while (queue.isNotEmpty()) {
            val state = queue.poll()
            val (x, y, direction, score) = state

            if (score > lowestScore) {
                continue
            }

            val stateKey = Triple(x, y, direction)
            if (stateKey in visited && visited[stateKey]!! < score) {
                continue
            }
            visited[stateKey] = score

            if (x == end.first && y == end.second) {
                yield(State(x, y, direction, score, state))
                lowestScore = score
            }

            fun move(direction: Direction, cost: Int) {
                val (dx, dy) = direction.getDeltas()
                val nextX = x + dx
                val nextY = y + dy
                if (canMove(nextX, nextY, score + 1)) {
                    queue.add(State(nextX, nextY, direction, score + cost, state))
                }
            }

            move(direction, 1)
            move(direction.turnRight(), 1001)
            move(direction.turnLeft(), 1001)
        }
    }
}

private fun List<CharArray>.findStart(): Pair<Int, Int> {
    this.indices.forEach { y ->
        this[y].indices.forEach { x ->
            if (this[y][x] == 'S') {
                return Pair(x, y)
            }
        }
    }
    throw IllegalArgumentException("No start found")
}

private fun List<CharArray>.findEnd(): Pair<Int, Int> {
    this.indices.forEach { y ->
        this[y].indices.forEach { x ->
            if (this[y][x] == 'E') {
                return Pair(x, y)
            }
        }
    }
    throw IllegalArgumentException("No end found")
}

private enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun turnLeft(): Direction {
        return when (this) {
            NORTH -> WEST
            EAST  -> NORTH
            SOUTH -> EAST
            WEST  -> SOUTH
        }
    }

    fun turnRight(): Direction {
        return when (this) {
            NORTH -> EAST
            EAST  -> SOUTH
            SOUTH -> WEST
            WEST  -> NORTH
        }
    }

    fun getDeltas(): Pair<Int, Int> {
        return when (this) {
            NORTH -> Pair(0, -1)
            EAST  -> Pair(1, 0)
            SOUTH -> Pair(0, 1)
            WEST  -> Pair(-1, 0)
        }
    }
}

private data class State(
    val x: Int,
    val y: Int,
    val direction: Direction,
    val score: Int,
    val parent: State? = null
)

private fun List<State>.countUniqueTiles(): Int {
    val states = this
    return sequence {
        states.forEach { state ->
            var cur: State? = state
            while (cur != null) {
                yield(Pair(cur.x, cur.y))
                cur = cur.parent
            }
        }
    }.distinct().count()
}