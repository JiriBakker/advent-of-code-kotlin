import java.util.ArrayDeque

fun day19a(input: String): Long {
    val intCodes = parseIntCodes(input)

    return sequence {
        for (y in 0L until 50) {
            for (x in 0L until 50) {
                val queue = ArrayDeque<Long>(listOf(x, y))
                yield(generateProgramOutput(intCodes.toMutableMap()) { queue.poll() }.first())
            }
        }
    }
        .sum()
}

fun day19b(input: String, targetSize: Long = 100L): Long {
    val intCodes = parseIntCodes(input)

    val width = targetSize - 1

    var x = 0L
    var y = 0L
    while (true) {
        do {
            val queue = ArrayDeque<Long>(listOf(x, y + width))
            val output = generateProgramOutput(intCodes.toMutableMap()) { queue.poll() }.first()
            x++
        } while (output == 0L)

        val xOffset = x - 1
        val queue = ArrayDeque<Long>(listOf(xOffset + width, y))
        val output = generateProgramOutput(intCodes.toMutableMap()) { queue.poll() }.first()
        if (output == 1L) {
            return xOffset * 10000 + y
        }

        y++
    }
}