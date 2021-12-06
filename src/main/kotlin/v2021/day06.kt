package v2021

import util.sumOfLong

private fun List<String>.parseLanternFish() =
    first().split(",").map { it.toInt() }

private fun List<Int>.countNrs(): Map<Int, Long> =
    groupBy { it }.mapValues { it.value.size.toLong() }

private fun MutableMap<Int, Long>.shiftLeft() =
    (0 .. 7).forEach { this[it] = this.getOrDefault(it + 1, 0) }


private fun countFish(initialTimeLeftPerFish: List<Int>, days: Int): Long {
    val fishesPerTimeLeft = initialTimeLeftPerFish.countNrs().toMutableMap()

    repeat(days) {
        val created = fishesPerTimeLeft.getOrDefault(0, 0)
        fishesPerTimeLeft.shiftLeft()
        fishesPerTimeLeft[6] = fishesPerTimeLeft.getOrDefault(6, 0) + created
        fishesPerTimeLeft[8] = created
    }

    return fishesPerTimeLeft.sumOfLong { it.value }
}

fun day06a(input: List<String>) =
    input
        .parseLanternFish()
        .let { countFish(it, 80) }

fun day06b(input: List<String>) =
     input
        .parseLanternFish()
        .let { countFish(it, 256) }