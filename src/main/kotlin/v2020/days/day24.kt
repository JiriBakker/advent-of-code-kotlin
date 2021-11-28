package v2020.days.day24

private data class Tile(val e: Int, val ne: Int)

private fun parseGrid(input: List<String>): Map<Int, Map<Int, Tile>> {
    val tiles = input.map { line ->
        var e = 0
        var ne = 0
        var i = 0
        while (i in line.indices) {
            when (line[i]) {
                'e' -> e++
                'w' -> e--
                's' -> {
                    i++
                    if (line[i] == 'e') e++
                    ne--
                }
                'n' -> {
                    i++
                    if (line[i] == 'w') e--
                    ne++
                }
            }
            i++
        }
        Tile(e, ne)
    }

    val grid = mutableMapOf<Int, MutableMap<Int, Tile>>()

    tiles.forEach { tile ->
        val (e, ne) = tile
        val row = grid.getOrPut(e) { mutableMapOf() }
        if (row.contains(ne)) {
            row.remove(ne)
        } else {
            row[ne] = tile
        }
    }

    return grid
}


fun day24a(input: List<String>): Int {
    val grid = parseGrid(input)

    return grid.values.sumOf { row -> row.size }
}

fun day24b(input: List<String>): Int {
    var grid = parseGrid(input)

    repeat(100) {
        val toCheck = mutableSetOf<Tile>()
        toCheck.addAll(grid.flatMap { it.value.values.toList() })
        toCheck.addAll(
            toCheck.flatMap { tile ->
                listOf(
                    Tile(tile.e - 1, tile.ne),
                    Tile(tile.e + 1, tile.ne),
                    Tile(tile.e, tile.ne + 1),
                    Tile(tile.e, tile.ne - 1),
                    Tile(tile.e - 1, tile.ne + 1),
                    Tile(tile.e + 1, tile.ne - 1),
                )
            }
        )

        val nextGrid = mutableMapOf<Int, MutableMap<Int, Tile>>()
        toCheck.map { tile ->
            val isBlack = grid[tile.e]?.get(tile.ne) != null
            val nrOfBlackNeighbours = listOfNotNull(
                grid[tile.e - 1]?.get(tile.ne),
                grid[tile.e + 1]?.get(tile.ne),
                grid[tile.e]?.get(tile.ne + 1),
                grid[tile.e]?.get(tile.ne - 1),
                grid[tile.e - 1]?.get(tile.ne + 1),
                grid[tile.e + 1]?.get(tile.ne - 1),
            ).size

            if ((isBlack && nrOfBlackNeighbours in 1..2) || (!isBlack && nrOfBlackNeighbours == 2)) {
                nextGrid.getOrPut(tile.e) { mutableMapOf() }[tile.ne] = tile
            }
        }

        grid = nextGrid
    }

    return grid.values.sumOf { row -> row.size }
}
