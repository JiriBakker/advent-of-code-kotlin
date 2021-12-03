package v2021

import util.toDecimal
import util.transpose

private fun List<String>.countOnes() =
    transpose()
        .map { column -> column.count { it == '1' } }

fun day03a(input: List<String>): Long {
    val oneCounts = input.countOnes()

    fun getRate(comparator: (Int, Int) -> Boolean) =
        oneCounts
            .joinToString("") {
                if (comparator.invoke(it, input.size / 2)) "1"
                else "0"
            }
            .toDecimal()

    val gammaRate = getRate { a, b -> a >= b }
    val epsilonRate = getRate { a, b -> a <= b }

    return gammaRate * epsilonRate
}

private fun getMostCommon(lines: List<String>) =
    lines
        .countOnes()
        .map {
            if (it >= lines.size - it) '1'
            else '0'
        }
        .toList()

fun day03b(input: List<String>): Long {
    fun findRating(comparator: (Char, Char) -> Boolean): Long {
        var remaining = input
        var index = 0
        while (remaining.size > 1) {
            val mostCommon = getMostCommon(remaining)
            remaining = remaining.filter {
                comparator.invoke(mostCommon[index], it[index])
            }
            index++
        }
        return remaining.first().toDecimal()
    }

    val oxygenRating = findRating { a, b -> a == b }
    val co2Rating = findRating { a, b -> a != b }

    return oxygenRating * co2Rating
}