package v2020

private fun iterateGrid(
    grid: List<List<Char>>,
    useDeepSearch: Boolean = false,
    filledSeatsLimit: Int = 4
): Pair<List<List<Char>>, Boolean> {
    val nextGrid = grid.map { it.toMutableList() }
    var hasChanged = false

    fun getNeighbours(x: Int, y: Int): List<Char> {
        fun deepSearchNeighbour(xDelta: Int, yDelta: Int, steps: Int = 1): Char? {
            val neighbour = grid.getOrNull(y + yDelta * steps)?.getOrNull(x + xDelta * steps)
            if (useDeepSearch && neighbour == '.') {
                return deepSearchNeighbour(xDelta, yDelta, steps + 1)
            }
            return neighbour
        }

        return listOfNotNull(
            deepSearchNeighbour(-1, -1),
            deepSearchNeighbour(0, -1),
            deepSearchNeighbour(1, -1),
            deepSearchNeighbour(-1, 0),
            deepSearchNeighbour(1, 0),
            deepSearchNeighbour(-1, 1),
            deepSearchNeighbour(0, 1),
            deepSearchNeighbour(1, 1)
        )
    }

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            val cell = grid[y][x]
            if (cell == '.') {
                nextGrid[y][x] = '.'
            } else {
                val neighbours = getNeighbours(x, y)
                if (cell == 'L' && neighbours.none { it == '#' }) {
                    nextGrid[y][x] = '#'
                    hasChanged = true
                } else if (cell == '#' && neighbours.count { it == '#' } >= filledSeatsLimit) {
                    nextGrid[y][x] = 'L'
                    hasChanged = true
                }
                else {
                    nextGrid[y][x] = cell
                }
            }
        }
    }

    return nextGrid to hasChanged
}

fun day11a(input: List<String>): Int {
    var grid = input.map { it.toCharArray().toList() }

    while (true) {
        val (nextGrid, hasChanged) = iterateGrid(grid)
        if (!hasChanged) {
            return nextGrid.sumOf { row -> row.count { it == '#' } }
        }
        grid = nextGrid
    }
}

fun day11b(input: List<String>): Int {
    var grid = input.map { it.toCharArray().toList() }

    while (true) {
        val (nextGrid, hasChanged) = iterateGrid(grid, true, 5)
        if (!hasChanged) {
            return nextGrid.sumOf { row -> row.count { it == '#' } }
        }
        grid = nextGrid
    }
}

