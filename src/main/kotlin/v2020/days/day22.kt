package v2020.days.day22

import util.splitByDoubleNewLine

private fun parseInput(input: List<String>): Pair<Player, Player> {
    val (player1Lines, player2Lines) = input.splitByDoubleNewLine()

    val player1Cards = player1Lines.drop(1).map(String::toInt).let { Player(1, it) }
    val player2Cards = player2Lines.drop(1).map(String::toInt).let { Player(2, it) }

    return player1Cards to player2Cards
}

private class Player(val nr: Int, cards: Collection<Int>) {
    private val cards = ArrayDeque(cards)

    val size get() = cards.size

    fun hasNoCards() = cards.isEmpty()

    fun popCard() = cards.removeFirst()

    fun addCards(card1: Int, card2: Int) {
        cards.addLast(card1)
        cards.addLast(card2)
    }

    fun computeScore(): Long =
        cards.foldIndexed(0L) { index, acc, it ->  acc + (cards.size - index) * it }

    fun copy(nrOfCards: Int): Player = Player(nr, cards.take(nrOfCards))

    val hash get() = cards.joinToString("_")
}

fun day22a(input: List<String>): Long {
    val (player1, player2) = parseInput(input)

    while (true) {
        if (player1.hasNoCards()) {
            return player2.computeScore()
        } else if (player2.hasNoCards()) {
            return player1.computeScore()
        }

        val player1Card = player1.popCard()
        val player2Card = player2.popCard()

        if (player1Card > player2Card) {
            player1.addCards(player1Card, player2Card)
        } else {
            player2.addCards(player2Card, player1Card)
        }
    }
}

private fun playGame(player1: Player, player2: Player): Player {
    val seen = mutableSetOf<String>()
    fun seenStateBefore(): Boolean {
        val hash = player1.hash + "___" + player2.hash
        return !seen.add(hash)
    }

    while (true) {
        if (seenStateBefore()) {
            return player1
        }

        if (player1.hasNoCards()) {
            return player2
        } else if (player2.hasNoCards()) {
            return player1
        }

        val player1Card = player1.popCard()
        val player2Card = player2.popCard()

        if (player1Card > player1.size || player2Card > player2.size) {
            if (player1Card > player2Card) {
                player1.addCards(player1Card, player2Card)
                continue
            } else {
                player2.addCards(player2Card, player1Card)
                continue
            }
        }

        val nextPlayer1Cards = player1.copy(player1Card)
        val nextPlayer2Cards = player2.copy(player2Card)

        val winner = playGame(nextPlayer1Cards, nextPlayer2Cards)
        when (winner.nr) {
            1 -> player1.addCards(player1Card, player2Card)
            2 -> player2.addCards(player2Card, player1Card)
        }
    }
}

fun day22b(input: List<String>): Long {
    val (player1, player2) = parseInput(input)

    val winner = playGame(player1, player2)

    return winner.computeScore()
}

