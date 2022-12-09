import Day08.Forest
import util.normalize

fun day08a(input: List<String>): Int {
    val forest = Forest(input)

    fun countVisibleEdge(x: Int, y: Int, maxHeight: Int, dx: Int, dy: Int): Int {
        var curY = y + dy
        var curX = x + dx
        while (curY in 0 until forest.height && curX in 0 until forest.width) {
            if (forest.grid[curY][curX] >= maxHeight) {
                return 0
            }
            curX += dx
            curY += dy
        }
        return 1
    }

    fun computeScore(topScore: Int, rightScore: Int, bottomScore: Int, leftScore: Int) =
        (topScore + rightScore + bottomScore + leftScore).normalize()

    val scores =
        forest.computeViewScores(
            ::countVisibleEdge,
            ::computeScore
        )

    return scores.sumOf { it.sum() }
}

fun day08b(input: List<String>): Int {
    val forest = Forest(input)

    fun countVisibleTrees(x: Int, y: Int, minHeight: Int, dx: Int, dy: Int): Int {
        var visibleCount = 0
        var curY = y + dy
        var curX = x + dx
        while (curY in 0 until forest.height && curX in 0 until forest.width) {
            if (forest.grid[curY][curX] >= minHeight) {
                return visibleCount + 1
            }

            visibleCount++

            curX += dx
            curY += dy
        }
        return visibleCount
    }

    fun computeScore(topScore: Int, rightScore: Int, bottomScore: Int, leftScore: Int) =
        (topScore * rightScore * bottomScore * leftScore)

    val scores =
        forest.computeViewScores(
            ::countVisibleTrees,
            ::computeScore
        )

    return scores.maxOf { it.max() }
}

object Day08 {
    class Forest(input: List<String>) {
        val grid = input.map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()
        val height = grid.size
        val width = grid.firstOrNull()?.size ?: 0

        fun computeViewScores(
            viewFunc: (x: Int, y: Int, height: Int, dx: Int, dy: Int) -> Int,
            scoreFunc: (top: Int, right: Int, bottom: Int, left: Int) -> Int
        ): List<List<Int>> {
            val scores =
                (0 until this.height).map { y ->
                    (0 until this.width).map { x ->
                        val treeHeight = this.grid[y][x]

                        scoreFunc.invoke(
                            viewFunc(x, y, treeHeight,  0, -1),
                            viewFunc(x, y, treeHeight, -1,  0),
                            viewFunc(x, y, treeHeight,  0,  1),
                            viewFunc(x, y, treeHeight,  1,  0)
                        )
                    }
                }

            return scores
        }
    }
}



