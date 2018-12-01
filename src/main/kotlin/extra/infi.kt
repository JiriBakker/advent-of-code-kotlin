package extra

import java.util.*

data class MazeCell(val y: Int, val x: Int, var distance: Int, val char: Char, val iteration: Int = 0) {
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

fun hash(y: Int, x: Int, iteration: Int): String {
    return "${y}_${x}_$iteration"
}

infix fun Int.safeMod(mod: Int): Int {
    var value = this
    while (value < 0) {
        value += mod
    }
    return value % mod
}

class Maze private constructor(private val mazeChars: List<List<Char>>) {
    private val cells: MutableMap<String, MazeCell> = mutableMapOf()

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

    fun height(): Int {
        return mazeChars.count()
    }

    fun width(): Int {
        return height()
    }

    fun getReachableNeighbours(cell: MazeCell): List<MazeCell> {
        val reachableNeighbours: MutableList<MazeCell> = mutableListOf()
        if (cell.y > 0 && cell.isTopOpen()) {
            val cellAbove = cellAt(cell.y - 1, cell.x, cell.iteration)
            if (cellAbove.isBottomOpen()) {
                reachableNeighbours.add(cellAbove)
            }
        }
        if (cell.y < height() - 1 && cell.isBottomOpen()) {
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
        if (cell.x < width() - 1  && cell.isRightOpen()) {
            val cellRight = cellAt(cell.y, cell.x + 1, cell.iteration)
            if (cellRight.isLeftOpen()) {
                reachableNeighbours.add(cellRight)
            }
        }
        return reachableNeighbours
    }

    fun cellAt(y: Int, x: Int, iteration: Int): MazeCell {
        val (initialY, initialX) = initialPos(y, x, iteration)
        val hash = hash(y, x, iteration)
        return cells.getOrElse(hash) {
            val cell = MazeCell(y, x, Int.MAX_VALUE, mazeChars[initialY][initialX], iteration)
            cells[hash] = cell
            cell
        }
    }

    fun iteratePos(y: Int, x: Int, iterator: IntProgression, operator: (Int, Int) -> Int): Pair<Int, Int> {
        var curY = y
        var curX = x
        for (iteration in iterator) {
            val shiftRow = iteration % 2 == 1
            if (shiftRow) {
                if (curY == (iteration - 1) % height()) {
                    curX = operator(curX, 1) safeMod width()
                }
            }
            else {
                if (curX == (iteration - 1) % width()) {
                    curY = operator(curY, 1) safeMod height()
                }
            }
        }
        return Pair(curY, curX)
    }

    fun initialPos(y: Int, x: Int, iteration: Int): Pair<Int, Int> {
        return iteratePos(y, x, iteration downTo 0, Int::minus)
    }

    fun nextPos(cell: MazeCell): Pair<Int, Int> {
        val nextIteration = cell.iteration + 1
        return iteratePos(cell.y, cell.x, nextIteration..nextIteration, Int::plus)
    }

}

private fun runDijkstraOnMaze(mazeLines: List<String>, processNeighbours: (MazeCell, Int, Queue<MazeCell>, Maze) -> Unit): Int {
    val maze = Maze.parse(mazeLines)
    val topLeft = maze.cellAt(0, 0, 0)
    topLeft.distance = 0

    val toVisit: Queue<MazeCell> =
        PriorityQueue<MazeCell>(maze.width() * maze.height()) { p0, p1 -> p0.distance - p1.distance }

    toVisit.offer(topLeft)

    while (toVisit.size > 0) {
        val currentCell = toVisit.poll()
        if (currentCell.x == maze.width() - 1 && currentCell.y == maze.height() - 1) {
            return currentCell.distance
        }

        maze.getReachableNeighbours(currentCell).forEach { cell ->
            processNeighbours(cell, currentCell.distance + 1, toVisit, maze)
        }
    }

    throw Exception("No path from top-left to bottom-right")
}

fun infiA(mazeLines: List<String>): Int {
    return runDijkstraOnMaze(mazeLines) { cell, newDistance, toVisit, _ ->
        if (cell.distance == Int.MAX_VALUE) {
            toVisit.add(cell)
        }
        cell.distance = Math.min(newDistance, cell.distance)
    }
}

fun infiB(mazeLines: List<String>): Int {
    return runDijkstraOnMaze(mazeLines) { cell, newDistance, toVisit, maze ->
        val (nextY, nextX) = maze.nextPos(cell)
        val cellAtNextPos = maze.cellAt(nextY, nextX, cell.iteration + 1)

        if (toVisit.contains(cellAtNextPos)) {
            toVisit.remove(cellAtNextPos)
        }
        cellAtNextPos.distance = Math.min(newDistance, cellAtNextPos.distance)
        toVisit.add(cellAtNextPos)
    }
}

