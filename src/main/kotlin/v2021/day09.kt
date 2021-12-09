package v2021

import util.product

private fun List<List<Int>>.getNeighbourPositions(x: Int, y: Int) =
    listOfNotNull(
        if (x > 0)                x - 1 to y else null,
        if (x < this[0].size - 1) x + 1 to y else null,
        if (y > 0)                x to y - 1 else null,
        if (y < this.size - 1)    x to y + 1 else null
    )

private fun List<List<Int>>.findLowPoints(): List<Pair<Int, Int>> {
    val grid = this

    fun List<Pair<Int, Int>>.allHigherThan(value: Int) =
        all { (x, y) -> grid[y][x] > value }

    return flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, value ->
            val neighbours = getNeighbourPositions(x, y)
            if (neighbours.allHigherThan(value))
                x to y
            else
                null
        }
    }
}

fun day09a(input: List<String>): Int {
    val grid = input.map { row -> row.map { it.toString().toInt() } }

    val lowPoints = grid.findLowPoints()

    return lowPoints.sumOf { (x, y) -> grid[y][x] + 1 }
}

fun day09b(input: List<String>): Int {
    val grid = input.map { row -> row.map { it.toString().toInt() } }

    val lowPoints = grid.findLowPoints()

    fun getBasinSize(x: Int, y: Int): Int {
        val checked = mutableSetOf<Pair<Int, Int>>()
        val toCheck = ArrayDeque<Pair<Int, Int>>()

        toCheck.add(x to y)
        var size = 0

        while (toCheck.isNotEmpty()) {
            val curPos = toCheck.removeFirst()

            val curX = curPos.first
            val curY = curPos.second

            if (grid[curY][curX] == 9) continue
            if (checked.contains(curPos)) continue

            checked.add(curPos)
            size++

            toCheck.addAll(
                grid.getNeighbourPositions(curX, curY)
            )
        }

        return size
    }

    val basinSizes = lowPoints.map { (x, y) -> getBasinSize(x, y) }

    return basinSizes
        .sortedDescending()
        .take(3)
        .product()
}