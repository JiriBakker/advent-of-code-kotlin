package v2019

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