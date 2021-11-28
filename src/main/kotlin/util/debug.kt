package util

fun printGrid(grid: List<List<Char>>) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            print(grid[y][x])
        }
        println()
    }
}