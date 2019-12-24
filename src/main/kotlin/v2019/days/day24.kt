package v2019.days.day24

import v2019.util.pow
import v2019.util.sumByLong

fun day24a(input: List<String>): Long {
    fun computeBiodiversity(x: Int, y: Int): Long = 2L.pow(y * 5L + x)

    var grid = input.mapIndexed { y, line ->
        line.mapIndexed { x, cell -> if (cell == '#') computeBiodiversity(x, y) else 0L }.toLongArray()
    }

    fun countAdjacentBugs(x: Int, y: Int): Int {
        fun hasBug(x: Int, y: Int): Boolean = (grid.getOrNull(y)?.getOrNull(x) ?: 0) > 0
        return (if (hasBug(x, y + 1)) 1 else 0) +
            (if (hasBug(x, y - 1)) 1 else 0) +
            (if (hasBug(x + 1, y)) 1 else 0) +
            (if (hasBug(x - 1, y)) 1 else 0)
    }

    val prevBiodiversities = mutableSetOf<Long>()

    while (true) {
        grid = grid.mapIndexed { y, row ->
            row.mapIndexed { x, biodiversity ->
                val nrOfAdjacentBugs = countAdjacentBugs(x, y)
                if (nrOfAdjacentBugs == 1 || (biodiversity == 0L && nrOfAdjacentBugs == 2)) computeBiodiversity(x, y)
                else 0L
            }.toLongArray()
        }
        val biodiversity = grid.sumByLong(LongArray::sum)
        if (prevBiodiversities.contains(biodiversity)) {
            return biodiversity
        }
        prevBiodiversities.add(biodiversity)
    }
}

private class Grid(var depth: Int, var cells: List<BooleanArray>) {
    fun hasBug(x: Int, y: Int): Boolean? = cells.getOrNull(y)?.getOrNull(x)

    fun countBugs(): Int = cells.sumBy { row -> row.count { it } }

    fun isOnNorthOuterEdge(x: Int, y: Int): Boolean = y == 0
    fun isOnEastOuterEdge(x: Int, y: Int): Boolean = x == 4
    fun isOnSouthOuterEdge(x: Int, y: Int): Boolean = y == 4
    fun isOnWestOuterEdge(x: Int, y: Int): Boolean = x == 0
    fun isOnNorthInnerEdge(x: Int, y: Int): Boolean = y == 1 && x == 2
    fun isOnEastInnerEdge(x: Int, y: Int): Boolean = x == 3 && y == 2
    fun isOnSouthInnerEdge(x: Int, y: Int): Boolean = y == 3 && x == 2
    fun isOnWestInnerEdge(x: Int, y: Int): Boolean = x == 1 && y == 2

    fun getCountForNorthOuterRow(): Int = cells[0].count { it }
    fun getCountForEastOuterRow(): Int = cells.count { it[4] }
    fun getCountForSouthOuterRow(): Int = cells[4].count { it }
    fun getCountForWestOuterRow(): Int = cells.count { it[0] }

    fun countAdjacentBugs(x: Int, y: Int, grids: Map<Int, Grid>): Int {
        val parent = grids[depth + 1]
        val child = grids[depth - 1]

        var sum = 0

        if (isOnNorthOuterEdge(x, y)) {
            sum += if (parent?.hasBug(2, 1) == true) 1 else 0
        } else if (isOnSouthInnerEdge(x, y)) {
            sum += child?.getCountForSouthOuterRow() ?: 0
        } else {
            sum += if (hasBug(x, y - 1) == true) 1 else 0
        }

        if (isOnEastOuterEdge(x, y)) {
            sum += if (parent?.hasBug(3, 2) == true) 1 else 0
        } else if (isOnWestInnerEdge(x, y)) {
            sum += child?.getCountForWestOuterRow() ?: 0
        } else {
            sum += if (hasBug(x + 1, y) == true) 1 else 0
        }

        if (isOnSouthOuterEdge(x, y)) {
            sum += if (parent?.hasBug(2, 3) == true) 1 else 0
        } else if (isOnNorthInnerEdge(x, y)) {
            sum += child?.getCountForNorthOuterRow() ?: 0
        } else {
            sum += if (hasBug(x, y + 1) == true) 1 else 0
        }

        if (isOnWestOuterEdge(x, y)) {
            sum += if (parent?.hasBug(1, 2) == true) 1 else 0
        } else if (isOnEastInnerEdge(x, y)) {
            sum += child?.getCountForEastOuterRow() ?: 0
        } else {
            sum += if (hasBug(x - 1, y) == true) 1 else 0
        }

        return sum
    }
}

private fun emptyGrid(depth: Int): Grid = Grid(depth, List(5) { BooleanArray(5) })

fun day24b(input: List<String>, minutes: Int = 200): Int {
    var grids = mapOf(0 to Grid(0, input.map { line -> line.map { cell -> cell == '#' }.toBooleanArray() }))

    for (minute in 1..minutes) {
        val nextGrids = grids.values.plus(
            grids.values.mapNotNull { grid ->
                if (grids[grid.depth + 1] == null &&
                    (grid.getCountForNorthOuterRow() in 1..2 ||
                        grid.getCountForEastOuterRow() in 1..2 ||
                        grid.getCountForSouthOuterRow() in 1..2 ||
                        grid.getCountForWestOuterRow() in 1..2)
                ) {
                    emptyGrid(grid.depth + 1)
                } else null
            }
        ).plus(
            grids.values.mapNotNull { grid ->
                if (grids[grid.depth - 1] == null &&
                    (grid.hasBug(1,2) == true ||
                        grid.hasBug(2,1) == true ||
                        grid.hasBug(3,2) == true ||
                        grid.hasBug(2,3) == true)
                ) {
                    emptyGrid(grid.depth - 1)
                } else null
            }
        )

        grids = nextGrids.map { grid ->
            val nextGrid = Grid(grid.depth,
                grid.cells.mapIndexed { y, row ->
                    row.mapIndexed { x, bug ->
                        if (x == 2 && y == 2) {
                            false
                        } else {
                            val nrOfAdjacentBugs = grid.countAdjacentBugs(x, y, grids)
                            nrOfAdjacentBugs == 1 || (!bug && nrOfAdjacentBugs == 2)
                        }
                    }.toBooleanArray()
                }
            )
            grid.depth to nextGrid
        }.toMap()
    }

    return grids.values.sumBy { it.countBugs() }
}

