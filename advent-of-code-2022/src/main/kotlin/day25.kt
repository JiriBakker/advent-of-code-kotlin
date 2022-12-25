import util.pow

fun day25a(input: List<String>) =
    input
        .sumOf { it.toDecimal() }
        .toSnafu()

fun String.toDecimal() =
    this.toCharArray()
        .reversed()
        .withIndex()
        .sumOf { (exp, char) ->
            when (char) {
                '2' -> 2L
                '1' -> 1L
                '-' -> -1L
                '=' -> -2L
                else -> 0L
            } * 5L.pow(exp.toLong())
        }

fun Long.toSnafu(): String {
    var maxExp = 1L

    while (this % 5L.pow(maxExp) != this) { maxExp++ }

    var remainder = this

    val snafu =
        (maxExp downTo 0).map { exp ->
            val powerOf5 = 5L.pow(exp)

            val offset = (0 until exp).sumOf { 2L * 5L.pow(it) }

            val div = (remainder + offset) / powerOf5

            remainder %= powerOf5

            when (div) {
                1L   -> "1"
                2L   -> "2"
                3L   -> "="
                4L   -> "-"
                else -> "0"
            }
        }

    return snafu.joinToString("").trimStart('0')
}

