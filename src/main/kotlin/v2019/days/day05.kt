package v2019.days.day05

import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram

fun day05a(input: String): Int {
    val intCodes = parseIntCodes(input)

    val (_, output) = runProgram(intCodes, listOf(1))

    return output.last()
}

fun day05b(input: String, inputValue: Int = 5): Int {
    val intCodes = parseIntCodes(input)

    val (_, output) = runProgram(intCodes, listOf(inputValue))

    return output.last()
}