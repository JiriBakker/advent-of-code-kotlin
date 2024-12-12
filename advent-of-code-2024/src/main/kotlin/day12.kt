import util.sumOfLong

fun day12a(input: List<String>) =
    input
        .findRegions()
        .sumOf { region ->
            val perimeterCount =
                region.count { (x, y) -> input.isPerimeter(x, y, x - 1, y) } +
                region.count { (x, y) -> input.isPerimeter(x, y, x + 1, y) } +
                region.count { (x, y) -> input.isPerimeter(x, y, x, y - 1) } +
                region.count { (x, y) -> input.isPerimeter(x, y, x, y + 1) }
            perimeterCount * region.size
        }

fun day12b(input: List<String>) =
    input
        .findRegions()
        .sumOf { region ->
            val westPerimeters = region.filter { (x, y) -> input.isPerimeter(x, y, x - 1, y) }
            val eastPerimeters = region.filter { (x, y) -> input.isPerimeter(x, y, x + 1, y) }
            val northPerimeters = region.filter { (x, y) -> input.isPerimeter(x, y, x, y - 1) }
            val southPerimeters = region.filter { (x, y) -> input.isPerimeter(x, y, x, y + 1) }

            val westPerimeterPrice = westPerimeters.countVerticalUniquePerimeters()
            val eastPerimeterPrice = eastPerimeters.countVerticalUniquePerimeters()
            val northPerimeterPrice = northPerimeters.countHorizontalUniquePerimeters()
            val southPerimeterPrice = southPerimeters.countHorizontalUniquePerimeters()

            (westPerimeterPrice + eastPerimeterPrice + northPerimeterPrice + southPerimeterPrice) * region.size
        }

private fun List<String>.findRegions(): List<List<Pair<Int,Int>>> {
    val visited = mutableSetOf<Pair<Int,Int>>()

    val regions = mutableListOf<List<Pair<Int,Int>>>()

    for (y in this.indices) {
        for (x in this[y].indices) {
            if (x to y in visited) continue
            val label = this[y][x]

            val curRegion = mutableListOf<Pair<Int,Int>>()

            val toVisit = mutableListOf(x to y)
            fun explore(x: Int, y: Int) {
                if (y !in this.indices || x !in this[y].indices) return
                if (this[y][x] != label) return

                toVisit.add(x to y)
            }

            while (toVisit.isNotEmpty()) {
                val (curX, curY) = toVisit.removeAt(0)

                if (!visited.add(curX to curY)) continue

                curRegion.add(curX to curY)

                explore(curX - 1, curY)
                explore(curX + 1, curY)
                explore(curX, curY - 1)
                explore(curX, curY + 1)
            }

            regions.add(curRegion)
        }
    }

    return regions
}

private fun List<String>.isPerimeter(x: Int, y: Int, neighbourX: Int, neighbourY: Int): Boolean {
    if (neighbourY !in this.indices || neighbourX !in this[neighbourY].indices) {
        return true
    } else if (this[neighbourY][neighbourX] != this[y][x]) {
        return true
    }
    return false
}

private fun List<Pair<Int,Int>>.countHorizontalUniquePerimeters(): Long {
    return this
        .groupBy { it.second }
        .sumOfLong { (_, xs) ->
            xs
                .map { it.first }
                .sorted()
                .zipWithNext()
                .sumOfLong { (x1, x2) ->
                    if (x2 - x1 > 1) {
                        1L
                    } else {
                        0L
                    }
                } + 1
        }
}

private fun List<Pair<Int,Int>>.countVerticalUniquePerimeters(): Long {
    return this
        .groupBy { it.first }
        .sumOfLong { (_, ys) ->
            ys
                .map { it.second }
                .sorted()
                .zipWithNext()
                .sumOfLong { (y1, y2) ->
                    if (y2 - y1 > 1) {
                        1L
                    } else {
                        0L
                    }
                } + 1
        }
}