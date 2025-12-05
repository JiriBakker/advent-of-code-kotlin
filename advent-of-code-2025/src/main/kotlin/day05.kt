import util.sumOfLong
import kotlin.collections.map
import kotlin.math.max

fun day05a(input: List<String>): Int {
    val ranges = input.takeWhile { it.isNotBlank() }.map { it.split("-") }.map { LongRange(it[0].toLong(), it[1].toLong()) }
    val ingredients = input.dropWhile { it.isNotBlank() }.drop(1).map(String::toLong)

    return ingredients.count { ingredient ->
        ranges.any { range ->
            ingredient in range
        }
    }
}

fun day05b(input: List<String>): Long {
    var ranges = input.takeWhile { it.isNotBlank() }.map { it.split("-") }.map { LongRange(it[0].toLong(), it[1].toLong()) }.sortedWith(compareBy({ it.first }, { it.last }))

    do {
        var madeChanges = false
        val nextRanges = mutableListOf<LongRange>()

        var curRange = ranges.first()

        var i = 0
        while (i < ranges.size - 1) {
            if (curRange.last >= ranges[i + 1].first - 1) {
                curRange = LongRange(curRange.first, max(ranges[i + 1].last, curRange.last))
                madeChanges = true
            } else {
                nextRanges.add(curRange)
                curRange = ranges[i + 1]
            }
            i++
        }
        nextRanges.add(curRange)
        ranges = nextRanges
    } while (madeChanges)

    return ranges.sumOfLong { it.last - it.first + 1 }
}