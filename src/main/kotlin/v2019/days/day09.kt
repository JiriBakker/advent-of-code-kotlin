package v2019.days.day09

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes

fun day09a(input: String): Long {
    val intCodes = parseIntCodes(input)

    return generateProgramOutput(intCodes) { 1L }
        .first { it != 0L }
}

fun day09b(input: String): Long {
    val intCodes = parseIntCodes(input)

    return generateProgramOutput(intCodes) { 2L }
        .first { it != 0L }
}