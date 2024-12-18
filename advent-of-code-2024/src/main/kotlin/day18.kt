import util.keepCounting

fun day18a(input: List<String>, nrOfBytesToFall: Int = 1024) =
    input.findPath(nrOfBytesToFall)!!

fun day18b(input: List<String>) =
    keepCounting()
        .first { input.findPath(it) == null }
        .let { input[it - 1] }

private fun List<String>.parseGrid(nrOfBytesToFall: Int): List<CharArray> {
    val width = this.maxOf { it.split(",").first().toInt() } + 1
    val height = this.maxOf { it.split(",").last().toInt() } + 1

    val grid = MutableList(height) { CharArray(width) { '.' } }

    for (line in this.take(nrOfBytesToFall)) {
        val (x, y) = line.split(",").map { it.toInt() }
        grid[y][x] = '#'
    }

    return grid
}

private fun List<String>.findPath(nrOfBytesToFall: Int): Int? {
    val grid = this.parseGrid(nrOfBytesToFall)

    val width = grid[0].size
    val height = grid.size

    val queue = mutableListOf<Triple<Int, Int, Int>>()
    val visited = mutableSetOf<Pair<Int, Int>>()

    queue.add(Triple(0, 0, 0))

    while (queue.isNotEmpty()) {
        val (x, y, length) = queue.removeAt(0)

        if (x < 0 || x >= width || y < 0 || y >= height) {
            continue
        }

        if (x == width - 1 && y == height - 1) {
            return length
        }

        if (grid[y][x] == '#') {
            continue
        }

        if (!visited.add(x to y)) {
            continue
        }

        queue.add(Triple(x - 1, y, length + 1))
        queue.add(Triple(x + 1, y, length + 1))
        queue.add(Triple(x, y - 1, length + 1))
        queue.add(Triple(x, y + 1, length + 1))
    }

    return null
}
