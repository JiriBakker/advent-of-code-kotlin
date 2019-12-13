package v2019.days.day13

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram
import kotlin.math.max
import kotlin.math.min

private const val EMPTY = 0L
private const val WALL = 1L
private const val BLOCK = 2L
private const val PADDLE = 3L
private const val BALL = 4L

fun day13a(input: String): Int {
    val intCodes = parseIntCodes(input)

    val initialProgramState = ProgramState(intCodes)

    return generateSequence(initialProgramState) { runProgram(it) }
        .takeWhile { !it.isFinished }
        .mapNotNull { it.output }
        .chunked(3)
        .count { it[2] == BLOCK }
}

private fun computePaddleMove(paddlePos: Pair<Long, Long>, ballPos: Pair<Long, Long>, prevBallPos: Pair<Long, Long>): Long {
    val (paddleX, paddleY) = paddlePos
    val (ballX, ballY) = ballPos
    val (prevBallX, _) = prevBallPos

    val ballDelta = ballX - prevBallX

    return if (ballX == paddleX && ballY < paddleY - 1) {
        ballDelta
    } else if (ballX > paddleX) {
        max(0L, ballDelta)
    } else {
        min(0L, ballDelta)
    }
}

fun day13b(input: String): Long {
    val intCodes = parseIntCodes(input).plus(0L to 2L)

    var prevBallPos = 0L to 0L
    var paddlePos = 0L to 0L
    var ballPos = 0L to 0L
    var score = 0L

    var currentState = ProgramState(intCodes, 0, 0, {
        computePaddleMove(paddlePos, ballPos, prevBallPos)
    })

    val outputCollector = mutableListOf<Long>()

    while (true) {
        currentState = runProgram(currentState)
        if (currentState.isFinished) {
            break
        }

        outputCollector.add(currentState.output!!)
        if (outputCollector.size == 3) {
            val (x, y, value) = outputCollector
            if (x == -1L && y == 0L) {
                score = value
            } else {
                when (value) {
                    PADDLE -> paddlePos = x to y
                    BALL -> {
                        prevBallPos = ballPos
                        ballPos = x to y
                    }
                }
            }
            outputCollector.clear()
        }
    }

    return score
}
