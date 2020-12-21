package v2020.days.day20

import util.flippedHorizontal
import util.flipVertical
import util.product
import util.rotatedLeft
import util.rotatedRight
import util.rotatedTwice
import util.splitByDoubleNewLine
import java.util.SortedMap

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

private class Tile private constructor(val id: Long, val grid: List<List<Char>>) {
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

        val baseEdges = mutableMapOf(
            Edge.TOP to collect(0, 0, 1, 0),
            Edge.LEFT to collect(0, 9, 0, -1),
            Edge.RIGHT to collect(9, 0, 0, 1),
            Edge.BOTTOM to collect(9, 9, -1, 0)
        )
        edges = baseEdges.plus(listOf(
            Edge.FLIPPED_TOP to baseEdges[Edge.TOP]!!.reversed(),
            Edge.FLIPPED_LEFT to baseEdges[Edge.LEFT]!!.reversed(),
            Edge.FLIPPED_RIGHT to baseEdges[Edge.RIGHT]!!.reversed(),
            Edge.FLIPPED_BOTTOM to baseEdges[Edge.BOTTOM]!!.reversed()
        ))
    }

    fun findAlignment(other: Tile, edge: Edge): Edge? {
        return other.edges.entries.firstOrNull { (_, otherChars) -> this.edges[edge] == otherChars }?.key
    }

    override fun toString(): String = "Tile $id"

    fun align(alignment: Pair<Edge, Edge>): Tile {
        return when (alignment.first) {
            Edge.RIGHT -> {
                when (alignment.second) {
                    Edge.LEFT -> this.flipVertical()
                    Edge.BOTTOM -> this.flipHorizontal().rotateRight()
                    Edge.RIGHT -> this.flipHorizontal()
                    Edge.TOP -> this.flipHorizontal().rotateLeft()
                    Edge.FLIPPED_LEFT -> this
                    Edge.FLIPPED_TOP -> this.rotateLeft()
                    Edge.FLIPPED_RIGHT -> this.rotateTwice()
                    Edge.FLIPPED_BOTTOM -> this.rotateRight()
                }
            }
            Edge.BOTTOM -> {
                when (alignment.second) {
                    Edge.TOP -> this.flipHorizontal()
                    Edge.LEFT -> this.flipVertical().rotateRight()
                    Edge.BOTTOM -> this.flipVertical()
                    Edge.RIGHT -> this.flipVertical().rotateLeft()
                    Edge.FLIPPED_BOTTOM -> this.rotateTwice()
                    Edge.FLIPPED_LEFT -> this.rotateRight()
                    Edge.FLIPPED_TOP -> this
                    Edge.FLIPPED_RIGHT -> this.rotateLeft()
                }
            }
            else -> error("Can only align on RIGHT and BOTTOM, not ${alignment.first}")
        }
    }

    fun rotateRight(): Tile = Tile(this.id, this.grid.rotatedRight())
    fun rotateLeft(): Tile = Tile(this.id, this.grid.rotatedLeft())
    fun rotateTwice(): Tile = Tile(this.id, this.grid.rotatedTwice())
    fun flipHorizontal(): Tile = Tile(this.id, this.grid.flippedHorizontal())
    fun flipVertical(): Tile = Tile(this.id, this.grid.flipVertical())

    companion object {
        fun parse(lines: List<String>): Tile {
            val id = lines.first().replace("Tile ", "").replace(":", "").toLong()
            return Tile(id, lines.drop(1).map { it.toList() } )
        }
    }
}

