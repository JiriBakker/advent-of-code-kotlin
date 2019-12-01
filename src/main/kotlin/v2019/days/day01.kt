package v2019.days.day01

import v2019.sumByLong
import kotlin.math.max

private fun computeRequiredFuel(mass: Long): Long {
    return max(mass / 3 - 2, 0)
}

fun day01a(input: List<String>): Long {
    return input
        .map { it.toLong() }
        .sumByLong(::computeRequiredFuel)
}

fun day01b(input: List<String>): Long {
    return input
        .map { it.toLong() }
        .sumByLong { mass ->
            generateSequence(computeRequiredFuel(mass), ::computeRequiredFuel)
                .takeWhile { it > 0L }
                .sum()
        }
}
