package days.day17

import getBounds

private class Pos(val x: Int, val y: Int)

private enum class CellType {
    SAND,
    CLAY,
    STATIC_WATER,
    MOVING_WATER
}

private class Cell(var type: CellType) {
    var isInBucket = type == CellType.CLAY
}

private class Grid(private val grid: Array<Array<Cell>>, val springPos: Pos) {
    private val height = grid.size
    private val width = grid[0].size

    var waterCellCount = 0
        private set

    var stillWaterCount = 0
        private set

    companion object {
        fun parse(inputLines: List<String>): Grid {
            val clayPositions = sequence {
                inputLines.forEach {
                    val (firstPart, secondPart) = it.split(", ")
                    val isHorizontalRange = firstPart[0] == 'y'

                    val axisCoordinate = firstPart.split('=')[1].toInt()
                    val (rangeStart, rangeEnd) = secondPart.substring(2).split("..").map(String::toInt)
                    for (coordinate in rangeStart..rangeEnd) {
                        if (isHorizontalRange) {
                            yield(Pos(coordinate, axisCoordinate))
                        } else {
                            yield(Pos(axisCoordinate, coordinate))
                        }
                    }
                }
            }.toList()

            val (minX, maxX, minY, maxY) = clayPositions.getBounds({ it.x }, { it.y })
            val normalizedClayPositions = clayPositions.map { Pos(it.x - minX + 1, it.y - minY) }

            val height = maxY - minY + 1
            val width = maxX - minX + 1 + 2 // '+2' to ensure margins on both sides of the grid for water to spill over

            val grid = Array(height) { Array(width) { Cell(CellType.SAND) } }
            normalizedClayPositions.forEach { grid[it.y][it.x] = Cell(CellType.CLAY) }

            markInBucketCells(grid)

            return Grid(grid, Pos(500 - minX + 1, 0))
        }

        private fun markInBucketCells(grid: Array<Array<Cell>>) {
            val height = grid.size
            val width = grid[0].size

            fun addToBucket(y: Int, minX: Int, maxX: Int) {
                for (x in minX..maxX) {
                    grid[y][x].isInBucket = true
                }
            }

            for (y in height - 1 downTo 0) {
                var leftClayX: Int? = null
                for (x in 0 until width) {
                    if (grid[y][x].type == CellType.CLAY) {
                        if (leftClayX != null) {
                            addToBucket(y, leftClayX + 1, x - 1)
                        }
                        leftClayX = x
                    } else {
                        val isBelowInBucket = y < height - 1 && grid[y + 1][x].isInBucket
                        if (!isBelowInBucket) {
                            leftClayX = null
                        }
                    }
                }
            }
        }
    }

    private inline fun isBlocked(x: Int, y: Int): Boolean {
        return x < 0 || x >= width ||
            y < 0 || y >= height ||
            grid[y][x].type == CellType.CLAY ||
            grid[y][x].type == CellType.STATIC_WATER
    }

    private fun visit(x: Int, y: Int, ifBelowBlockedAction: () -> Unit): Boolean {
        if (isBlocked(x, y)) {
            return false
        }
        if (grid[y][x].type == CellType.MOVING_WATER) {
            return true
        }

        waterCellCount++

        if (y < height - 1 && !visitBelow(x, y + 1)) {
            ifBelowBlockedAction()
        }

        if (grid[y][x].isInBucket) {
            grid[y][x].type = CellType.STATIC_WATER
            stillWaterCount++
            return false
        }

        grid[y][x].type = CellType.MOVING_WATER
        return true
    }

    private fun visitLeft(x: Int, y: Int): Boolean {
        return visit(x, y) { visitLeft(x - 1, y) }
    }

    private fun visitRight(x: Int, y: Int): Boolean {
        return visit(x, y) { visitRight(x + 1, y) }
    }

    private fun visitBelow(x: Int, y: Int): Boolean {
        return visit(x, y) {
            visitLeft(x - 1, y)
            visitRight(x + 1, y)
        }
    }

    fun runWater() {
        visitBelow(springPos.x, springPos.y)
    }

    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(when (grid[y][x].type) {
                    CellType.CLAY -> '#'
                    CellType.SAND -> '.'
                    CellType.MOVING_WATER -> '|'
                    CellType.STATIC_WATER -> '~'
                })
            }
            println()
        }
    }
}

fun day17a(inputLines: List<String>): Int {
    val grid = Grid.parse(inputLines)
    grid.runWater()
    return grid.waterCellCount
}

fun day17b(inputLines: List<String>): Int {
    val grid = Grid.parse(inputLines)
    grid.runWater()
    return grid.stillWaterCount
}
