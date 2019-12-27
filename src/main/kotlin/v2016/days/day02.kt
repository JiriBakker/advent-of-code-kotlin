package v2016.days.day02


import kotlin.math.abs

data class Pos(val x: Int, val y: Int) {
    fun manhattanDistance(): Int = abs(this.x) + abs(this.y)

    companion object {
        val ORIGIN = Pos(0, 0)
    }
}


fun move(instructions: String, startPos: Pos, isValidPos: (Pos) -> Boolean): Pos {
    return instructions.fold(startPos) { pos, direction ->
        val nextPos = when (direction) {
            'U' -> Pos(pos.x, pos.y - 1)
            'D' -> Pos(pos.x, pos.y + 1)
            'L' -> Pos(pos.x - 1, pos.y)
            'R' -> Pos(pos.x + 1, pos.y)
            else -> error("Unknown direction: $direction")
        }
        if (isValidPos(nextPos)) nextPos else pos
    }
}

fun day02a(input: List<String>): Int {
    return sequence {
        input.fold(Pos(1, 1)) { pos, instructions ->
            val nextPos = move(instructions, pos) { (x, y) -> x in 0..2 && y in 0..2 }
            yield(nextPos.x + 1 + nextPos.y * 3)
            nextPos
        }
    }.joinToString("").toInt()
}

fun day02b(input: List<String>): String {
    fun isValidPos(pos: Pos): Boolean {
        return pos.y in 0..4
            && pos.x >= abs(2 - pos.y)
            && pos.x <= 4 - abs(2 - pos.y)
    }

    fun keyFromPos(pos: Pos): Char {
        return when (pos.y) {
            0 -> '1'
            1 -> (pos.x + 1).toString()[0]
            2 -> (pos.x + 5).toString()[0]
            3 -> 'A' + (pos.x - 1)
            4 -> 'D'
            else -> error("Outside of keypad range: $pos")
        }
    }

    return sequence {
        input.fold(Pos(0, 2)) { pos, instructions ->
            val nextPos = move(instructions, pos, ::isValidPos)
            yield(keyFromPos(nextPos))
            nextPos
        }
    }.joinToString("")
}