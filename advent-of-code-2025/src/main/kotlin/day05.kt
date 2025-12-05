import util.sumOfLong
import kotlin.collections.map
import kotlin.math.max

fun day05a(input: List<String>): Int {
    val ranges = parseRanges(input)
    val ingredients = parseIngredients(input)

    return ingredients.count { ingredient ->
        ranges.any { range ->
            ingredient in range
        }
    }
}

fun day05b(input: List<String>): Long {
    val ranges = parseRanges(input).sortRanges()
    return ranges.consolidate().countIngredients()
}

private fun parseIngredients(input: List<String>) =
    input
        .dropWhile { it.isNotBlank() }
        .drop(1)
        .map(String::toLong)

private fun parseRanges(input: List<String>) =
    input
        .takeWhile { it.isNotBlank() }
        .map { it.split("-") }
        .map { LongRange(it[0].toLong(), it[1].toLong()) }

private fun List<LongRange>.sortRanges() =
    // Sort by start of range, and if equal, then sort by end
    sortedWith(compareBy({ it.first }, { it.last }))

private fun List<LongRange>.consolidate(): List<LongRange> {
    // Combine ranges that (partially) overlap to end up with only non-overlapping ranges
    var ranges = this

    while (true) {
        val nextRanges = mutableListOf<LongRange>()
        var curRange = ranges.first()

        (0 until ranges.size - 1)
            .forEach { i ->
                if (curRange.last >= ranges[i + 1].first - 1) {
                    // Ranges overlap, so combine them
                    curRange = LongRange(
                        curRange.first,
                        max(ranges[i + 1].last, curRange.last) // Consider cases where first range fully overlaps second range
                    )
                } else {
                    // Gap between ranges, so finalize previous, and continue with next range
                    nextRanges.add(curRange)
                    curRange = ranges[i + 1]
                }
            }

        nextRanges.add(curRange) // Don't forget to store last range

        if (ranges.size == nextRanges.size) break // No changes this loop, so we can stop iterating

        ranges = nextRanges
    }

    return ranges
}

private fun List<LongRange>.countIngredients() =
    sumOfLong { it.last - it.first + 1 } // Calculate length of (inclusive) ranges, and sum them together