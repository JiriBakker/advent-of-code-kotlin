import util.pow
import util.sumOfLong
import kotlin.Int

fun day04a(input: List<String>): Long {
    return input.sumOfLong { line ->
        val winningNumbers = parseWinningNumbers(line)
        val scratchCard = parseScratchCard(line)

        val nrOfMatches = winningNumbers.count { scratchCard.contains(it) }.toLong()
        2L.pow(nrOfMatches - 1)
    }
}

fun day04b(input: List<String>): Long {
    val nrOfMatchesPerCard = input.map { line ->
        val winningNumbers = parseWinningNumbers(line)
        val scratchCard = parseScratchCard(line)

        winningNumbers.count { scratchCard.contains(it) }.toLong()
    }

    val cache = mutableMapOf<Int, Long>()
    fun countChildCopies(cardNr: Int): Long {
        val nrOfMatches = nrOfMatchesPerCard[cardNr]

        if (nrOfMatches == 0L) return 1L

        return cache.getOrPut(cardNr) {
            (cardNr + 1..cardNr + nrOfMatches)
                .sumOfLong {
                    countChildCopies(it.toInt())
                } + 1
        }
    }

    return nrOfMatchesPerCard.indices.sumOfLong(::countChildCopies)
}

private fun parseWinningNumbers(line: String) =
    line.split(": ")[1].split(" | ").first().split(" ").mapNotNull { if (it.isNotEmpty()) it.toInt() else null }

private fun parseScratchCard(line: String) =
    line.split(" | ")[1].split(" ").mapNotNull { if (it.isNotEmpty()) it.toInt() else null }