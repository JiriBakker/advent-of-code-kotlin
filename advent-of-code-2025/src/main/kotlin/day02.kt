import util.allEqual
import util.sumOfLong

fun day02a(input: String): Long {
    val ranges = parseInput(input)
    return computeInvalidSum(ranges)
}

fun day02b(input: String): Long {
    val ranges = parseInput(input)
    return computeInvalidSum(ranges, maxRepeats = 7)
}

private fun computeInvalidSum(ranges: List<List<String>>, maxRepeats: Int = 2): Long {
    return ranges.sumOfLong { range ->
        val (start, end) = range.map(String::toLong)

        (start .. end).sumOfLong { nr ->
            val nrString = nr.toString()

            for (i in maxRepeats downTo 2) {
                if (nrString.length % i == 0) {
                    val chunks = nrString.chunked(nrString.length / i)
                    if (chunks.allEqual()) {
                        return@sumOfLong nrString.toLong()
                    }
                }
            }

            0L
        }
    }
}

private fun parseInput(input: String) =
    input
        .split(",")
        .map { it.split("-") }
