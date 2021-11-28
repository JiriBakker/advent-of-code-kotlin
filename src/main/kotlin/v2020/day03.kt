package v2020

private fun countTrees(grid: List<String>, stepX: Int, stepY: Int): Long {
    val gridWidth = grid[0].length
    var curX = 0
    var curY = 0

    var treeCount = 0L

    while (curY < grid.size) {
        treeCount += if (grid[curY][curX % gridWidth] == '#') 1 else 0
        curX += stepX
        curY += stepY
    }

    return treeCount
}

fun day03a(input: List<String>): Long {
    return countTrees(input, 3, 1)
}

fun day03b(input: List<String>): Long {
    return countTrees(input, 1, 1) *
        countTrees(input, 3, 1) *
        countTrees(input, 5, 1) *
        countTrees(input, 7, 1) *
        countTrees(input, 1, 2)
}

