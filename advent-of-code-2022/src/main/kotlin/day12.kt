import Day12.Grid.Companion.parseGrid
import Day12.findShortestPath
import java.util.LinkedList

fun day12a(input: List<String>): Int {
    val grid = input.parseGrid()
    return grid.findShortestPath(listOf(grid.start))
}

fun day12b(input: List<String>): Int {
    val grid = input.parseGrid()
    return grid.findShortestPath(grid.lowest)
}

object Day12 {

    data class GridSquare(val x: Int, val y: Int)

    class Grid private constructor(
        val grid: Map<Int, Map<Int, Char>>,
        val start: GridSquare,
        val destination: GridSquare,
        val lowest: List<GridSquare>
    ) {
        companion object {
            fun List<String>.parseGrid(): Grid {
                var start: GridSquare? = null
                var destination: GridSquare? = null
                val lowest = mutableListOf<GridSquare>()

                val grid = this.mapIndexed { y, line ->
                    y to line.mapIndexed { x, char ->
                        val height = when (char) {
                            'S' -> {
                                start = GridSquare(x, y)
                                lowest.add(GridSquare(x, y))
                                'a'
                            }
                            'a' -> {
                                lowest.add(GridSquare(x, y))
                                'a'
                            }
                            'E' -> {
                                destination = GridSquare(x, y)
                                'z'
                            }
                            else -> char
                        }
                        x to height
                    }.toMap()
                }.toMap()

                return Grid(grid, start!!, destination!!, lowest)
            }
        }
    }

    fun Grid.findShortestPath(possibleStartPositions: List<GridSquare>): Int {
        val toVisit = LinkedList<Pair<Int, GridSquare>>()
        val visited = mutableSetOf<GridSquare>()

        possibleStartPositions.forEach { startPos ->
            toVisit.add(0 to startPos)
        }

        while (toVisit.isNotEmpty()) {
            val (pathLength, gridSquare) = toVisit.poll()

            if (!visited.add(gridSquare)) {
                continue
            }

            if (gridSquare == destination) {
                return pathLength
            }

            val (x, y) = gridSquare
            val height = grid[y]!![x]!!

            fun addToVisit(targetX: Int, targetY: Int) {
                val targetHeight = grid[targetY]?.get(targetX) ?: 'z'
                if (targetHeight <= height + 1) {
                    toVisit.add((pathLength + 1) to GridSquare(targetX, targetY))
                }
            }

            addToVisit(x - 1, y)
            addToVisit(x + 1, y)
            addToVisit(x, y - 1)
            addToVisit(x, y + 1)
        }

        throw Exception("No valid path found")
    }
}