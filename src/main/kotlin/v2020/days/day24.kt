package v2020.days.day24

import util.min

private class Tile(directions: Map<String, Int>) {

    private val directions: Map<String, Int>

    init {
        this.directions = directions.toMutableMap()
        fun reduce(first: String, second: String, result: String? = null): Boolean {
            val min = min(this.directions[first]!!, this.directions[second]!!)
            if (min > 0) {
                this.directions[first] = this.directions[first]!! - min
                this.directions[second] = this.directions[second]!! - min
                if (result != null) {
                    this.directions[result] = this.directions[result]!! + min
                }
            }
            return min > 0
        }

        do {
            val hadChanges = listOf(
                reduce("w", "e"),
                reduce("nw", "se"),
                reduce("ne", "sw"),
                reduce("se", "w", "sw"),
                reduce("sw", "e", "se"),
                reduce("ne", "w", "nw"),
                reduce("nw", "e", "ne"),
                reduce("nw", "sw", "w"),
                reduce("ne", "se", "e"),
            )

        } while (hadChanges.any { it })
    }

    val hash get() =
        "sw-${directions["sw"]}_se-${directions["se"]}_nw-${directions["nw"]}_ne-${directions["ne"]}_w-${directions["w"]}_e-${directions["e"]}"

    fun createNeighbour(direction: String):Tile {
        val neighbourDirections = this.directions.toMutableMap()
        neighbourDirections[direction] = neighbourDirections[direction]!! + 1
        return Tile(neighbourDirections)
    }

}

private val defaultDirections = mapOf("se" to 0, "sw" to 0, "ne" to 0, "nw" to 0, "e" to 0, "w" to 0)

private fun parseInput(input: List<String>): List<Tile> {
    return input.map { line ->
        val directions = defaultDirections.toMutableMap()
        var i = 0
        while (i in line.indices) {
            when (val direction = line.drop(i).take(2)) {
                "se" -> directions["se"] = directions["se"]!! + 1
                "sw" -> directions["sw"] = directions["sw"]!! + 1
                "ne" -> directions["ne"] = directions["ne"]!! + 1
                "nw" -> directions["nw"] = directions["nw"]!! + 1
                else -> {
                    if (direction[0] == 'e') {
                        directions["e"] = directions["e"]!! + 1
                    } else {
                        directions["w"] = directions["w"]!! + 1
                    }
                    i--
                }
            }
            i += 2
        }
        Tile(directions)
    }
}


private fun countBlackTiles(tiles: List<Tile>): Int {
    val hashes = tiles.map(Tile::hash)

    return hashes.groupBy { it }.count { it.value.size % 2 == 1 }
}

fun day24a(input: List<String>): Int {
    val tiles = parseInput(input)

    return countBlackTiles(tiles)
}

private fun flipTiles(tiles: List<Tile>): List<Tile> {
    val counts = tiles.map(Tile::hash).groupBy { it }

    val toCheck = tiles.toMutableList()
    tiles.forEach { tile ->
        val neighbours = listOf(
            tile.createNeighbour("sw"),
            tile.createNeighbour("se"),
            tile.createNeighbour("nw"),
            tile.createNeighbour("ne"),
            tile.createNeighbour("w"),
            tile.createNeighbour("e"),
        )
        toCheck.addAll(neighbours)
    }
    val checked = mutableSetOf<String>()

    return toCheck.mapNotNull { tile ->
        if (!checked.add(tile.hash)) {
            null
        } else {

            val neighbourHashes = listOf(
                tile.createNeighbour("sw").hash,
                tile.createNeighbour("se").hash,
                tile.createNeighbour("nw").hash,
                tile.createNeighbour("ne").hash,
                tile.createNeighbour("w").hash,
                tile.createNeighbour("e").hash,
            )

            val blackNeighbourCount = neighbourHashes.count { hash ->
                (counts[hash]?.let { it.size % 2 == 1 } ?: false)
            }
            val isBlackTile = counts[tile.hash]?.let { it.size % 2 == 1 } ?: false


            if (isBlackTile) {
                if (blackNeighbourCount == 0 || blackNeighbourCount > 2) {
                    null
                } else {
                    tile
                }
            } else if (blackNeighbourCount == 2) {
                tile
            } else {
                null
            }
        }
    }
}

fun day24b(input: List<String>): Int {
    val initialTiles = parseInput(input)

    var tiles = initialTiles

    repeat(100) {
        tiles = flipTiles(tiles)
    }


    return countBlackTiles(tiles)
}


