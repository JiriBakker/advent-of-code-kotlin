package v2018.days.day20

private typealias Grid = MutableMap<Int, MutableMap<Int, Int>>
private typealias Index = Int

private fun followDirections(grid: Grid, directions: String, startIndex: Int, startX: Int, startY: Int, startPathLength: Int): Index {
    var x = startX
    var y = startY
    var pathLength = startPathLength

    fun markCurrentCell() {
        val gridRow = grid.getOrPut(y) { mutableMapOf() }
        val pathLengthToCell = gridRow.getOrDefault(x, Int.MAX_VALUE)
        if (pathLength < pathLengthToCell) {
            gridRow[x] = pathLength
        }
    }

    var index = startIndex
    while (true) {
        when (directions[index]) {
            'N' -> { pathLength++; y-- }
            'E' -> { pathLength++; x++ }
            'W' -> { pathLength++; x-- }
            'S' -> { pathLength++; y++ }

            '|' -> return index
            '$' -> return index
            ')' -> return index
            else -> // '('
                do {
                    index = followDirections(grid, directions, index + 1, x, y, pathLength)
                } while (directions[index] != ')')
        }
        markCurrentCell()
        index++
    }
}

fun day20a(directions: String): Int {
    val grid = mutableMapOf<Int, MutableMap<Int, Int>>()
    followDirections(grid, directions, 1, 0, 0, 0)
    return grid.map { row -> row.value.values.max()!! }.max()!!
}

fun day20b(directions: String): Int {
    val grid = mutableMapOf<Int, MutableMap<Int, Int>>()
    followDirections(grid, directions, 1, 0, 0, 0)
    return grid.values.sumBy { row -> row.values.count { it >= 1000 } }
}