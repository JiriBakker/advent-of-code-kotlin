package v2019.days.day11

import util.getBounds
import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes

private enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}

private const val BLACK = 0L
private const val WHITE = 1L

private fun turn(curDirection: Direction, instruction: Long): Direction {
    return when (instruction) {
        0L -> when (curDirection) {
            Direction.UP -> Direction.LEFT
            Direction.LEFT -> Direction.DOWN
            Direction.DOWN -> Direction.RIGHT
            Direction.RIGHT -> Direction.UP
        }
        else -> when (curDirection) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }
}

private fun paint(intCodes: MutableMap<Long, Long>, startColor: Long): Map<Pair<Long, Long>, Long> {
    var curDirection = Direction.UP
    var curX = 0L
    var curY = 0L
    val painting = mutableMapOf<Pair<Long, Long>, Long>()
    painting[0L to 0L] = startColor

    generateProgramOutput(intCodes) { painting.getOrDefault(curX to curY, BLACK) }
        .chunked(2)
        .forEach { (color, turnInstruction) ->
            painting[curX to curY] = color

            curDirection = turn(curDirection, turnInstruction)
            when (curDirection) {
                Direction.UP    -> curY--
                Direction.RIGHT -> curX++
                Direction.DOWN  -> curY++
                Direction.LEFT  -> curX--
            }
        }

    return painting
}

fun day11a(input: String): Int {
    val intCodes = parseIntCodes(input)

    val painting = paint(intCodes, BLACK)

    return painting.size
}

fun day11b(input: String): String {
    val intCodes = parseIntCodes(input)

    val painting = paint(intCodes, WHITE)

    val whitePixels = painting.filter { it.value == WHITE }.keys
    val (minX, maxX) = whitePixels.map { it.first }.getBounds { it }
    val (minY, maxY) = whitePixels.map { it.second }.getBounds { it }

    return LongRange(minY, maxY).map { y ->
        LongRange(minX, maxX).map { x ->
            if (whitePixels.contains(x to y)) '█' else ' '
        }.joinToString("")
    }.joinToString("\n")
}
