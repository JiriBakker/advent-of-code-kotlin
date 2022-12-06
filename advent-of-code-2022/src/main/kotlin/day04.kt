fun day04a(input: List<String>) =
    input.count { line ->
        val (firstRange, secondRange) = line.parseRanges()

        firstRange.fullyOverlaps(secondRange)
            || secondRange.fullyOverlaps(firstRange)
    }


fun day04b(input: List<String>) =
    input.count { line ->
        val (firstRange, secondRange) = line.parseRanges()

        firstRange.partiallyOverlaps(secondRange)
            || secondRange.partiallyOverlaps(firstRange)
    }

private fun String.parseRanges(): Pair<IntRange, IntRange> {
    val (firstRange, secondRange) =
        this.split(",").map(String::toRange)

    return firstRange to secondRange
}

private fun String.toRange() =
    this.split("-")
        .map(String::toInt)
        .let { it[0] .. it[1] }

private fun IntRange.fullyOverlaps(other: IntRange) =
    first <= other.first && last >= other.last

private fun IntRange.partiallyOverlaps(other: IntRange) =
    other.first in this || other.last in this
