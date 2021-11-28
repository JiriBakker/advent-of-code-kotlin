package v2019.days.day24

import util.pow
import util.sumOfLong

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
        val biodiversity = grid.sumOfLong(LongArray::sum)
        if (prevBiodiversities.contains(biodiversity)) {
            return biodiversity
        }
        prevBiodiversities.add(biodiversity)
    }
}

private data class Pos(val x: Int, val y: Int) {
    fun north(): Pos = Pos(x, y - 1)
    fun east(): Pos = Pos(x + 1, y)
    fun south(): Pos = Pos(x, y + 1)
    fun west(): Pos = Pos(x - 1, y)
}

private class Grid(var depth: Int, var cells: List<BooleanArray>) {

    fun hasBug(pos: Pos): Boolean? = cells.getOrNull(pos.y)?.getOrNull(pos.x)

    fun countBugs(): Int = cells.sumOf { row -> row.count { it } }

    fun isOnNorthOuterEdge(pos: Pos): Boolean = pos.y == 0
    fun isOnEastOuterEdge(pos: Pos): Boolean = pos.x == 4
    fun isOnSouthOuterEdge(pos: Pos): Boolean = pos.y == 4
    fun isOnWestOuterEdge(pos: Pos): Boolean = pos.x == 0

    fun countBugsOnNorthOuterRow(): Int = cells[0].count { it }
    fun countBugsOnEastOuterRow(): Int = cells.count { it[4] }
    fun countBugsOnSouthOuterRow(): Int = cells[4].count { it }
    fun countBugsOnWestOuterRow(): Int = cells.count { it[0] }

    fun countAdjacentBugs(pos: Pos, grids: Map<Int, Grid>): Int {
        val parent = grids[depth + 1]
        val child = grids[depth - 1]

        var sum = 0

        sum += if (isOnNorthOuterEdge(pos)) {
            if (parent?.hasBug(northOfChild) == true) 1 else 0
        } else if (pos == southOfChild) {
            child?.countBugsOnSouthOuterRow() ?: 0
        } else {
            if (hasBug(pos.north()) == true) 1 else 0
        }

        sum += if (isOnEastOuterEdge(pos)) {
            if (parent?.hasBug(eastOfChild) == true) 1 else 0
        } else if (pos == westOfChild) {
            child?.countBugsOnWestOuterRow() ?: 0
        } else {
            if (hasBug(pos.east()) == true) 1 else 0
        }

        sum += if (isOnSouthOuterEdge(pos)) {
            if (parent?.hasBug(southOfChild) == true) 1 else 0
        } else if (pos == northOfChild) {
            child?.countBugsOnNorthOuterRow() ?: 0
        } else {
            if (hasBug(pos.south()) == true) 1 else 0
        }

        sum += if (isOnWestOuterEdge(pos)) {
            if (parent?.hasBug(westOfChild) == true) 1 else 0
        } else if (pos == eastOfChild) {
            child?.countBugsOnEastOuterRow() ?: 0
        } else {
            if (hasBug(pos.west()) == true) 1 else 0
        }

        return sum
    }

    companion object {
        val childCell = Pos(2, 2)
        val northOfChild = Pos(2, 1)
        val eastOfChild = Pos(3, 2)
        val southOfChild = Pos(2, 3)
        val westOfChild = Pos(1, 2)
    }
}

private fun emptyGrid(depth: Int): Grid = Grid(depth, List(5) { BooleanArray(5) })

fun day24b(input: List<String>, minutes: Int = 200): Int {
    var grids = mapOf(0 to Grid(0, input.map { line -> line.map { cell -> cell == '#' }.toBooleanArray() }))

    for (minute in 1..minutes) {
        val nextGrids =
            grids.values
                .plus( // Add parent grid if needed
                    grids.maxByOrNull { it.key }!!
                        .let { (_, grid) ->
                            if (grid.countBugsOnNorthOuterRow() in 1..2 ||
                                grid.countBugsOnEastOuterRow() in 1..2 ||
                                grid.countBugsOnSouthOuterRow() in 1..2 ||
                                grid.countBugsOnWestOuterRow() in 1..2
                            ) {
                                listOf(emptyGrid(grid.depth + 1))
                            } else listOf()
                        }
                )
                .plus( // Add child grid if needed
                    grids.minByOrNull { it.key }!!
                        .let { (_, grid) ->
                            if (grid.hasBug(Grid.northOfChild) == true ||
                                grid.hasBug(Grid.eastOfChild) == true ||
                                grid.hasBug(Grid.southOfChild) == true ||
                                grid.hasBug(Grid.westOfChild) == true
                        ) {
                            listOf(emptyGrid(grid.depth - 1))
                        } else listOf()
                    }
                )

        grids = nextGrids.map { grid ->
            grid.depth to Grid(grid.depth,
                grid.cells.mapIndexed { y, row ->
                    row.mapIndexed { x, bug ->
                        val pos = Pos(x, y)
                        if (pos == Grid.childCell) {
                            false
                        } else {
                            val nrOfAdjacentBugs = grid.countAdjacentBugs(pos, grids)
                            nrOfAdjacentBugs == 1 || (!bug && nrOfAdjacentBugs == 2)
                        }
                    }.toBooleanArray()
                }
            )
        }.toMap()
    }

    return grids.values.sumOf { it.countBugs() }
}

