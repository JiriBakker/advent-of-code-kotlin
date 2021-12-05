package v2021

import util.sumOf
import v2021.Day04.parse

private class BingoCard(numbers: List<List<Int>>) {
    private val rows: List<MutableMap<Int, Boolean>>
    private val cols: List<MutableMap<Int, Boolean>>

    init {
        rows = numbers.map { row -> row.associateWith { false }.toMutableMap() }
        cols = numbers[0].mapIndexed { columnIndex, _ ->
            numbers.associate { row -> row[columnIndex] to false }.toMutableMap()
        }
    }

    private fun MutableMap<Int, Boolean>.markSafe(nr: Int) {
        if (containsKey(nr)) {
            this[nr] = true
        }
    }

    fun mark(nr: Int) {
        rows.forEach { row -> row.markSafe(nr) }
        cols.forEach { col -> col.markSafe(nr) }
    }

    private fun Map<Int, Boolean>.allMarked() =
        all { (_, isMarked) -> isMarked }

    val hasBingo get() =
        rows.any { row -> row.allMarked() } || cols.any { col -> col.allMarked() }

    val score get() =
        rows.sumOf { row -> row.sumOf { (nr, isMarked) -> if (isMarked) 0 else nr } }
}

private object Day04 {
    fun List<String>.parse(): Pair<List<Int>, List<BingoCard>> {
        val nrsToDraw = first().split(",").map { it.toInt() }

        val bingoCards =
            this.drop(1) // nrsToDraw line
                .chunked(6) // bingoCard blocks
                .map { bingoCardLines ->
                    bingoCardLines
                        .drop(1) // empty line
                        .map { line ->
                            line
                                .trim()
                                .split(" ")
                                .filter { it.trim() != "" } // Remove multiple spaces
                                .map { it.toInt() }
                        }
                }
                .map { BingoCard(it) }

        return nrsToDraw to bingoCards
    }
}

private fun drawNrs(
    nrsToDraw: List<Int>,
    bingoCards: List<BingoCard>,
    bingoCardCompletedCallback: (BingoCard, Int) -> Int?
): Int {
    nrsToDraw.forEach { nr ->
        bingoCards.forEach { bingoCard ->
            bingoCard.mark(nr)

            if (bingoCard.hasBingo) {
                bingoCardCompletedCallback.invoke(bingoCard, nr)
                    ?.let { return it } // If callback returns something, answer is found, so return it
            }
        }
    }

    throw Error("No winning bingo card")
}

fun day04a(input: List<String>): Int {
    val (nrsToDraw, bingoCards) = input.parse()

    return drawNrs(nrsToDraw, bingoCards) { completedBingoCard, lastNr ->
        completedBingoCard.score * lastNr
    }
}

fun day04b(input: List<String>): Int {
    val (nrsToDraw, bingoCards) = input.parse()

    val bingoCardsCompleted = mutableSetOf<BingoCard>()

    return drawNrs(nrsToDraw, bingoCards) { completedBingoCard, lastNr ->
        bingoCardsCompleted.add(completedBingoCard)
        if (bingoCardsCompleted.count() == bingoCards.count()) { // All bingo cards completed
            completedBingoCard.score * lastNr
        } else null
    }
}