package v2019.days.day02

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram

fun day02a(input: String, overrides: List<Pair<Long, Long>> = listOf()): Long {
    val intCodes = parseIntCodes(input).toMutableMap()
    overrides.forEach { intCodes[it.first] = it.second }

    val state = runProgram(ProgramState(intCodes))

    return state.intCodes[0] ?: error("No value found at first memory address")
}

private class SearchRange(var min: Long, var max: Long) {
    fun median(): Long {
        return ((max - min) / 2) + min
    }
    fun consolidateMin() {
        min = median() + 1
    }
    fun consolidateMax() {
        max = median() - 1
    }
}

fun day02b(input: String): Long {
    val target = 19690720L

    val initialIntCodes = parseIntCodes(input)

    val range1 = SearchRange(0, 99)
    val range2 = SearchRange(0, 99)

    fun findOptimal(range: SearchRange) {
        while (range.max > range.min) {
            val intCodes = initialIntCodes.toMutableMap()
            intCodes[1] = range1.median()
            intCodes[2] = range2.median()

            val state = runProgram(ProgramState(intCodes.toMap()))
            val result = state.intCodes

            when {
                result[0]!! == target -> return
                result[0]!! > target  -> range.consolidateMax()
                else                  -> range.consolidateMin()
            }
        }
    }

    findOptimal(range1)
    findOptimal(range2)

    return 100L * range1.median() + range2.median()
}