private fun parseLayers(input: String, width: Int, height: Int): List<String> {
    return input.chunked(width * height)
}

private const val BLACK = '0'
private const val WHITE = '1'
private const val TRANSPARENT = '2'

private fun List<Char>.pixelsToString(width: Int): String {
    return this
        .joinToString("") { if (it == WHITE) "█" else " " }
        .chunked(width)
        .joinToString("\n")
}

fun day08a(input: String, width: Int = 25, height: Int = 6): Int {
    val layers = parseLayers(input, width, height)

    return layers
        .map { layer -> layer.groupingBy { digit -> digit }.eachCount() }
        .minByOrNull { counts -> counts[BLACK] ?: Int.MAX_VALUE }!!
        .let { counts -> (counts[WHITE] ?: 0) * (counts[TRANSPARENT] ?: 0) }

}

fun day08b(input: String, width: Int = 25, height: Int = 6): String {
    val layers = parseLayers(input, width, height)

    return IntRange(0, width * height - 1)
        .map { index -> layers.first { it[index] != TRANSPARENT }[index] }
        .pixelsToString(width)
}