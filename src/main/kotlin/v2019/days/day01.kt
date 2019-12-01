package v2019.days

import kotlin.math.max

private fun computeRequiredFuel(mass: Long): Long {
    return max(mass / 3 - 2, 0)
}

fun day01a(input: List<String>): Long {
    return input
        .map { computeRequiredFuel(it.toLong()) }
        .sum()
}

fun day01b(input: List<String>): Long {
   return input
        .map {
            var requiredFuel = computeRequiredFuel(it.toLong())
            var totalFuel = requiredFuel
            while (computeRequiredFuel(requiredFuel) > 0) {
                totalFuel += computeRequiredFuel(requiredFuel)
                requiredFuel = computeRequiredFuel(requiredFuel)
            }
            totalFuel
        }
        .sum()
}
