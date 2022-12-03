import kotlin.math.abs

import Day01.Pos

private object Day01 {
    data class Pos(val x: Int, val y: Int) {
        fun turnRight(): Pos = Pos(-y, x)
        fun turnLeft(): Pos = Pos(y, -x)
        fun plus(other: Pos): Pos = Pos(x + other.x, y + other.y)
        fun manhattanDistance(): Int = abs(this.x) + abs(this.y)

        companion object {
            val ORIGIN = Pos(0, 0)
        }
    }
}

private fun parseMoves(input: String): List<Pair<Char, Int>> {
    return input
        .split(" ")
        .map { it.trimEnd(',') }
        .map { it[0] to it.drop(1).toInt() }
}

private fun walk(moves: List<Pair<Char, Int>>): Sequence<Pos> {
    var delta = Pos(0,-1)
    return sequence {
        moves.fold(Pos.ORIGIN) { (x, y), (direction, distance) ->
            delta = if (direction == 'R') {
                delta.turnRight()
            } else {
                delta.turnLeft()
            }

            var pos = Pos(x, y)
            repeat(distance) {
                pos = pos.plus(delta)
                yield(pos)
            }
            pos
        }
    }
}

fun day01a(input: String): Int {
    val moves = parseMoves(input)

    return walk(moves)
        .last()
        .manhattanDistance()
}

fun day01b(input: String): Int {
    val moves = parseMoves(input)

    val visited = mutableSetOf(Pos.ORIGIN)
    return walk(moves)
        .first { !visited.add(it) }
        .manhattanDistance()
}
