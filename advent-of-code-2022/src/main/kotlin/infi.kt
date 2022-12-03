import Infi.Direction.*
import Infi.followInstructions
import Infi.print
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun infiA(input: List<String>): Long {
    var curX = 0L
    var curY = 0L

    followInstructions(instructions = input) { x, y ->
        curX = x
        curY = y
    }

    return abs(curX) + abs(curY)
}

fun infiB(input: List<String>) {
    val grid = mutableMapOf<Long, MutableSet<Long>>()

    followInstructions(instructions = input) { x, y ->
        grid.getOrPut(y) { mutableSetOf() }.add(x)
    }

    grid.print()
}

object Infi {

    fun followInstructions(
        instructions: List<String>,
        visitedCallback: (Long, Long) -> Unit = {_,_->}
    ) {
        var curDirection = NORTH
        var curX = 0L
        var curY = 0L

        instructions.forEach { line ->
            val (operation, amount) = line.split(" ")

            when (operation) {
                "draai" ->
                    curDirection = curDirection.rotate(amount.toInt())

                "loop" -> {
                    repeat(amount.toInt()) {
                        curX += curDirection.dx
                        curY += curDirection.dy
                        visitedCallback(curX, curY)
                    }
                }

                "spring" -> {
                    curX += amount.toInt() * curDirection.dx
                    curY += amount.toInt() * curDirection.dy
                    visitedCallback(curX, curY)
                }
            }
        }
    }

    enum class Direction(val dx: Int, val dy: Int) {
        NORTH(0, -1),
        NORTH_EAST(1, -1),
        EAST(1, 0),
        SOUTH_EAST(1, 1),
        SOUTH(0, 1),
        SOUTH_WEST(-1, 1),
        WEST(-1, 0),
        NORTH_WEST(-1, -1);

        fun rotate(angle: Int): Direction {
            return if (angle == 0) {
                this
            } else if (angle < 0) {
                rotateLeft(angle)
            } else {
                rotateRight(angle)
            }
        }

        private fun rotateRight(angle: Int): Direction {
            val next = when (this) {
                NORTH -> NORTH_EAST
                NORTH_EAST -> EAST
                EAST -> SOUTH_EAST
                SOUTH_EAST -> SOUTH
                SOUTH -> SOUTH_WEST
                SOUTH_WEST -> WEST
                WEST -> NORTH_WEST
                NORTH_WEST -> NORTH
            }

            return next.rotate(angle - 45)
        }

        private fun rotateLeft(angle: Int): Direction {
            val next = when (this) {
                NORTH -> NORTH_WEST
                NORTH_EAST -> NORTH
                EAST -> NORTH_EAST
                SOUTH_EAST -> EAST
                SOUTH -> SOUTH_EAST
                SOUTH_WEST -> SOUTH
                WEST -> SOUTH_WEST
                NORTH_WEST -> WEST
            }

            return next.rotate(angle + 45)
        }
    }

    fun Map<Long, Set<Long>>.print() {
        val minY = this.keys.min()
        val maxY = this.keys.max()

        var minX = Long.MAX_VALUE
        var maxX = Long.MIN_VALUE
        (minY .. maxY).forEach { y ->
            minX = min(minX, this[y]!!.min())
            maxX = max(maxX, this[y]!!.max())
        }

        (minY .. maxY).forEach { y ->
            (minX .. maxX).forEach { x ->
                if (this[y]?.contains(x) == true)
                    print("x")
                else
                    print(" ")
            }
            println()
        }
    }
}