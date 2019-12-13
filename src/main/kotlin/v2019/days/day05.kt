package v2019.days.day05

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes

fun day05a(input: String): Long {
    val intCodes = parseIntCodes(input)

    return generateProgramOutput(intCodes) { 1L }
        .first { it != 0L }
}

fun day05b(input: String, inputValue: Long = 5): Long {
    val intCodes = parseIntCodes(input)

    return generateProgramOutput(intCodes) { inputValue }
        .first { it != 0L }
}