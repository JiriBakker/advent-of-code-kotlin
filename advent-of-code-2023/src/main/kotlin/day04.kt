import util.pow
import util.sumOfLong
import kotlin.Int

fun day04a(input: List<String>) =
    countNumberOfMatchesPerCard(input)
        .sumOfLong { nrOfMatches -> 2L.pow(nrOfMatches - 1) }

fun day04b(input: List<String>): Long {
    val nrOfMatchesPerCard = countNumberOfMatchesPerCard(input)

    val cache = mutableMapOf<Int, Long>()
    fun countChildCopies(cardNr: Int): Long {
        val nrOfMatches = nrOfMatchesPerCard[cardNr]

        if (nrOfMatches == 0L) return 1L

        return cache.getOrPut(cardNr) {
            (cardNr + 1 .. cardNr + nrOfMatches.toInt())
                .sumOfLong(::countChildCopies) + 1
        }
    }

    return nrOfMatchesPerCard.indices.sumOfLong(::countChildCopies)
}

private fun countNumberOfMatchesPerCard(input: List<String>) =
    input.map { line ->
        val winningNumbers = parseWinningNumbers(line)
        val scratchCard = parseScratchCard(line)

        winningNumbers.count { scratchCard.contains(it) }.toLong()
    }

private fun parseWinningNumbers(line: String) =
    line.split(": ")[1].split(" | ").first().split(" ").mapNotNull(String::toIntOrNull)

private fun parseScratchCard(line: String) =
    line.split(" | ")[1].split(" ").mapNotNull(String::toIntOrNull)