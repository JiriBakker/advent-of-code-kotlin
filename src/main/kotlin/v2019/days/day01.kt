package v2019.days

import v2019.sumByLong
import kotlin.math.max

private fun computeRequiredFuel(mass: Long): Long {
    return max(mass / 3 - 2, 0)
}

fun day01a(input: List<String>): Long {
    return input
        .sumByLong { mass -> computeRequiredFuel(mass.toLong()) }
}

fun day01b(input: List<String>): Long {
   return input
        .sumByLong { mass ->
            generateSequence(
                computeRequiredFuel(mass.toLong()),
                { fuelMass -> if (fuelMass == 0L) null else computeRequiredFuel(fuelMass) }
            ).sum()
        }
}
