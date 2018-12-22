package days.day22

import Pos
import java.util.PriorityQueue

private enum class CellType {
    Rocky,
    Wet,
    Narrow
}

private enum class EquippedTool {
    Torch,
    ClimbingGear,
    Neither
}

private class Cell(val x: Int, val y: Int, val erosionLevel: Long, val type: CellType, var equippedTool: EquippedTool) {
    var costToReach = 10000000000L
}

private class Grid(val depth: Int, val targetPos: Pos) {
    private val grid: MutableMap<Int, MutableMap<Int, MutableMap<EquippedTool, Cell>>> = mutableMapOf()

    init {
        val startCell = createCell(0, 0, EquippedTool.Torch, 0L)
        startCell.costToReach = 0L
        grid[0] = mutableMapOf(Pair(0, mutableMapOf(Pair(EquippedTool.Torch, startCell))))
    }

    private fun createCell(x: Int, y: Int, equippedTool: EquippedTool, maybeGeologicIndex: Long? = null): Cell {
        val geologicIndex = maybeGeologicIndex ?: when {
            y == targetPos.y && x == targetPos.x -> 0L
            y == 0 -> x * 16807L
            x == 0 -> y * 48271L
            else -> {
                val cellLeft = getCell(x - 1, y, equippedTool)
                val cellAbove = getCell(x, y - 1, equippedTool)
                cellLeft.erosionLevel * cellAbove.erosionLevel
            }
        }

        val erosionLevel = (geologicIndex + depth) % 20183
        val cellType = when (erosionLevel % 3) {
            0L -> CellType.Rocky
            1L -> CellType.Wet
            else -> CellType.Narrow
        }
        return Cell(x, y, erosionLevel, cellType, equippedTool)
    }

    fun getCell(x: Int, y: Int, equippedTool: EquippedTool): Cell {
        val row = grid.getOrPut(y, ::mutableMapOf)
        if (!row.containsKey(x)) {
            row[x] = mutableMapOf()
        }
        if (!row[x]!!.containsKey(equippedTool)) {
            row[x]!![equippedTool] = createCell(x, y, equippedTool)
        }
        return row[x]!![equippedTool]!!
    }

    fun computeRiskFactor(): Int {
        return grid.values.sumBy { row ->
            row.values.sumBy { perTool ->
                perTool.values.sumBy { cell ->
                    when (cell.type) {
                        CellType.Rocky -> 0
                        CellType.Wet -> 1
                        CellType.Narrow -> 2
                    }
                }
            }
        }
    }
}

private fun parse(inputLines: List<String>): Triple<Int, Int, Int> {
    val depth = inputLines[0].substring(7).toInt()
    val (targetX, targetY) = inputLines[1].substring(8).split(',').map(String::toInt)
    return Triple(depth, targetX, targetY)
}

fun day22a(inputLines: List<String>): Int {
    val (depth, targetX, targetY) = parse(inputLines)

    val grid = Grid(depth, Pos(targetX, targetY))
    for (y in 0..targetY) {
        for (x in 0..targetX) {
            grid.getCell(x, y, EquippedTool.Neither)
        }
    }

    return grid.computeRiskFactor()
}

