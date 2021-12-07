package v2021

import util.sumOfLong

private fun List<String>.parseLanternFish() =
    first().split(",").map { it.toInt() }

private fun List<Int>.countNrs(): Map<Int, Long> =
    groupBy { it }.mapValues { it.value.size.toLong() }

private fun MutableMap<Int, Long>.shiftLeft() =
    (0 .. 7).forEach { this[it] = this.getOrDefault(it + 1, 0) }

private fun countFish(initial: List<Int>, days: Int): Long {
    val buckets = initial.countNrs().toMutableMap()

    repeat(days) {
        val created = buckets.getOrDefault(0, 0)
        buckets.shiftLeft()
        buckets[6] = buckets.getOrDefault(6, 0) + created
        buckets[8] = created
    }

    return buckets.sumOfLong { it.value }
}

fun day06a(input: List<String>) =
    input
        .parseLanternFish()
        .let { countFish(it, 80) }

fun day06b(input: List<String>) =
     input
        .parseLanternFish()
        .let { countFish(it, 256) }