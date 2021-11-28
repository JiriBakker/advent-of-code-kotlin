package v2019

import util.BinarySearchRange
import util.doBinarySearch

fun day02a(input: String, overrides: List<Pair<Long, Long>> = listOf()): Long {
    val intCodes = parseIntCodes(input).toMutableMap()
    overrides.forEach { intCodes[it.first] = it.second }

    generateProgramOutput(intCodes).toList()

    return intCodes[0] ?: error("No value found at first memory address")
}

fun day02b(input: String): Long {
    val target = 19690720L

    val initialIntCodes = parseIntCodes(input)

    val range1 = BinarySearchRange(0, 99)
    val range2 = BinarySearchRange(0, 99)

    fun runProgram(): Long {
        val intCodes = initialIntCodes.toMutableMap()
        intCodes[1] = range1.median()
        intCodes[2] = range2.median()

        generateProgramOutput(intCodes).toList()

        return intCodes[0]!!
    }

    doBinarySearch(range1, target) { runProgram() }
    doBinarySearch(range2, target) { runProgram() }

    return 100L * range1.median() + range2.median()
}