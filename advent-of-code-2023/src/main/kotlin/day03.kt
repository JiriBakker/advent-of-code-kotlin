import util.sumOfLong
import kotlin.Int

fun day03a(input: List<String>): Long {
    val numberPositions = findNumbers(input).toList()
    val symbolPositions = findSymbols(input).toList()

    return numberPositions
        .filter { number ->
            symbolPositions.any { symbol -> areNeighbours(symbol, number) }
        }
        .sumOfLong { (x1, x2, y): Triple<Int, Int, Int> ->
            input[y].substring(x1, x2).toLong()
        }
}

fun day03b(input: List<String>): Long {
    val numberPositions = findNumbers(input).toList()
    val symbolPositions = findSymbols(input, whitelist = setOf('*')).toList()

    return symbolPositions.sumOfLong { symbol ->
        val neighbouringNumbers = numberPositions.filter { number -> areNeighbours(symbol, number) }

        if (neighbouringNumbers.size == 2) {
            neighbouringNumbers
                .map { (x1, x2, y) -> input[y].substring(x1, x2).toLong() }
                .reduce(Long::times)
        } else {
            0L
        }
    }
}

private fun areNeighbours(symbol: Pair<Int, Int>, number: Triple<Int, Int, Int>): Boolean {
    val (x1, x2, y) = number
    val (symbolX, symbolY) = symbol
    return symbolX in (x1 - 1..x2) && symbolY in (y - 1..y + 1)
}

private fun findNumbers(input: List<String>): Sequence<Triple<Int, Int, Int>> {
    return sequence {
        var curNumberStartIndex: Int? = null
        for (y in input.indices) {
            for (x in input[y].indices) {
                val char = input[y][x]
                if (char.isDigit()) {
                    if (curNumberStartIndex == null) {
                        curNumberStartIndex = x
                    }
                }
                else if (curNumberStartIndex != null) {
                    yield(Triple(curNumberStartIndex, x, y))
                    curNumberStartIndex = null
                }
            }
            if (curNumberStartIndex != null) {
                yield(Triple(curNumberStartIndex, input[y].length, y))
                curNumberStartIndex = null
            }
        }
    }
}

private fun findSymbols(input: List<String>, whitelist: Set<Char> = emptySet()): Sequence<Pair<Int, Int>> {
    return sequence {
        for (y in input.indices) {
            for (x in input[y].indices) {
                val char = input[y][x]
                if (
                    (whitelist.isNotEmpty() && char in whitelist) ||
                    (char != '.' && !char.isDigit())
                ) {
                    yield(Pair(x, y))
                }
            }
        }
    }
}