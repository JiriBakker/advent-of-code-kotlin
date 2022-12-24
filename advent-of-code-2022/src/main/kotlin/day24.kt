import Day24.findShortestPathTime
import Day24.parseGrid
import util.manhattanDistance
import java.util.PriorityQueue

fun day24a(input: List<String>): Int {
    val grid = input.parseGrid()

    val targetX = grid[0]!!.size - 1
    val targetY = grid.size

    return grid.findShortestPathTime(0, -1, 0, targetX, targetY)
}

fun day24b(input: List<String>): Int {
    val grid = input.parseGrid()

    val targetX = grid[0]!!.size - 1
    val targetY = grid.size

    val firstPathTime = grid.findShortestPathTime(0, -1, 0, targetX, targetY)
    val secondPathTime = grid.findShortestPathTime(targetX, targetY, firstPathTime, 0, -1)
    return grid.findShortestPathTime(0, -1, secondPathTime, targetX, targetY)
}

object Day24 {

    data class State(val x: Int, val y: Int, val time: Int) {
        fun hash() = "$x,$y,$time"
    }

    fun Map<Int, Map<Int, Cell>>.findShortestPathTime(startX: Int, startY: Int, startTime: Int, targetX: Int, targetY: Int): Int {
        val width = this[0]!!.size
        val height = this.size

        fun Cell.hasBlizzardAt(time: Int) =
            this.xOffsets.any { offset -> (time - offset) % width == 0 } ||
                this.yOffsets.any { offset -> (time - offset) % height == 0 }

        val toCheck = ArrayDeque<State>()
        toCheck.add(State(startX, startY, startTime))

        fun addToCheck(x: Int, y: Int, time: Int) {
            val inXBounds = x in 0 until width
            val inYBounds = y in 0 until height
            val isStart = x == startX && y == startY
            val isTarget = x == targetX && y == targetY

            if (inXBounds &&
                    (isStart
                        || isTarget
                        || (inYBounds && !this[y]!![x]!!.hasBlizzardAt(time))
                    )
            ) {
                toCheck.add(State(x, y, time))
            }
        }

        val checked = mutableSetOf<String>()

        while (toCheck.isNotEmpty()) {
            val cur = toCheck.removeFirst()

            if (!checked.add(cur.hash())) {
                continue
            }

            if (cur.x == targetX && cur.y == targetY) {
                return cur.time
            }

            addToCheck(cur.x - 1, cur.y, cur.time + 1)
            addToCheck(cur.x + 1, cur.y, cur.time + 1)
            addToCheck(cur.x, cur.y - 1, cur.time + 1)
            addToCheck(cur.x, cur.y + 1, cur.time + 1)
            addToCheck(cur.x, cur.y, cur.time + 1)
        }

        throw Exception("No path found")
    }

    fun List<String>.parseGrid(): Map<Int, Map<Int, Cell>> {
        val height = this.size - 2
        val width = this[0].length - 2

        return (0 until height).associateWith { y ->
                (0 until width).associateWith { x ->
                    val xOffsets = mutableSetOf<Int>()
                    val yOffsets = mutableSetOf<Int>()

                    (0 until height).forEach { scanY ->
                        val cellInColumn = this[scanY + 1][x + 1]
                        if (cellInColumn == 'v') {
                            if (scanY < y) {
                                yOffsets.add(y - scanY)
                            } else if (scanY == y) {
                                yOffsets.add(0)
                            } else {
                                yOffsets.add(height - (scanY - y))
                            }
                        } else if (cellInColumn == '^') {
                            if (scanY < y) {
                                yOffsets.add(height - (y - scanY))
                            } else if (scanY == y) {
                                yOffsets.add(0)
                            } else {
                                yOffsets.add(scanY - y)
                            }
                        }
                    }

                    (0 until width).forEach { scanX ->
                        val cellInRow = this[y + 1][scanX + 1]
                        if (cellInRow == '>') {
                            if (scanX < x) {
                                xOffsets.add(x - scanX)
                            } else if (scanX == x) {
                                xOffsets.add(0)
                            } else {
                                xOffsets.add(width - (scanX - x))
                            }
                        } else if (cellInRow == '<') {
                            if (scanX < x) {
                                xOffsets.add(width - (x - scanX))
                            } else if (scanX == x) {
                                xOffsets.add(0)
                            } else {
                                xOffsets.add(scanX - x)
                            }
                        }
                    }

                    Cell(x, y, xOffsets, yOffsets)
                }
            }

    }

    class Cell(val x: Int, val y: Int, val xOffsets: Set<Int>, val yOffsets: Set<Int>)
}