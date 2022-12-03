import util.sumOfLong
import java.util.ArrayDeque

private fun parsePortRanges(input: List<String>): List<Pair<Long, Long>> {
    return input.map { range -> range.split('-').let { it[0].toLong() to it[1].toLong() } }
}

private fun findOpenPortRanges(closedPortRanges: List<Pair<Long, Long>>, maxPortInclusive: Long): Sequence<Pair<Long, Long>> {
    return sequence {
        val ranges = ArrayDeque(closedPortRanges.sortedBy { it.first })

        var currentPort = 0L

        while (ranges.isNotEmpty()) {
            val range = ranges.pop()

            if (range.first > currentPort) {
                yield(currentPort to range.first - 1)
                currentPort = range.first
            }
            if (range.second >= currentPort) {
                currentPort = range.second + 1
            }
        }

        if (currentPort <= maxPortInclusive) {
            yield(currentPort to maxPortInclusive)
        }
    }
}

fun day20a(input: List<String>, maxPortInclusive: Long = 4294967295L): Long {
    return findOpenPortRanges(parsePortRanges(input), maxPortInclusive)
        .first()
        .first
}

fun day20b(input: List<String>, maxPortInclusive: Long = 4294967295L): Long {
    return findOpenPortRanges(parsePortRanges(input), maxPortInclusive)
        .toList()
        .sumOfLong { it.second - it.first + 1}
}