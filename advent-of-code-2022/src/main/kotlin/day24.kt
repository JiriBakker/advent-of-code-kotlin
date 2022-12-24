import Day24.parseGrid
import Day24.State
import util.manhattanDistance
import java.util.PriorityQueue

fun day24a(input: List<String>): Int {
    val grid = input.parseGrid()

    val width = grid[0]!!.size
    val height = grid.size

    val targetX = width - 1
    val targetY = height

    fun Day24.Cell.hasBlizzardAt(time: Int) =
        this.xOffsets.any { offset -> (time - offset) % width == 0 } ||
            this.yOffsets.any { offset -> (time - offset) % height == 0 }

    val toCheck = PriorityQueue<State> { a, b ->
        when (a.time.compareTo(b.time)) {
            1 -> 1
            -1 -> -1
            else -> manhattanDistance(a.x, a.y, targetX, targetY).compareTo(manhattanDistance(b.x, b.y, targetX, targetY))
        }
    }
    toCheck.add(State(0, -1, 0, null))

    fun addToCheck(x: Int, y: Int, time: Int, prev: State) {
        if (x in 0 until width
            && (
                (y in 0 until height && !grid[y]!![x]!!.hasBlizzardAt(time))
                || (x == targetX && y == targetY)
                || (x == 0 && y == -1)
            )
        ) {
            toCheck.add(State(x, y, time, prev))
        }
    }

    val checked = mutableSetOf<String>()

    while (toCheck.isNotEmpty()) {
        val cur = toCheck.poll()

        if (!checked.add(cur.hash())) {
            continue
        }

        if (cur.x == targetX && cur.y == targetY) {
            return cur.time
        }

        addToCheck(cur.x - 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x + 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x, cur.y - 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y + 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y, cur.time + 1, cur)
    }

    throw Exception("No path found")
}

fun day24b(input: List<String>): Int {
    val grid = input.parseGrid()

    val width = grid[0]!!.size
    val height = grid.size

    var targetX = width - 1
    var targetY = height

    fun Day24.Cell.hasBlizzardAt(time: Int) =
        this.xOffsets.any { offset -> (time - offset) % width == 0 } ||
            this.yOffsets.any { offset -> (time - offset) % height == 0 }

    val toCheck = PriorityQueue<State> { a, b ->
        when (a.time.compareTo(b.time)) {
            1 -> 1
            -1 -> -1
            else -> manhattanDistance(a.x, a.y, targetX, targetY).compareTo(manhattanDistance(b.x, b.y, targetX, targetY))
        }
    }
    toCheck.add(State(0, -1, 0, null))

    fun addToCheck(x: Int, y: Int, time: Int, prev: State) {
        if (x in 0 until width
            && (
                (y in 0 until height && !grid[y]!![x]!!.hasBlizzardAt(time))
                || (x == targetX && y == targetY)
                || (x == 0 && y == -1)
            )
        ) {
            toCheck.add(State(x, y, time, prev))
        }
    }


    val checked = mutableSetOf<String>()

    while (toCheck.isNotEmpty()) {
        val cur = toCheck.poll()

        if (!checked.add(cur.hash())) {
            continue
        }

        if (cur.x == targetX && cur.y == targetY) {
            toCheck.clear()
            toCheck.add(cur)
            checked.clear()
            break
        }

        addToCheck(cur.x - 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x + 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x, cur.y - 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y + 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y, cur.time + 1, cur)
    }

    while (toCheck.isNotEmpty()) {
        val cur = toCheck.poll()

        if (!checked.add(cur.hash())) {
            continue
        }

        if (cur.x == 0 && cur.y == -1) {
            toCheck.clear()
            toCheck.add(cur)
            checked.clear()
            break
        }

        addToCheck(cur.x - 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x + 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x, cur.y - 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y + 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y, cur.time + 1, cur)
    }

    targetX = width - 1
    targetY = height

    while (toCheck.isNotEmpty()) {
        val cur = toCheck.poll()

        if (!checked.add(cur.hash())) {
            continue
        }

        if (cur.x == targetX && cur.y == targetY) {
            return cur.time
        }

        addToCheck(cur.x - 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x + 1, cur.y, cur.time + 1, cur)
        addToCheck(cur.x, cur.y - 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y + 1, cur.time + 1, cur)
        addToCheck(cur.x, cur.y, cur.time + 1, cur)
    }

    throw Exception("No path found")
}

object Day24 {

    data class State(val x: Int, val y: Int, val time: Int, val prev: State?) {
        fun hash() = "$x,$y,$time"
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

    class Cell(val x: Int, val y: Int, val xOffsets: Set<Int>, val yOffsets: Set<Int>) {

    }
}