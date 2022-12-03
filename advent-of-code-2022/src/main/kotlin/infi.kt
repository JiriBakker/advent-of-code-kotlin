import Infi.Perspective.*
import Infi.print
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun infiA(input: List<String>): Long {
    var curPerspective = NORTH
    var curX = 0L
    var curY = 0L

    input.forEach { line ->
        val (instruction, amount) = line.split(" ")

        when (instruction) {
            "draai" ->
                curPerspective = curPerspective.rotate(amount.toInt())

            "loop" -> {
                curX += amount.toInt() * curPerspective.dx
                curY += amount.toInt() * curPerspective.dy
            }

            "spring" -> {
                curX += amount.toInt() * curPerspective.dx
                curY += amount.toInt() * curPerspective.dy
            }
        }
    }

    return abs(curX) + abs(curY)
}


fun infiB(input: List<String>): Long {
    var curPerspective = NORTH
    var curX = 0L
    var curY = 0L

    val grid = mutableMapOf<Long, MutableSet<Long>>()
    fun setPos(x: Long, y: Long) {
        grid.getOrPut(y) { mutableSetOf() }.add(x)
    }

    input.forEach { line ->
        val (instruction, amount) = line.split(" ")

        when (instruction) {
            "draai" ->
                curPerspective = curPerspective.rotate(amount.toInt())

            "loop" -> {
                repeat(amount.toInt()) {
                    curX += curPerspective.dx
                    curY += curPerspective.dy
                    setPos(curX, curY)
                }
            }

            "spring" -> {
                curX += amount.toInt() * curPerspective.dx
                curY += amount.toInt() * curPerspective.dy
                setPos(curX, curY)
            }
        }
    }

    grid.print()

    return abs(curX) + abs(curY)
}

object Infi {
    enum class Perspective(val dx: Int, val dy: Int) {
        NORTH(0, -1),
        NORTH_EAST(1, -1),
        EAST(1, 0),
        SOUTH_EAST(1, 1),
        SOUTH(0, 1),
        SOUTH_WEST(-1, 1),
        WEST(-1, 0),
        NORTH_WEST(-1, -1);

        fun rotate(angle: Int): Perspective {
            return if (angle == 0) {
                this
            } else if (angle < 0) {
                rotateLeft(angle)
            } else {
                rotateRight(angle)
            }
        }

        private fun rotateRight(angle: Int): Perspective {
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

        private fun rotateLeft(angle: Int): Perspective {
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
        var minX = Long.MAX_VALUE
        var maxX = Long.MIN_VALUE
        (this.keys.min() .. this.keys.max()).forEach { y ->
            minX = min(minX, this[y]!!.min())
            maxX = max(maxX, this[y]!!.max())
        }

        (this.keys.min() .. this.keys.max()).forEach { y ->
            (minX .. maxX).forEach { x ->
                if (this.containsKey(y) && this[y]!!.contains(x)) print("x")
                else print(" ")
            }
            println()
        }
    }
}