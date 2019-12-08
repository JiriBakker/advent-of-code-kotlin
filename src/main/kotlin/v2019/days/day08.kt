package v2019.days.day08

private fun parseLayers(input: String, width: Int, height: Int): List<List<Int>> {
    return input.map(Character::getNumericValue).chunked(width * height)
}

private fun List<Int>.pixelsToString(width: Int): String {
    return this
        .joinToString("") { if (it == 1) "█" else " " }
        .chunked(width)
        .joinToString("\n")
}

fun day08a(input: String, width: Int = 25, height: Int = 6): Int {
    val layers = parseLayers(input, width, height)

    val layerWithLeastZeroes =
        layers
            .map { it.groupingBy { digit -> digit }.eachCount() }
            .minBy { counts -> counts[0] ?: Int.MAX_VALUE }!!

    return (layerWithLeastZeroes[1] ?: 0) * (layerWithLeastZeroes[2] ?: 0)
}

fun day08b(input: String, width: Int = 25, height: Int = 6): String {
    val layers = parseLayers(input, width, height)

    return IntRange(0, width * height - 1)
        .map { index -> layers.first { it[index] != 2 }[index] }
        .pixelsToString(width)
}