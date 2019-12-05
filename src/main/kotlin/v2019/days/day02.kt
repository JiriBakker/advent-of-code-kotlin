package v2019.days.day02

import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram

fun day02a(input: String, overrides: List<Pair<Int, Int>> = listOf()): Int {
    val intCodes = parseIntCodes(input).toMutableList()
    overrides.forEach { intCodes[it.first] = it.second }

    val (result, _) = runProgram(intCodes)

    return result.first()
}

private class SearchRange(var min: Int, var max: Int) {
    fun median(): Int {
        return ((max - min) / 2) + min
    }
    fun consolidateMin() {
        min = median() + 1
    }
    fun consolidateMax() {
        max = median() - 1
    }
} ""

fun day02b_binarySearch(input: String): Int {
    val target = 19690720

    val initialIntCodes = parseIntCodes(input)

    val range1 = SearchRange(0, 99)
    val range2 = SearchRange(0, 99)

    fun findOptimal(range: SearchRange) {
        while (range.max > range.min) {
            val intCodes = initialIntCodes.toMutableList()
            intCodes[1] = range1.median()
            intCodes[2] = range2.median()

            val (result, _) = runProgram(intCodes.toList())

            when {
                result.first() == target -> return
                result.first() > target  -> range.consolidateMax()
                else                     -> range.consolidateMin()
            }
        }
    }

    findOptimal(range1)
    findOptimal(range2)

    return 100 * range1.median() + range2.median()
}

fun day02b_bruteForce(input: String): Int {
    val initialIntCodes = parseIntCodes(input)

    for (i1 in 0..99) {
        for (i2 in 0..99) {
            val intCodes = initialIntCodes.toMutableList()
            intCodes[1] = i1
            intCodes[2] = i2

            val (result, _) = runProgram(intCodes.toList())
            if (result.first() == 19690720) {
                return 100 * i1 + i2
            }
        }
    }

    throw Exception("No answer found")
}
