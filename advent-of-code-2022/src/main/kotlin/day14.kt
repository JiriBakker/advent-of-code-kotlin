import Day14.Grid.Companion.parseGrid
import util.max
import util.normalize

private const val INFINITY = 10000

fun day14a(input: List<String>): Int {
    val grid = input.parseGrid(201, 200)

    var sandCount = 0
    while (grid.dropSand()) {
        sandCount++
    }

    return sandCount
}

fun day14b(input: List<String>): Int {
    val grid = input.parseGrid(0, 200)

    var sandCount = 0
    do {
        sandCount++
    } while (grid.dropSand())

    return sandCount
}

object Day14 {

    class Grid private constructor(
        private val grid: MutableMap<Int, MutableMap<Int, Char>>,
        private val floorY: Int,
        private val ignoreY: Int
    ) {
        private fun getTile(x: Int, y: Int) =
            grid[y]?.get(x)

        private fun isBlocked(x: Int, y: Int) =
            y >= floorY || getTile(x, y) != null

        fun dropSand(): Boolean {
            var sandX = 500
            var sandY = 0

            while (sandY < ignoreY) {
                if (!isBlocked(sandX, sandY + 1)) {
                    sandY++
                } else if (!isBlocked(sandX - 1, sandY + 1)) {
                    sandY++
                    sandX--
                } else if (!isBlocked(sandX + 1, sandY + 1)) {
                    sandY++
                    sandX++
                } else {
                    grid.setTile(sandX, sandY, 'o')
                    break
                }
            }

            return sandY in 1 until ignoreY
        }

        companion object {
            fun List<String>.parseGrid(minFloorY: Int, ignoreY: Int): Grid {
                val grid = mutableMapOf<Int, MutableMap<Int, Char>>()

                var floorY = minFloorY
                this.forEach { line ->
                    val coordinates =
                        line.split(" -> ").map { it.split(",").map(String::toInt).let { (x, y) -> x to y } }
                    var curX = coordinates[0].first
                    var curY = coordinates[0].second

                    coordinates.drop(1).forEach { (targetX, targetY) ->
                        val dx = (targetX - curX).normalize()
                        val dy = (targetY - curY).normalize()

                        while (curX != targetX || curY != targetY) {
                            grid.setTile(curX, curY, '#')
                            curX += dx
                            curY += dy

                            floorY = max(floorY, curY + 2)
                        }
                        grid.setTile(targetX, targetY, '#')
                    }
                }
                return Grid(grid, floorY, ignoreY)
            }

            private fun MutableMap<Int, MutableMap<Int, Char>>.setTile(x: Int, y: Int, char: Char) {
                this.getOrPut(y) { mutableMapOf() }[x] = char
            }
        }
    }

}