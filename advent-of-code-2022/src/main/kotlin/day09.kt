import Day09.print
import util.normalize
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun day09a(input: List<String>) =
    input.followInstructions(1)

fun day09b(input: List<String>) =
    input.followInstructions(9)

private fun List<String>.followInstructions(tailKnotCount: Int): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val tailKnots = (1 .. tailKnotCount).associateWith { 0 to 0 }.toMutableMap()

    var headX = 0
    var headY = 0

    fun follow(startX: Int, startY: Int) {
        var followX = startX
        var followY = startY

        tailKnots.forEach { (i, knot) ->
            var (knotX, knotY) = knot
            val deltaX = followX - knotX
            val deltaY = followY - knotY

            val areTouching = abs(deltaX) <= 1 && abs(deltaY) <= 1
            if (!areTouching) {
                knotX += deltaX.normalize()
                knotY += deltaY.normalize()
            }

            tailKnots[i] = knotX to knotY
            followX = knotX
            followY = knotY
        }
    }

    fun markVisited() =
        visited.add(tailKnots.values.last())

    fun moveHead(deltaX: Int, deltaY: Int, distance: Int) {
        repeat(distance) {
            if (deltaX != 0) {
                headX += deltaX
                follow(headX, headY)
            }
            if (deltaY != 0) {
                headY += deltaY
                follow(headX, headY)
            }
            markVisited()
        }
    }

    markVisited()

    this.forEach { line ->
        val (direction, distance) = line.split(" ")
        when (direction) {
            "U" -> moveHead( 0, -1, distance.toInt())
            "R" -> moveHead( 1,  0, distance.toInt())
            "D" -> moveHead( 0,  1, distance.toInt())
            "L" -> moveHead(-1,  0, distance.toInt())
        }
    }

    return visited.size
}

object Day09 {

    fun Map<Int, Pair<Int, Int>>.print(headX: Int, headY: Int, visited: Set<Pair<Int, Int>>) {
        var minX = headX
        var maxX = headX
        var minY = headY
        var maxY = headY
        this.values.forEach { (x, y) ->
            minX = min(minX, x)
            maxX = max(maxX, x)
            minY = min(minY, y)
            maxY = max(maxY, y)
        }

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                when {
                    x == headX && y == headY ->
                        print("H")

                    this.values.contains(x to y) -> {
                        var index = 1
                        while (this[index] != x to y) {
                            index++
                        }
                        print(index)
                    }

                    visited.contains(x to y) ->
                        print("#")

                    x == 0 && y == 0 ->
                        print("s")

                    else ->
                        print(".")
                }
                println()
            }
            println()
        }
    }
}