private fun List<List<Char>>.countNonMonsterHashes(): Int {
    val baseMonster: List<List<Char>> = listOf(
        "                  # ".toList(),
        "#    ##    ##    ###".toList(),
        " #  #  #  #  #  #   ".toList()
    )

    fun List<List<Char>>.countHashes(): Int =
        this.sumBy { row -> row.count { it == '#' } }

    fun monsterSearch(monster: List<List<Char>>): Int {
        val grid = this.map { it.toMutableList() }
        for (y in 0 until grid.size - monster.size) {
            (0 until grid[y].size - monster[0].size).forEach match@{ x ->
                for (my in monster.indices) {
                    for (mx in monster[my].indices) {
                        if (monster[my][mx] == '#') {
                            if (grid[y + my][x + mx] != '#') {
                                return@match
                            }
                        }
                    }
                }
                for (my in monster.indices) {
                    for (mx in monster[my].indices) {
                        if (monster[my][mx] == '#') {
                            grid[y + my][x + mx] = '.'
                        }
                    }
                }
            }
        }
        return grid.countHashes()
    }

    val baseHashCount = this.countHashes()

    return sequenceOf(
        monsterSearch(baseMonster),
        monsterSearch(baseMonster.rotatedRight()),
        monsterSearch(baseMonster.rotatedTwice()),
        monsterSearch(baseMonster.rotatedLeft()),
        monsterSearch(baseMonster.flippedHorizontal()),
        monsterSearch(baseMonster.flippedHorizontal().rotatedRight()),
        monsterSearch(baseMonster.flippedHorizontal().rotatedTwice()),
        monsterSearch(baseMonster.flippedHorizontal().rotatedLeft())
    )
        .first { it < baseHashCount}
}

private fun List<List<Tile>>.combineToImage(): List<List<Char>> {
    val image = mutableListOf<MutableList<Char>>()
    for (y1 in this.indices) {
        for (y2 in 1 until 9) {
            image.add(mutableListOf())
            for (tile in this[y1]) {
                for (x2 in 1 until 9) {
                    image[y1 * 8 + (y2 - 1)].add(tile.grid[y2][x2])
                }
            }
        }
    }
    return image
}

private fun constructNeighbourMap(tiles: List<Tile>): SortedMap<Long, Map<Edge, Long>> {
    return tiles.map { tile ->
        tile.id to tiles.mapNotNull { other ->
            listOf(Edge.TOP, Edge.RIGHT, Edge.BOTTOM, Edge.LEFT)
                .map { edge -> edge to tile.findAlignment(other, edge) }
                .singleOrNull { it.second != null }
                ?.let { other to it.first }
        }
            .map { it.second to it.first.id }
            .toMap()
    }.toMap().toSortedMap()
}

fun day20a(input: List<String>): Long {
    val tiles = input.splitByDoubleNewLine().map(Tile::parse)

    val neighbours = constructNeighbourMap(tiles)

    val corners = neighbours.filter { it.value.size == 2 }

    return corners.keys.product()
}

fun day20b(input: List<String>, imageWidthInTiles: Int = 12): Int {
    val tiles = input.splitByDoubleNewLine().map {
        val tile = Tile.parse(it)
        tile.id to tile
    }.toMap().toMutableMap()

    val neighbours = constructNeighbourMap(tiles.values.toList())

    val topLeftCorner = neighbours.entries.single { it.value.size == 2 && it.value.keys.contains(Edge.BOTTOM) && it.value.keys.contains(Edge.RIGHT) }
    val topLeftTile = tiles[topLeftCorner.key]!!

    val imageTiles = mutableListOf(mutableListOf(topLeftTile))
    repeat(imageWidthInTiles - 1) { imageTiles.add(mutableListOf()) }

    fun Tile.findMatchingTile(edge: Edge): Tile {
        val (nextTile, alignment) =
            neighbours[this.id]!!
                .map {
                    val otherTile = tiles[it.value]!!
                    otherTile to this.findAlignment(otherTile, edge)
                }
                .single { it.second != null }

        return nextTile.align(edge to alignment!!)
    }

    fun Tile.commitToRow(y: Int) {
        imageTiles[y].add(this)
        tiles[this.id] = this
    }

    fun fillRow(left: Tile, y: Int) {
        var curTile = left
        (1 until imageWidthInTiles).forEach { x ->
            curTile = curTile.findMatchingTile(Edge.RIGHT)
            curTile.commitToRow(y)
        }
    }

    fun fillLeft(y: Int): Tile {
        val above = imageTiles[y - 1][0]
        val nextTile = above.findMatchingTile(Edge.BOTTOM)
        nextTile.commitToRow(y)
        return nextTile
    }

    fillRow(topLeftTile, 0)

    (1 until imageWidthInTiles).forEach { y ->
        fillRow(fillLeft(y), y)
    }

    return imageTiles.combineToImage().countNonMonsterHashes()
}