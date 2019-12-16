package v2015.days.day08

private fun String.decode(): String =
    this
        .drop(1)
        .dropLast(1)
        .replace("\\\\", "\\")
        .replace("\\\"", "\"")
        .replace("""\\x[0-9a-f]{2}""".toRegex(), " ")

private fun String.encode(): String =
    "\"" +
        this
            .replace("\\", "\\\\")
            .replace("\"", "\\\"") + "\""

fun day08a(input: List<String>): Int {
    val charsInCode = input.sumBy { it.length }
    val charsInMemory = input.map(String::decode).sumBy { it.length }

    return charsInCode - charsInMemory
}

fun day08b(input: List<String>): Int {
    val charsInCode = input.sumBy { it.length }
    val charsInMemory = input.map(String::encode).sumBy { it.length }

    return charsInMemory - charsInCode
}
