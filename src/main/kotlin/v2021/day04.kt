package v2021

import v2021.Day04.parse

private typealias BingoCard = List<List<Int>>

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

        return nrsToDraw to bingoCards
    }
}

private fun List<BingoCard>.getWinningLines(): Map<Set<Int>, BingoCard> {
    return this.flatMap { bingoCard ->
        buildList {
            addAll(bingoCard.map { row -> row })
            addAll((0 .. 4).map { column -> bingoCard.map { row -> row[column] } })
        }
            .map { it.toSet() to bingoCard }
    }.toMap()
}

private fun drawNrs(
    nrsToDraw: List<Int>,
    winningLines: Map<Set<Int>, BingoCard>,
    bingoCardCompletedCallback: (BingoCard, List<Int>) -> Int?
): Int {
    val hitsPerLine = winningLines.keys.associateWith { 0 }.toMutableMap()
    val nrsDrawn = mutableListOf<Int>()

    nrsToDraw.forEach { nr ->
        nrsDrawn.add(nr)

        winningLines
            .filter { it.key.contains(nr) } // Only lines that have the drawn nr
            .forEach { (line, bingoCard) ->
                hitsPerLine[line] = hitsPerLine[line]!! + line.count { it == nr }
                if (hitsPerLine[line] == 5) { // Line completed
                    bingoCardCompletedCallback
                        .invoke(bingoCard, nrsDrawn)
                        ?.let { return it } // If callback returns something, answer is found, so return it
                }
            }
    }

    throw Error("No winning bingo card")
}

private fun computeFinalScore(completedBingoCard: BingoCard, nrsDrawn: List<Int>) =
    completedBingoCard
        .sumOf { row ->
            row
                .filter { nr -> !nrsDrawn.contains(nr) } // TODO List.contains not super efficient
                .sum()
        } * nrsDrawn.last()

fun day04a(input: List<String>): Int {
    val (nrsToDraw, bingoCards) = input.parse()
    val winningLines = bingoCards.getWinningLines()

    return drawNrs(nrsToDraw, winningLines) { completedBingoCard, nrsDrawn ->
        computeFinalScore(completedBingoCard, nrsDrawn)
    }
}

fun day04b(input: List<String>): Int {
    val (nrsToDraw, bingoCards) = input.parse()
    val winningLines = bingoCards.getWinningLines()

    val bingoCardsCompleted = mutableSetOf<List<List<Int>>>()

    return drawNrs(nrsToDraw, winningLines) { completedBingoCard, nrsDrawn ->
        bingoCardsCompleted.add(completedBingoCard)
        if (bingoCardsCompleted.count() == bingoCards.count()) { // All bingo cards completed
            computeFinalScore(completedBingoCard, nrsDrawn)
        } else null
    }
}