package days.day18

private enum class CellType {
    OPEN,
    LUMBERYARD,
    WOODED
}

private data class Cell(val x: Int, val y: Int, var type: CellType) {
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
    for (y in 0 until grid.size) {
        for (x in 0 until grid[y].size) {
            grid[y][x].copyToPrev()
        }
    }
}

private fun print(grid: Grid) {
    for (y in 0 until grid.size) {
        for (x in 0 until grid[y].size) {
            print(when (grid[y][x].type) {
                CellType.OPEN -> '.'
                CellType.LUMBERYARD -> '#'
                else -> '|'
            })
        }
        println()
    }
}

private fun parse(inputLines: List<String>): Grid {
    val listGrid: MutableList<MutableList<Cell>> = mutableListOf()
    for (y in 0 until inputLines.size) {
        listGrid.add(mutableListOf())
        for (x in 0 until inputLines[y].length) {
            val type = when (inputLines[y][x]) {
                '.' -> CellType.OPEN
                '#' -> CellType.LUMBERYARD
                else -> CellType.WOODED
            }
            listGrid[y].add(Cell(x, y, type))
        }
    }

    val grid = listGrid.map { it.toTypedArray() }.toTypedArray()
    for (y in 0 until inputLines.size) {
        for (x in 0 until inputLines[y].length) {
            val type = grid[y][x].type
            updateNeighbours(grid, x, y) {
                when (type) {
                    CellType.OPEN -> it.nrOfOpenNeighbours++
                    CellType.LUMBERYARD -> it.nrOfLumberyardNeighbours++
                    else -> it.nrOfWoodedNeighbours++
                }
            }
        }
    }

    copyToPrev(grid)

    return grid
}

private fun updateNeighbours(grid: Grid, x: Int, y: Int, operation: (Cell) -> Unit) {
    if (y > 0) {
        operation(grid[y - 1][x]) // N
        if (x > 0) {
            operation(grid[y - 1][x - 1]) // NW
        }
        if (x < grid[0].size - 1) {
            operation(grid[y - 1][x + 1]) // NE
        }
    }
    if (x > 0) {
        operation(grid[y][x - 1]) // W
    }
    if (x < grid[0].size - 1) {
        operation(grid[y][x + 1]) // E
    }
    if (y < grid.size - 1) {
        operation(grid[y + 1][x]) // S
        if (x > 0) {
            operation(grid[y + 1][x - 1]) // SW
        }
        if (x < grid[0].size - 1) {
            operation(grid[y + 1][x + 1]) // SE
        }
    }
}

private fun iterate(grid: Grid, nrOfIterations: Int): Int {
    var nrOfWooded = grid.sumBy { it.count { cell -> cell.type == CellType.WOODED } }
    var nrOfLumberyards = grid.sumBy { it.count { cell -> cell.type == CellType.LUMBERYARD } }

    val seenScores = mutableMapOf<Int, Int>()
    fun hasRecurrence(iteration: Int): Boolean {
        val score = nrOfWooded * nrOfLumberyards
        val prevIterations = seenScores[score]
        if (prevIterations == null) {
            seenScores[score] = iteration
            return false
        }
        val interval = iteration - prevIterations
        return interval != 1 && (nrOfIterations - iteration) % interval == 0
    }

    for (iteration in 0 until nrOfIterations) {
        if (hasRecurrence(iteration)) {
            break
        }

        for (y in 0 until grid.size) {
            for (x in 0 until grid[y].size) {
                val cell = grid[y][x]
                if (cell.prevType == CellType.OPEN) {
                    if (cell.prevNrOfWoodedNeighbours >= 3) {
                        cell.type = CellType.WOODED
                        nrOfWooded++
                        updateNeighbours(grid, x, y) { it.nrOfOpenNeighbours--; it.nrOfWoodedNeighbours++ }
                    }
                } else if (cell.prevType == CellType.WOODED) {
                    if (cell.prevNrOfLumberyardNeighbours >= 3) {
                        cell.type = CellType.LUMBERYARD
                        nrOfWooded--
                        nrOfLumberyards++
                        updateNeighbours(grid, x, y) { it.nrOfWoodedNeighbours--; it.nrOfLumberyardNeighbours++ }
                    }
                } else if (cell.prevType == CellType.LUMBERYARD) {
                    if (!(cell.prevNrOfWoodedNeighbours >= 1 && cell.prevNrOfLumberyardNeighbours >= 1)) {
                        cell.type = CellType.OPEN
                        nrOfLumberyards--
                        updateNeighbours(grid, x, y) { it.nrOfLumberyardNeighbours--; it.nrOfOpenNeighbours++ }
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