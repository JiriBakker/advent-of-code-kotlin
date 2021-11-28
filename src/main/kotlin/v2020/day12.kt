package v2020

import util.safeMod
import kotlin.math.abs

private fun rotate(dx: Int, dy: Int, angle: Int): Pair<Int, Int> {
    return when (angle.safeMod(360)) {
        90   -> -dy to  dx
        180  -> -dx to -dy
        270  ->  dy to -dx
        else ->  dx to  dy
    }
}

fun day12a(input: List<String>): Int {
    var dx = 1
    var dy = 0

    val (x, y) = input.fold(0 to 0) { (curX, curY), instruction ->
        val action = instruction.first()
        val distance = instruction.drop(1).toInt()

        when (action) {
            'N' -> curX to curY - distance
            'S' -> curX to curY + distance
            'E' -> curX + distance to curY
            'W' -> curX - distance to curY
            'R' -> {
                val (newDx, newDy) = rotate(dx, dy, distance)
                dx = newDx
                dy = newDy
                curX to curY
            }
            'L' -> {
                 val (newDx, newDy) = rotate(dx, dy, -distance)
                dx = newDx
                dy = newDy
                curX to curY
            }
            'F' -> curX + dx * distance to curY + dy * distance
            else -> curX to curY
        }
    }

    return abs(x) + abs(y)
}

fun day12b(input: List<String>): Int {
    var dx = 10
    var dy = -1

    val (x, y) = input.fold(0 to 0) { (curX, curY), instruction ->
        val action = instruction.first()
        val distance = instruction.drop(1).toInt()

        when (action) {
            'N' -> {
                dy -= distance
                curX to curY
            }
            'S' -> {
                dy += distance
                curX to curY
            }
            'E' -> {
                dx += distance
                curX to curY
            }
            'W' -> {
                dx -= distance
                curX to curY
            }
            'R' -> {
                 val (newDx, newDy) = rotate(dx, dy, distance)
                dx = newDx
                dy = newDy
                curX to curY
            }
            'L' -> {
                val (newDx, newDy) = rotate(dx, dy, -distance)
                dx = newDx
                dy = newDy
                curX to curY
            }
            'F' -> curX + dx * distance to curY + dy * distance
            else -> curX to curY
        }
    }

    return abs(x) + abs(y)
}
