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
    input.dropWhile { it.isNotBlank() }.drop(1).map(String::toLong)

private fun parseRanges(input: List<String>) =
    input.takeWhile { it.isNotBlank() }.map { it.split("-") }.map { LongRange(it[0].toLong(), it[1].toLong()) }

private fun List<LongRange>.sortRanges() =
    sortedWith(compareBy({ it.first }, { it.last }))

private fun List<LongRange>.consolidate(): List<LongRange> {
    var ranges = this

    while (true) {
        val nextRanges = mutableListOf<LongRange>()
        var curRange = ranges.first()

        (0 until ranges.size - 1)
            .forEach { i ->
                if (curRange.last >= ranges[i + 1].first - 1) {
                    curRange = LongRange(curRange.first, max(ranges[i + 1].last, curRange.last))
                } else {
                    nextRanges.add(curRange)
                    curRange = ranges[i + 1]
                }
            }

        nextRanges.add(curRange)

        if (ranges.size == nextRanges.size) break

        ranges = nextRanges
    }

    return ranges
}

private fun List<LongRange>.countIngredients() =
    sumOfLong { it.last - it.first + 1 }