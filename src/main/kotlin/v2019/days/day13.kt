package v2019.days.day13

import v2019.intCoder.parseIntCodes
import v2019.intCoder.generateProgramOutput
import kotlin.math.max
import kotlin.math.min

private const val EMPTY = 0L
private const val WALL = 1L
private const val BLOCK = 2L
private const val PADDLE = 3L
private const val BALL = 4L

private class Pos(val x: Long, val y: Long)

fun day13a(input: String): Int {
    val intCodes = parseIntCodes(input)

    return generateProgramOutput(intCodes)
        .chunked(3)
        .count { it[2] == BLOCK }
}

private fun computePaddleMove(paddle: Pos, ball: Pos, prevBall: Pos): Long {
    val ballDelta = ball.x - prevBall.x

    return if (ball.x == paddle.x && ball.y < paddle.y - 1) {
        ballDelta
    } else if (ball.x > paddle.x) {
        max(0L, ballDelta)
    } else {
        min(0L, ballDelta)
    }
}

private fun isScoreCoordinate(x: Long, y: Long): Boolean = x == -1L && y == 0L

fun day13b(input: String): Long {
    val intCodes = parseIntCodes(input)
    intCodes[0L] = 2L

    var paddlePos = Pos(0L, 0L)
    var prevBallPos = Pos(0L, 0L)
    var ballPos = Pos(0L, 0L)
    var score = 0L

    val outputCollector = mutableListOf<Long>()

    val inputProvider = { computePaddleMove(paddlePos, ballPos, prevBallPos) }

    generateProgramOutput(intCodes, inputProvider)
        .forEach { output ->
            outputCollector.add(output)
            if (outputCollector.size == 3) {
                val (x, y, value) = outputCollector
                when {
                    isScoreCoordinate(x, y) -> score = value
                    value == PADDLE -> paddlePos = Pos(x, y)
                    value == BALL -> {
                        prevBallPos = ballPos
                        ballPos = Pos(x, y)
                    }
                }
                outputCollector.clear()
            }
        }

    return score
}
