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


fun day13b(input: String): Long {
    val intCodes = parseIntCodes(input)
    intCodes[0L] = 2L

    var paddle = Pos(0L, 0L)
    var ball = Pos(0L, 0L)
    var score = 0L

    generateProgramOutput(intCodes) { ball.x.compareTo(paddle.x).toLong() }
        .chunked(3)
        .forEach { (x, y, value) ->
            when {
                x == -1L && y == 0L -> score = value
                value == PADDLE -> paddle = Pos(x, y)
                value == BALL -> ball = Pos(x, y)
            }
        }

    return score
}