private fun getReachableNeighbours(cell: Cell, grid: Grid): List<Cell> {
    val reachableNeighbours: MutableSet<Cell> = mutableSetOf()

    fun addNeighbour(nextCell: Cell, addedCost: Int) {
        nextCell.costToReach = Math.min(nextCell.costToReach, cell.costToReach + addedCost)
        reachableNeighbours.add(nextCell)
    }

    fun findReachable(x: Int, y: Int) {
        when (cell.type) {
            CellType.Rocky -> {
                val neighbourWithTorch = grid.getCell(x, y, EquippedTool.Torch)
                val neighbourWithClimbingGear = grid.getCell(x, y, EquippedTool.ClimbingGear)
                when (neighbourWithTorch.type) {
                    CellType.Rocky -> {
                        if (cell.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbourWithTorch, 1)
                            addNeighbour(neighbourWithClimbingGear, 8)
                        }
                        if (cell.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbourWithTorch, 8)
                            addNeighbour(neighbourWithClimbingGear, 1)
                        }
                    }
                    CellType.Wet -> {
                        if (cell.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbourWithClimbingGear, 8)
                        } else if (cell.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbourWithClimbingGear, 1)
                        }
                    }
                    CellType.Narrow -> {
                        if (cell.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbourWithTorch, 1)
                        } else if (cell.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbourWithTorch, 8)
                        }
                    }
                }
            }
            CellType.Wet -> {
                val neighbourWithClimbingGear = grid.getCell(x, y, EquippedTool.ClimbingGear)
                val neighbourWithNeither = grid.getCell(x, y, EquippedTool.Neither)
                when (neighbourWithClimbingGear.type) {
                    CellType.Rocky -> {
                        if (cell.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbourWithClimbingGear, 1)
                        } else if (cell.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbourWithClimbingGear, 8)
                        }
                    }
                    CellType.Wet -> {
                        if (cell.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbourWithClimbingGear, 1)
                            addNeighbour(neighbourWithNeither, 8)
                        }
                        if (cell.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbourWithClimbingGear, 8)
                            addNeighbour(neighbourWithNeither, 1)
                        }
                    }
                    CellType.Narrow -> {
                        if (cell.equippedTool == EquippedTool.ClimbingGear) {
                            addNeighbour(neighbourWithNeither, 8)
                        } else if (cell.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbourWithNeither, 1)
                        }
                    }
                }
            }
            CellType.Narrow -> {
                val neighbourWithTorch = grid.getCell(x, y, EquippedTool.Torch)
                val neighbourWithNeither = grid.getCell(x, y, EquippedTool.Neither)
                when (neighbourWithTorch.type) {
                    CellType.Rocky -> {
                        if (cell.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbourWithTorch, 1)
                        } else if (cell.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbourWithTorch, 8)
                        }
                    }
                    CellType.Wet -> {
                        if (cell.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbourWithNeither, 8)
                        } else if (cell.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbourWithNeither, 1)
                        }
                    }
                    CellType.Narrow -> {
                        if (cell.equippedTool == EquippedTool.Torch) {
                            addNeighbour(neighbourWithTorch, 1)
                            addNeighbour(neighbourWithNeither, 8)
                        } else if (cell.equippedTool == EquippedTool.Neither) {
                            addNeighbour(neighbourWithTorch, 8)
                            addNeighbour(neighbourWithNeither, 1)
                        }
                    }
                }
            }
        }
    }

    if (cell.y > 0) {
        findReachable(cell.x, cell.y - 1)
    }
    if (cell.x > 0) {
        findReachable(cell.x - 1, cell.y)
    }
    findReachable(cell.x, cell.y + 1)
    findReachable(cell.x + 1, cell.y)

    return reachableNeighbours.toList()
}

fun day22b(inputLines: List<String>): Long? {
    val (depth, targetX, targetY) = parse(inputLines)

    val grid = Grid(depth, Pos(targetX, targetY))

    val toVisit: PriorityQueue<Cell> = PriorityQueue { p0, p1 -> (p0.costToReach - p1.costToReach).toInt() }
    val topLeft = grid.getCell(0, 0, EquippedTool.Torch)
    toVisit.offer(topLeft)

    val seenCells: MutableSet<String> = mutableSetOf("0_0_${topLeft.equippedTool}")

    while (toVisit.size > 0) {
        val currentCell = toVisit.poll()
        if (currentCell.x == targetX && currentCell.y == targetY) {
            return currentCell.costToReach
        }

        getReachableNeighbours(currentCell, grid).forEach {
            val hash = "${it.x}_${it.y}_${it.equippedTool}"
            if (!seenCells.contains(hash)) {
                toVisit.add(it)
                seenCells.add(hash)
            }
        }
    }

    return null
}