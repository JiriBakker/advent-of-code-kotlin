package v2020.days.day20

import util.splitByDoubleNewLine

private enum class Edge {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT,
    FLIPPED_TOP,
    FLIPPED_RIGHT,
    FLIPPED_BOTTOM,
    FLIPPED_LEFT,
}

private class Tile private constructor(val id: Int, val grid: List<List<Char>>) {

    var edges: Map<Edge, String>
        private set

    init {
        fun collect(startX: Int, startY: Int, dx: Int, dy: Int): String {
            var x = startX
            var y = startY
            val chars = mutableListOf<Char>()
            while (x in 0 until 10 && y in 0 until 10) {
                chars.add(grid[y][x])
                x += dx
                y += dy
            }
            return chars.joinToString("")
        }

        edges = mapOf(
            Edge.TOP to collect(0, 0, 1, 0),
            Edge.LEFT to collect(0, 9, 0, -1),
            Edge.RIGHT to collect(9, 0, 0, 1),
            Edge.BOTTOM to collect(9, 9, -1, 0),
            Edge.FLIPPED_TOP to collect(0, 0, 1, 0).reversed(),
            Edge.FLIPPED_LEFT to collect(0, 9, 0, -1).reversed(),
            Edge.FLIPPED_RIGHT to collect(9, 0, 0, 1).reversed(),
            Edge.FLIPPED_BOTTOM to collect(9, 9, -1, 0).reversed()
        )
    }

    fun canAlign(other: Tile): Boolean {
        return this.edges.entries.any { (edge, chars) -> other.edges.entries.any { (otherEdge, otherChars) -> chars == otherChars } }
    }

    fun findAlignment(other: Tile, edge: Edge): Edge? {
        return other.edges.entries.firstOrNull { (_, otherChars) -> this.edges[edge] == otherChars }?.key
    }

    override fun toString(): String =
        "Tile $id"

    fun align(alignment: Pair<Edge, Edge>): Tile {
        return when (alignment.first) {
            Edge.RIGHT -> {
                when (alignment.second) {
                    Edge.LEFT -> rotateRight(rotateRight(flipHorizontal(this)))
                    Edge.BOTTOM -> rotateRight(flipHorizontal(this))
                    Edge.RIGHT -> flipHorizontal(this)
                    Edge.TOP -> flipHorizontal(rotateRight(this))
                    Edge.FLIPPED_LEFT -> this
                    Edge.FLIPPED_TOP -> rotateRight(rotateRight(rotateRight(this)))
                    Edge.FLIPPED_RIGHT -> rotateRight(rotateRight(this))
                    Edge.FLIPPED_BOTTOM -> rotateRight(this)
                }
            }
            Edge.BOTTOM -> {
                when (alignment.second) {
                    Edge.TOP -> flipHorizontal(this)
                    Edge.LEFT -> flipHorizontal(rotateRight(this))
                    Edge.BOTTOM -> flipHorizontal(rotateRight(rotateRight(this)))
                    Edge.RIGHT -> rotateRight(flipHorizontal(this))
                    Edge.FLIPPED_BOTTOM -> rotateRight(rotateRight(this))
                    Edge.FLIPPED_LEFT -> rotateRight(this)
                    Edge.FLIPPED_TOP -> this
                    Edge.FLIPPED_RIGHT -> rotateRight(rotateRight(rotateRight(this)))
                }
            }
            else -> error("Can only align on RIGHT and BOTTOM, not ${alignment.first}")
        }
    }

    companion object {
        fun parse(lines: List<String>): Tile {
            val id = lines.first().replace("Tile ", "").replace(":", "").toInt()
            return Tile(id, lines.drop(1).map { it.toList() } )
        }

        fun rotateRight(tile: Tile): Tile {
            return Tile(tile.id, tile.grid.rotateRight())
        }

        fun flipHorizontal(tile: Tile): Tile {
            return Tile(tile.id, tile.grid.flipHorizontal())
        }
    }
}

private fun <T> List<List<T>>.rotateRight(): List<List<T>> {
    val grid = this.map { it.toMutableList() }
    val width = this.first().size
    val height = this.size

    for (y in 0 until height) {
        for (x in 0 until width) {
            grid[x][width - 1 - y] = this[y][x]
        }
    }


    return grid
}

private fun <T> List<List<T>>.flipHorizontal(): List<List<T>> {
    val width = this.first().size
    val height = this.size

    val dest = mutableListOf<MutableList<T>>()
    for (y in 0 until height) {
        dest.add(mutableListOf())
        for (x in 0 until width) {
            dest[y].add(this[y][width - 1 - x])
        }
    }
    return dest
}

