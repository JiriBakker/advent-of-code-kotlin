package v2017.days.day03

import kotlin.math.abs

private fun generatePortLocations(): Sequence<Pair<Int, Int>> {
    return sequence {
        yield(0 to 0)

        var squareBase = 3
        var x = squareBase / 2
        var y = squareBase / 2 - 1
        var squareOffset = 0

        while (true) {
            yield(x to y)

            val squareStartPort = (squareBase - 2) * (squareBase - 2) + 1
            val squareSideLength = (squareBase * squareBase - squareStartPort + 1) / 4

            when ((squareOffset + 1) / squareSideLength) {
                0 -> y--
                1 -> x--
                2 -> y++
                3 -> x++
                else -> {
                    squareBase += 2
                    x++
                    squareOffset = -1
                }
            }
            squareOffset++
        }
    }
}

fun day03a(input: String): Int {
    val portToFind = input.toInt()

    val (x, y) = generatePortLocations().drop(portToFind - 1).first()

    return abs(x) + abs(y)
}

fun day03b(input: String): Int {
    val portToFind = input.toInt()

    val ports = mutableMapOf<Pair<Int, Int>, Int>()
    ports[0 to 0] = 1

    return generatePortLocations()
        .drop(1)
        .map { pos ->
            val (x, y) = pos
            val port = listOf(
                x - 1 to y - 1,
                x - 1 to y,
                x - 1 to y + 1,
                x to y - 1,
                x to y + 1,
                x + 1 to y - 1,
                x + 1 to y,
                x + 1 to y + 1
            ).sumOf { ports.getOrDefault(it, 0) }

            ports[pos] = port
            port
        }
        .first { it > portToFind }
}
