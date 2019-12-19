package v2015.days.day18

private fun parseGrid(input: List<String>): List<BooleanArray> {
    return input.map { line -> line.map { it == '#' }.toBooleanArray() }
}

private fun iterateGrid(grid: List<BooleanArray>): List<BooleanArray> {
    fun isOn(x: Int, y: Int): Boolean = y >= 0 && x >= 0 && y < grid.size && x < grid[y].size && grid[y][x]

    val output = MutableList(grid.size) { BooleanArray(grid[0].size) }

    for (y in 0 until grid.size) {
        for (x in 0 until grid[y].size) {
            val state = grid[y][x]
            val onNeighbours = listOf(
                x - 1 to y - 1,
                x     to y - 1,
                x + 1 to y - 1,
                x - 1 to y,
                x + 1 to y,
                x - 1 to y + 1,
                x     to y + 1,
                x + 1 to y + 1
            )
                .count { isOn(it.first, it.second) }

            output[y][x] = when (state) {
                true -> onNeighbours == 2 || onNeighbours == 3
                false -> onNeighbours == 3
            }
        }
    }

    return output
}

fun day18a(input: List<String>, steps: Int = 100): Int {
    var grid = parseGrid(input)

    repeat(steps) { grid = iterateGrid(grid) }

    return grid.map { row -> row.count { it } }.sum()
}

fun day18b(input: List<String>, steps: Int = 100): Int {
    var grid = parseGrid(input)

    fun turnCornersOn() {
        grid[0][0] = true
        grid[0][grid[0].size - 1] = true
        grid[grid.size - 1][grid[0].size - 1] = true
        grid[grid.size - 1][0] = true
    }

    turnCornersOn()

    repeat(steps) {
        grid = iterateGrid(grid)
        turnCornersOn()
    }

    return grid.map { row -> row.count { it } }.sum()

}