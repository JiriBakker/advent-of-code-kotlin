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