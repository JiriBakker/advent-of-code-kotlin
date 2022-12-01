package v2021

private fun List<String>.parseInputImage() =
    (0 until size).associateWith { y ->
        (0 until this[y].length).associateWith { x ->
            this[y][x] == '#'
        }
    }.let { Image(it, false) }

private class Image(pixels: Map<Int, Map<Int, Boolean>>, val isInfinityLit: Boolean) {
    private var pixels: Map<Int, Map<Int, Boolean>>

    val minX: Int
    val maxX: Int
    val minY: Int
    val maxY: Int

    init {
        this.pixels = pixels
        minX = pixels.values.minOf { line -> line.keys.minOrNull() ?: 0 }
        maxX = pixels.values.maxOf { line -> line.keys.maxOrNull() ?: 0 }
        minY = pixels.keys.minOrNull() ?: 0
        maxY = pixels.keys.maxOrNull() ?: 0
    }

    fun isLit(x: Int, y: Int) =
        if (x < minX || x > maxX || y < minY || y > maxY) isInfinityLit
        else pixels[y]!![x]!!

    fun countLit() =
        pixels.values.sumOf { line -> line.count { it.value } }
}

private fun Image.print() {
    for (y in minY .. maxY) {
        for (x in minX .. maxX) {
            if (isLit(x, y)) print('#')
            else print('.')
        }
        println()
    }
    println()
}

private fun Image.evolve(algorithm: List<Boolean>, iterations: Int): Image {
    var inputImage = this

    val hasAlternatingInfinity = algorithm[0]

    fun isLitInOutput(x: Int, y: Int): Boolean {
        val index =
            listOf(
                if (inputImage.isLit(x - 1, y - 1)) '1' else '0',
                if (inputImage.isLit(x, y - 1)) '1' else '0',
                if (inputImage.isLit(x + 1, y - 1)) '1' else '0',
                if (inputImage.isLit(x - 1, y)) '1' else '0',
                if (inputImage.isLit(x, y)) '1' else '0',
                if (inputImage.isLit(x + 1, y)) '1' else '0',
                if (inputImage.isLit(x - 1, y + 1)) '1' else '0',
                if (inputImage.isLit(x, y + 1)) '1' else '0',
                if (inputImage.isLit(x + 1, y + 1)) '1' else '0'
            )
                .joinToString("")
                .toInt(2)

        return algorithm[index]
    }

    repeat(iterations) {
        val minX = inputImage.minX - 1
        val maxX = inputImage.maxX + 1
        val minY = inputImage.minY - 1
        val maxY = inputImage.maxY + 1

        val outputPixels =
            (minY .. maxY).associateWith { y ->
                (minX .. maxX).associateWith { x ->
                    isLitInOutput(x, y)
                }
            }

        inputImage =
            Image(
                outputPixels,
                !inputImage.isInfinityLit && hasAlternatingInfinity
            )
    }

    return inputImage
}

fun day20a(input: List<String>): Int {
    val algorithm = input.first().map { it == '#' }
    val inputImage = input.drop(2).parseInputImage()

    return inputImage
        .evolve(algorithm, iterations = 2)
        .countLit()
}

fun day20b(input: List<String>): Int {
    val algorithm = input.first().map { it == '#' }
    val inputImage = input.drop(2).parseInputImage()

    return inputImage
        .evolve(algorithm, iterations = 50)
        .countLit()
}