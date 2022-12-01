package v2021

import kotlin.math.abs

private fun List<String>.parsePositions() =
    first().split(",").map { it.toInt() }

private fun findMinFuelCost(positions: List<Int>, computeFunc: (Int, Int) -> Int) =
    (0 until positions.maxOrNull()!!)
        .minOf { target ->
            positions.sumOf { pos ->
                computeFunc(pos, target)
            }
        }

fun day07a(input: List<String>): Int {
    val positions = input.parsePositions()

    return findMinFuelCost(positions) { pos, target ->
        abs(target - pos)
    }
}

fun day07b(input: List<String>): Int {
    val positions = input.parsePositions()

    return findMinFuelCost(positions) { pos, target ->
        val distance = abs(target - pos)
        (distance * (distance + 1)) / 2
    }
}