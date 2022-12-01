package v2017

private fun parseLayers(input: List<String>): Map<Int, Int> {
    return input.map { it.split(": ").map(String::toInt).let { (depth, range) -> depth to range } }.toMap()
}

fun day13a(input: List<String>): Int {
    val layers = parseLayers(input)
    fun scannerIndexAtLayer(depth: Int): Int = layers[depth].let { range -> depth % ((range!! - 1) * 2) }

    return layers
        .filter { scannerIndexAtLayer(it.key) == 0 }
        .map { (depth, layer) -> depth * layer }
        .sum()
}

fun day13b(input: List<String>): Int {
    val layers = parseLayers(input)
    fun scannerIndexAtLayer(depth: Int, delay: Int): Int = layers[depth].let { range -> (depth + delay) % ((range!! - 1) * 2) }

    return (0 .. Int.MAX_VALUE).first { delay ->
        layers.none { scannerIndexAtLayer(it.key, delay) == 0 }
    }
}
