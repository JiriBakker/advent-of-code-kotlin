import java.util.PriorityQueue

fun day16a(input: List<String>) =
    input
        .map(String::toCharArray)
        .findPaths()
        .first()
        .score

fun day16b(input: List<String>) =
    input
        .map(String::toCharArray)
        .findPaths()
        .toList()
        .countUniqueTiles()

private fun List<CharArray>.findPaths(): Sequence<State> {
    val start = this.find('S')
    val end = this.find('E')

    val queue = PriorityQueue<State> { a, b -> a.score.compareTo(b.score) }
    queue.add(State(start.first, start.second, Direction.EAST, 0))

    var lowestScore = Int.MAX_VALUE

    fun canMove(x: Int, y: Int, score: Int): Boolean {
        return y in this.indices && x in this[y].indices && this[y][x] != '#' && score <= lowestScore
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
                val nextX = x + direction.dx
                val nextY = y + direction.dy
                if (canMove(nextX, nextY, score + 1)) {
                    queue.add(State(nextX, nextY, direction, score + cost, state))
                }
            }

            move(direction, 1)
            move(direction.right, 1001)
            move(direction.left, 1001)
        }
    }
}

private fun List<CharArray>.find(target: Char): Pair<Int, Int> {
    this.indices.forEach { y ->
        this[y].indices.forEach { x ->
            if (this[y][x] == target) {
                return Pair(x, y)
            }
        }
    }
    throw IllegalArgumentException("$target not found")
}

private enum class Direction(val value: Int, val dx: Int, val dy: Int) {
    NORTH(0, 0, -1),
    EAST(1, 1, 0),
    SOUTH(2, 0, 1),
    WEST(3, -1, 0);

    val left get() = entries[(value - 1 + 4) % 4]
    val right get() = entries[(value + 1) % 4]
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