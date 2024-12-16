import java.util.PriorityQueue

fun day16a(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }

    val start = grid.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == 'S') Pair(x, y) else null }.filterNotNull() }.flatten().first()
    val end = grid.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == 'E') Pair(x, y) else null }.filterNotNull() }.flatten().first()

    val queue = PriorityQueue<State> { a, b -> a.score.compareTo(b.score)}
    queue.add(State(start.first, start.second, Direction.EAST, 0))

    val visited = mutableSetOf<Triple<Int, Int, Direction>>()

    fun canMove(x: Int, y: Int): Boolean {
        return y in grid.indices && x in grid[y].indices && grid[y][x] != '#'
    }

    while (queue.isNotEmpty()) {
        val (x, y, direction, score) = queue.poll()
        if (x == end.first && y == end.second) {
            return score
        }

        if (!visited.add(Triple(x, y, direction))) {
            continue
        }

        val (dx, dy) = direction.getDeltas()

        if (canMove(x + dx, y + dy)) {
            queue.add(State(x + dx, y + dy, direction, score + 1))
        }

        val leftDirection = when (direction) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            Direction.WEST -> Direction.SOUTH
        }
        val (leftDx, leftDy) = leftDirection.getDeltas()

        if (canMove(x + leftDx, y + leftDy)) {
            queue.add(State(x + leftDx, y + leftDy, leftDirection, score + 1001))
        }

        val rightDirection = when (direction) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST -> Direction.NORTH
        }
        val (rightDx, rightDy) = rightDirection.getDeltas()

        if (canMove(x + rightDx, y + rightDy)) {
            queue.add(State(x + rightDx, y + rightDy, rightDirection, score + 1001))
        }
    }

    error("No path found")
}

fun day16b2(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }

    val start = grid.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == 'S') Pair(x, y) else null }.filterNotNull() }.flatten().first()
    val end = grid.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == 'E') Pair(x, y) else null }.filterNotNull() }.flatten().first()

    val queue = PriorityQueue<State> { a, b -> a.score.compareTo(b.score)}
    queue.add(State(start.first, start.second, Direction.EAST, 0))

    val visited = mutableMapOf<Triple<Int, Int, Direction>, Int>()

    fun canMove(x: Int, y: Int, score: Int): Boolean {
        return score <= 90460 && y in grid.indices && x in grid[y].indices && grid[y][x] != '#'
    }

    val bestPaths = mutableListOf<State>()

    var bestScore = 90460

    while (queue.isNotEmpty()) {
        val state = queue.poll()
        val (x, y, direction, score) = state
        if (x == end.first && y == end.second) {
            println("Found path with score $score")
            if (bestPaths.isNotEmpty() && bestPaths[0].score < score) {
                return countUniqueTiles(bestPaths)
            }
            bestPaths.add(State(x, y, direction, score))
            // bestScore = score
        }

        val key = Triple(x, y, direction)
        if (key in visited && visited[key]!! < score) {
            continue
        }
        visited[key] = score

        val (dx, dy) = direction.getDeltas()

        if (canMove(x + dx, y + dy, score + 1)) {
            queue.add(State(x + dx, y + dy, direction, score + 1, state))
        }

        val leftDirection = when (direction) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            Direction.WEST -> Direction.SOUTH
        }
        val (leftDx, leftDy) = leftDirection.getDeltas()

        if (canMove(x + leftDx, y + leftDy, score + 1001)) {
            queue.add(State(x + leftDx, y + leftDy, leftDirection, score + 1001, state))
        }

        val rightDirection = when (direction) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST -> Direction.NORTH
        }
        val (rightDx, rightDy) = rightDirection.getDeltas()

        if (canMove(x + rightDx, y + rightDy, score + 1001)) {
            queue.add(State(x + rightDx, y + rightDy, rightDirection, score + 1001, state))
        }
    }

    if (bestPaths.isNotEmpty()) {
        return countUniqueTiles(bestPaths)
    }

    error("No path found")
}

