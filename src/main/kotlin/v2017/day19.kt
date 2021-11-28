package v2017

private enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun oppositeDirection(direction: Direction): Direction {
            return when (direction) {
                UP -> DOWN
                DOWN -> UP
                LEFT -> RIGHT
                RIGHT -> LEFT
            }
        }
    }
}

private data class Pos(val x: Int, val y: Int)

private fun walkPath(input: List<String>): Sequence<Char> {
    var pos = Pos(input.first().indexOf('|'), 0)
    var direction = Direction.DOWN

    return sequence {
        while (true) {
            val char = input.getOrNull(pos.y)?.getOrNull(pos.x)
            if (char == null || char == ' ') {
                return@sequence
            }
            yield(char)
            if (char == '+') {
                val (nextPos, nextDirection) = listOf(
                    Pos(pos.x, pos.y - 1) to Direction.UP,
                    Pos(pos.x, pos.y + 1) to Direction.DOWN,
                    Pos(pos.x - 1, pos.y) to Direction.LEFT,
                    Pos(pos.x + 1, pos.y) to Direction.RIGHT
                ).single { (pos, nextDirection) ->
                    pos.y in input.indices
                        && pos.x in input[pos.y].indices
                        && input[pos.y][pos.x] != ' '
                        && direction != Direction.oppositeDirection(nextDirection)
                }
                pos = nextPos
                direction = nextDirection
            } else {
                pos = when (direction) {
                    Direction.UP -> Pos(pos.x, pos.y - 1)
                    Direction.DOWN -> Pos(pos.x, pos.y + 1)
                    Direction.LEFT -> Pos(pos.x - 1, pos.y)
                    Direction.RIGHT -> Pos(pos.x + 1, pos.y)
                }
            }
        }
    }
}

fun day19a(input: List<String>): String {
    return walkPath(input).filter(Char::isLetter).joinToString("")
}

fun day19b(input: List<String>): Int {
    return walkPath(input).count()
}
