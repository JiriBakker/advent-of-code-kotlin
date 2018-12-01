package extra

import java.util.*

private data class MazeCell(val y: Int, val x: Int, var distance: Int, val char: Char, val iteration: Int = 0) {
    fun isTopOpen(): Boolean {
        return char in setOf('║', '╠', '╚', '╝', '╬', '╩', '╣')
    }

    fun isBottomOpen(): Boolean {
        return char in setOf('║', '╠', '╔', '╗', '╬', '╦', '╣')
    }

    fun isLeftOpen(): Boolean {
        return char in setOf('╗', '╦', '╝', '╬', '╩', '═', '╣')
    }

    fun isRightOpen(): Boolean {
        return char in setOf('╔', '╠', '╦', '╚', '╬', '╩', '═')
    }
}

private infix fun Int.safeMod(mod: Int): Int {
    var value = this
    while (value < 0) {
        value += mod
    }
    return value % mod
}

private class Maze private constructor(private val mazeChars: List<List<Char>>) {
    private val visitedCells: MutableMap<String, MazeCell> = mutableMapOf()

    // We assume the maze is a square
    private val height = mazeChars.count()
    private val width = mazeChars.count()

    companion object {
        fun parse(mazeLines: List<String>): Maze {
            val maze: MutableList<MutableList<Char>> = mutableListOf()
            for (y in 0 until mazeLines.count()) {
                val mazeRow: MutableList<Char> = mutableListOf()
                for (x in 0 until mazeLines[y].count()) {
                    mazeRow.add(x, mazeLines[y][x])
                }
                maze.add(y, mazeRow)
            }
            return Maze(maze)
        }
    }

    private fun getReachableNeighbours(cell: MazeCell): List<MazeCell> {
        val reachableNeighbours: MutableList<MazeCell> = mutableListOf()
        if (cell.y > 0 && cell.isTopOpen()) {
            val cellAbove = cellAt(cell.y - 1, cell.x, cell.iteration)
            if (cellAbove.isBottomOpen()) {
                reachableNeighbours.add(cellAbove)
            }
        }
        if (cell.y < height - 1 && cell.isBottomOpen()) {
            val cellBelow = cellAt(cell.y + 1, cell.x, cell.iteration)
            if (cellBelow.isTopOpen()) {
                reachableNeighbours.add(cellBelow)
            }
        }
        if (cell.x > 0 && cell.isLeftOpen()) {
            val cellLeft = cellAt(cell.y, cell.x - 1, cell.iteration)
            if (cellLeft.isRightOpen()) {
                reachableNeighbours.add(cellLeft)
            }
        }
        if (cell.x < width - 1  && cell.isRightOpen()) {
            val cellRight = cellAt(cell.y, cell.x + 1, cell.iteration)
            if (cellRight.isLeftOpen()) {
                reachableNeighbours.add(cellRight)
            }
        }
        return reachableNeighbours
    }

    private fun findChar(y: Int, x: Int, iteration: Int): Char {
        val (initialY, initialX) = applyShifts(y, x, iteration downTo 0, Int::minus)
        return mazeChars[initialY][initialX]
    }

    private fun applyShifts(y: Int, x: Int, iterator: IntProgression, operator: (Int, Int) -> Int): Pair<Int, Int> {
        return iterator.fold(Pair(y, x)) { (curY, curX): Pair<Int, Int>, iteration: Int ->
            val shiftRow = iteration % 2 == 1
            val onShiftingRow = shiftRow && curY == (iteration - 1) % height
            val onShiftingColumn = !shiftRow && curX == (iteration - 1) % width

            when {
                onShiftingRow    -> Pair(curY, operator(curX, 1) safeMod width)
                onShiftingColumn -> Pair(operator(curY, 1) safeMod height, curX)
                else             -> Pair(curY, curX)
            }
        }
    }

    private fun postAfterNextShift(cell: MazeCell): Pair<Int, Int> {
        val nextIteration = cell.iteration + 1
        return applyShifts(cell.y, cell.x, nextIteration..nextIteration, Int::plus)
    }


    private fun isBottomRightCell(cell: MazeCell): Boolean {
        return cell.x == width - 1 && cell.y == height - 1
    }

    private fun cellAt(y: Int, x: Int, iteration: Int): MazeCell {
        val hash = "${y}_${x}_$iteration"
        return visitedCells.getOrElse(hash) {
            val cell = MazeCell(y, x, Int.MAX_VALUE, findChar(y, x, iteration), iteration)
            visitedCells[hash] = cell
            cell
        }
    }

    fun cellAfterNextShift(cell: MazeCell): MazeCell {
        val (nextY, nextX) = postAfterNextShift(cell)
        return cellAt(nextY, nextX, cell.iteration + 1)
    }

    fun runDijkstra(processNeighbour: (MazeCell) -> MazeCell): Int {
        val toVisit: Queue<MazeCell> =
            PriorityQueue<MazeCell>(width * height) { p0, p1 -> p0.distance - p1.distance }

        val topLeft = cellAt(0, 0, 0)
        topLeft.distance = 0
        toVisit.offer(topLeft)

        while (toVisit.size > 0) {
            val currentCell = toVisit.poll()
            if (isBottomRightCell(currentCell)) {
                return currentCell.distance
            }

            getReachableNeighbours(currentCell).forEach { neighbourCell ->
                val processedNeighbour = processNeighbour(neighbourCell)
                if (processedNeighbour.distance == Int.MAX_VALUE) {
                    processedNeighbour.distance = Math.min(currentCell.distance + 1, processedNeighbour.distance)
                    toVisit.add(processedNeighbour)
                }
            }
        }

        throw Exception("No path from top-left to bottom-right")
    }
}

fun infiA(mazeLines: List<String>): Int {
    val maze = Maze.parse(mazeLines)
    return maze.runDijkstra { cell -> cell }
}

fun infiB(mazeLines: List<String>): Int {
    val maze = Maze.parse(mazeLines)
    return maze.runDijkstra { cell -> maze.cellAfterNextShift(cell) }
}