fun day16b(input: List<String>): Int {
    val grid = input.map { it.toCharArray() }

    val start = grid.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == 'S') Pair(x, y) else null }.filterNotNull() }.flatten().first()
    val end = grid.mapIndexed { y, row -> row.mapIndexed { x, c -> if (c == 'E') Pair(x, y) else null }.filterNotNull() }.flatten().first()

    val queue = PriorityQueue<State> { a, b -> a.score.compareTo(b.score)}
    queue.add(State(start.first, start.second, Direction.EAST, 0))

    fun canMove(x: Int, y: Int, score: Int): Boolean {
        return score <= 90460 && y in grid.indices && x in grid[y].indices && grid[y][x] != '#'
    }

    val visited = mutableMapOf<Triple<Int, Int, Direction>, Int>()

    val bestPaths = mutableListOf<State>()

    var bestScore = 90460

    var i = 0L
    while (queue.isNotEmpty()) {

        val state = queue.poll()
        val (x, y, direction, score) = state

        if (i % 100000L < 1L) {
            println("$i queue size: ${queue.size} checking score $score at $x, $y")
        }
        i++

        if (score > bestScore) {
            continue
        }

        val key = Triple(x, y, direction)
        if (key in visited && visited[key]!! < score) {
            continue
        }
        visited[key] = score

        // println("Checking $x, $y, $direction, $score")
        if (x == end.first && y == end.second) {
            println("Found path with score $score")
            if (bestPaths.isNotEmpty() && bestPaths[0].score < score) {
                return countUniqueTiles(bestPaths)
            }
            bestPaths.add(State(x, y, direction, score, state))
            bestScore = score
        }

        val (dx, dy) = direction.getDeltas()

        if (canMove(x + dx, y + dy, score + 1)) {
            val nextX = x + dx
            val nextY = y + dy
            queue.add(State(nextX, nextY, direction, score + 1, state))
        }

        val leftDirection = when (direction) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST -> Direction.NORTH
            Direction.SOUTH -> Direction.EAST
            Direction.WEST -> Direction.SOUTH
        }
        val (leftDx, leftDy) = leftDirection.getDeltas()

        if (canMove(x + leftDx, y + leftDy, score + 1001)) {
            val leftX = x + leftDx
            val leftY = y + leftDy
            queue.add(State(leftX, leftY, leftDirection, score + 1001, state))
        }

        val rightDirection = when (direction) {
            Direction.NORTH -> Direction.EAST
            Direction.EAST -> Direction.SOUTH
            Direction.SOUTH -> Direction.WEST
            Direction.WEST -> Direction.NORTH
        }
        val (rightDx, rightDy) = rightDirection.getDeltas()

        if (canMove(x + rightDx, y + rightDy, score + 1001)) {
            val rightX = x + rightDx
            val rightY = y + rightDy
            queue.add(State(rightX, rightY, rightDirection, score + 1001, state))
        }
    }

    if (bestPaths.isNotEmpty()) {
        return countUniqueTiles(bestPaths)
    }

    error("No path found")
}

private enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun getDeltas(): Pair<Int, Int> {
        return when (this) {
            NORTH -> Pair(0, -1)
            EAST -> Pair(1, 0)
            SOUTH -> Pair(0, 1)
            WEST -> Pair(-1, 0)
        }
    }
}

private data class State(val x: Int, val y: Int, val direction: Direction, val score: Int, val parent: State? = null) {
    fun hasVisited(x: Int, y: Int, direction: Direction): Boolean {
        var current: State? = this.parent
        while (current != null) {
            if (current.x == x && current.y == y && current.direction == direction) {
                return true
            }
            current = current.parent
        }
        return false
    }
}

private data class VisitedState(val x: Int, val y: Int, val direction: Direction, val score: Int)

private fun countUniqueTiles(bestPaths: List<State>): Int {
    println("Best paths: ${bestPaths.size}")
    val uniqueTiles = mutableSetOf<Pair<Int, Int>>()
    bestPaths.forEach { state ->
        var cur: State? = state
        while (cur != null) {
            uniqueTiles.add(Pair(cur.x, cur.y))
            cur = cur.parent
        }
    }
    return uniqueTiles.size
}