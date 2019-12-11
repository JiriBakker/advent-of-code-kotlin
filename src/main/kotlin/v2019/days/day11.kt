package v2019.days.day11

import v2019.getBounds
import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram

private enum class Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}

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

private fun paint(intCodes: Map<Long, Long>, startColor: Long): Map<Pair<Long, Long>, Long> {
    var curDirection = Direction.UP
    var curX = 0L
    var curY = 0L
    val painting = mutableMapOf<Pair<Long, Long>, Long>()
    painting[Pair(0L, 0L)] = startColor

    var state = ProgramState(intCodes)
    while (true) {
        state = runProgram(state.withInputs(listOf(painting.getOrDefault(Pair(curX, curY), 0L))))
        if (state.output == null) {
            break
        }

        painting[Pair(curX, curY)] = state.output!!

        state = runProgram(state.withInputs(listOf(painting.getOrDefault(Pair(curX, curY), 0L))))
        val turnInstruction = state.output

        curDirection = turn(curDirection, turnInstruction!!)

        when (curDirection) {
            Direction.UP -> curY--
            Direction.RIGHT -> curX++
            Direction.DOWN -> curY++
            Direction.LEFT -> curX--
        }
    }

    return painting
}

fun day11a(input: String): Int {
    val intCodes = parseIntCodes(input)

    val painting = paint(intCodes, 0)

    return painting.size
}

fun day11b(input: String): String {
    val intCodes = parseIntCodes(input)

    val painting = paint(intCodes, 1)

    val whitePixels = painting.filter { it.value == 1L }.keys
    val (minX, maxX) = whitePixels.map { it.first }.getBounds { it }
    val (minY, maxY) = whitePixels.map { it.second }.getBounds { it }

    val output = StringBuilder()
    for (y in minY .. maxY) {
        for (x in minX .. maxX) {
            output.append(if (whitePixels.contains(Pair(x, y))) '█' else ' ')
        }
        output.append('\n')
    }
    return output.toString()
}