private fun countNonMonsterHashes(image: List<List<Char>>): Int {
    val monster = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    )

    fun List<List<Char>>.countHashes(): Int =
        this.sumBy { row -> row.count { it == '#' } }

    fun monsterSearch(startGrid: List<List<Char>>): Int {
        val grid = startGrid.map { it.toMutableList() }
        for (y in 0 until grid.size - monster.size) {
            (0 until grid[y].size - monster[0].length).forEach match@{ x ->
                for (my in 0 until monster.size) {
                    for (mx in 0 until monster[my].length) {
                        if (monster[my][mx] == '#') {
                            if (grid[y + my][x + mx] != '#') {
                                return@match
                            }
                        }
                    }
                }

                for (my in 0 until monster.size) {
                    for (mx in 0 until monster[my].length) {
                        if (monster[my][mx] == '#') {
                            grid[y + my][x + mx] = '.'
                        }
                    }
                }
            }
        }
        return grid.countHashes()
    }

    val hashCount = image.countHashes()

    return sequenceOf(
        monsterSearch(image),
        monsterSearch(image.rotateRight()),
        monsterSearch(image.rotateRight().rotateRight()),
        monsterSearch(image.rotateRight().rotateRight().rotateRight()),
        monsterSearch(image.flipHorizontal()),
        monsterSearch(image.flipHorizontal().rotateRight()),
        monsterSearch(image.flipHorizontal().rotateRight().rotateRight()),
        monsterSearch(image.flipHorizontal().rotateRight().rotateRight().rotateRight()),
    )
        .dropWhile { it == hashCount}
        .first()
}

private fun tilesToImage(imageTiles: List<List<Tile>>): List<List<Char>> {
    val image = mutableListOf<MutableList<Char>>()
    for (y1 in imageTiles.indices) {
        for (y2 in 1 until 9) {
            image.add(mutableListOf())
            for (tile in imageTiles[y1]) {
                for (x2 in 1 until 9) {
                    image[y1 * 8 + (y2 - 1)].add(tile.grid[y2][x2])
                }
            }
        }
    }
    return image
}


fun day20a(input: List<String>): Long {
    val tiles = input.splitByDoubleNewLine().map(Tile::parse)

    val alignCounts = tiles.map { tile ->
        tile to tiles.filter { other -> other != tile && other.canAlign(tile) }
    }.toMap()

    val corners = alignCounts.filter { it.value.size == 2 }

    return corners.entries.fold(1L) { acc, entry -> acc * entry.key.id.toLong() }

}

fun day20b(input: List<String>, imageWidthInTiles: Int = 12): Int {
    val tiles = input.splitByDoubleNewLine().map {
        val tile = Tile.parse(it)
        tile.id to tile
    }.toMap().toMutableMap()

    val neighbours: Map<Int, Map<Edge, Int>> = tiles.values.map { tile ->
        tile.id to tiles.values.mapNotNull { other ->
            if (other != tile) {
                other to listOf(Edge.TOP, Edge.RIGHT, Edge.BOTTOM, Edge.LEFT)
                    .map { edge -> edge to tile.findAlignment(other, edge) }
                    .singleOrNull { it.second != null }
                    ?.let { it.first }
            } else {
                null
            }
        }
            .filter { it.second != null }
            .map { it.second!! to it.first.id }
            .toMap()
    }.toMap().toSortedMap()

    val topLeftCorner = neighbours.entries.single { it.value.size == 2 && it.value.keys.contains(Edge.BOTTOM) && it.value.keys.contains(Edge.RIGHT) }
    val topLeftTile = tiles[topLeftCorner.key]!!

    val imageTiles = mutableListOf(mutableListOf(topLeftTile))

    fun findMatchingTile(prevTile: Tile, edge: Edge): Tile {
        val (nextTile, alignment) =
            neighbours[prevTile.id]!!
                .map {
                    val otherTile = tiles[it.value]!!
                    otherTile to prevTile.findAlignment(otherTile, edge)
                }
                .single { it.second != null }

        return nextTile.align(edge to alignment!!)
    }

    fun saveTile(tile: Tile, y: Int) {
        imageTiles[y].add(tile)
        tiles[tile.id] = tile
    }

    fun fillRow(left: Tile, y: Int) {
        var curTile = left
        (1 until imageWidthInTiles).forEach { x ->
            curTile = findMatchingTile(curTile, Edge.RIGHT)
            saveTile(curTile, y)
        }
    }

    fun fillLeft(y: Int): Tile {
        val above = imageTiles[y - 1][0]
        val nextTile = findMatchingTile(above, Edge.BOTTOM)
        saveTile(nextTile, y)
        return nextTile
    }

    fillRow(topLeftTile, 0)

    (1 until imageWidthInTiles).forEach { y ->
        imageTiles.add(mutableListOf())
        val left = fillLeft(y)
        fillRow(left, y)
    }

    val image = tilesToImage(imageTiles)

    return countNonMonsterHashes(image)
}

