package v2019.days.day01

import v2019.sumByLong
import kotlin.math.max

private val computeRequiredFuel = { mass: Long -> max(mass / 3 - 2, 0) }

private fun recurseUntilZero(initialValue: Long, computeFunc: (Long) -> Long): Long {
    if (initialValue <= 0L) {
        return 0
    }
    val value = computeFunc(initialValue)
    return value + recurseUntilZero(value, computeFunc)
}

fun day01a(input: List<String>): Long {
    return input
        .map { it.toLong() }
        .sumByLong { mass -> computeRequiredFuel(mass) }
}

fun day01b(input: List<String>): Long {
    return input
        .map { it.toLong() }
        .sumByLong { mass -> recurseUntilZero(mass, computeRequiredFuel) }
}
