package days.day18

private enum class CellType {
    OPEN,
    LUMBERYARD,
    WOODED
}

private class Cell(val x: Int, val y: Int, var type: CellType) {
    var nrOfOpenNeighbours = 0
    var nrOfLumberyardNeighbours = 0
    var nrOfWoodedNeighbours = 0

    var prevType = type
        private set

    var prevNrOfOpenNeighbours = 0
        private set

    var prevNrOfLumberyardNeighbours = 0
        private set

    var prevNrOfWoodedNeighbours = 0
        private set

    fun copyToPrev() {
        prevType = type
        prevNrOfLumberyardNeighbours = nrOfLumberyardNeighbours
        prevNrOfOpenNeighbours = nrOfOpenNeighbours
        prevNrOfWoodedNeighbours = nrOfWoodedNeighbours
    }
}

private typealias Grid = Array<Array<Cell>>

private fun copyToPrev(grid: Grid) {
    grid.forEach { it.forEach { cell -> cell.copyToPrev() } }
}

private fun print(grid: Grid) {
    grid.forEach {
        it.forEach { cell ->
            print(when (cell.type) {
                CellType.OPEN -> '.'
                CellType.LUMBERYARD -> '#'
                else -> '|'
            })
        }
        println()
    }
}

private fun parse(inputLines: List<String>): Grid {
    val grid = inputLines.mapIndexed { y, row ->
        row.mapIndexed { x, type ->
            Cell(x, y,
                when (type) {
                    '.' -> CellType.OPEN
                    '#' -> CellType.LUMBERYARD
                    else -> CellType.WOODED
                }
            )
        }.toTypedArray()
    }.toTypedArray()

    grid.forEach {
        it.forEach { cell ->
            updateNeighbours(grid, cell.x, cell.y) { neighbour ->
                when (cell.type) {
                    CellType.OPEN -> neighbour.nrOfOpenNeighbours++
                    CellType.LUMBERYARD -> neighbour.nrOfLumberyardNeighbours++
                    else -> neighbour.nrOfWoodedNeighbours++
                }
            }
        }
    }

    copyToPrev(grid)

    return grid
}

private fun updateNeighbours(grid: Grid, x: Int, y: Int, updateOperation: (Cell) -> Unit) {
    fun updateIfPossible(x: Int, y: Int) {
        if (y >= 0 && x >= 0 && y < grid.size && x < grid[0].size) {
            updateOperation(grid[y][x])
        }
    }

    updateIfPossible(x, y - 1)
    updateIfPossible(x, y + 1)
    updateIfPossible(x - 1, y)
    updateIfPossible(x + 1, y)
    updateIfPossible(x - 1, y - 1)
    updateIfPossible(x - 1, y + 1)
    updateIfPossible(x + 1, y - 1)
    updateIfPossible(x + 1, y + 1)
}

private fun iterate(grid: Grid, nrOfIterations: Int): Int {
    var nrOfWooded = grid.sumBy { it.count { cell -> cell.type == CellType.WOODED } }
    var nrOfLumberyards = grid.sumBy { it.count { cell -> cell.type == CellType.LUMBERYARD } }

    val prevScores = mutableMapOf<Int, Int>()
    fun hasRecurrence(iteration: Int): Boolean {
        val score = nrOfWooded * nrOfLumberyards
        val prevIteration = prevScores[score]
        if (prevIteration == null) {
            prevScores[score] = iteration
            return false
        }
        val interval = iteration - prevIteration
        return interval > 1 && (nrOfIterations - iteration) % interval == 0
    }

    for (iteration in 0 until nrOfIterations) {
        if (hasRecurrence(iteration)) {
            break
        }

        grid.forEach {
            it.forEach { cell ->
                if (cell.prevType == CellType.OPEN) {
                    if (cell.prevNrOfWoodedNeighbours >= 3) {
                        cell.type = CellType.WOODED
                        nrOfWooded++
                        updateNeighbours(grid, cell.x, cell.y) { it.nrOfOpenNeighbours--; it.nrOfWoodedNeighbours++ }
                    }
                } else if (cell.prevType == CellType.WOODED) {
                    if (cell.prevNrOfLumberyardNeighbours >= 3) {
                        cell.type = CellType.LUMBERYARD
                        nrOfWooded--
                        nrOfLumberyards++
                        updateNeighbours(grid, cell.x, cell.y) { it.nrOfWoodedNeighbours--; it.nrOfLumberyardNeighbours++ }
                    }
                } else if (cell.prevType == CellType.LUMBERYARD) {
                    if (!(cell.prevNrOfWoodedNeighbours >= 1 && cell.prevNrOfLumberyardNeighbours >= 1)) {
                        cell.type = CellType.OPEN
                        nrOfLumberyards--
                        updateNeighbours(grid, cell.x, cell.y) { it.nrOfLumberyardNeighbours--; it.nrOfOpenNeighbours++ }
                    }
                }
            }
        }
        copyToPrev(grid)
    }
    return nrOfWooded * nrOfLumberyards
}

fun day18a(inputLines: List<String>): Int {
    val grid = parse(inputLines)
    return iterate(grid, 10)
}

fun day18b(inputLines: List<String>): Int {
    val grid = parse(inputLines)
    return iterate(grid, 1000000000)
}