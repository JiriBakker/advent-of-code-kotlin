package v2020.days.day22

import util.splitByDoubleNewLine

private class PlayerCards(cards: Collection<Int>) {
    private val cards = ArrayDeque(cards)

    val size get() = cards.size

    fun hasNoCards() = cards.isEmpty()

    val topCard get() = cards.removeFirst()

    fun grabCards(card1: Int, card2: Int) {
        cards.addLast(card1)
        cards.addLast(card2)
    }

    fun computeScore(): Long =
        cards.foldIndexed(0L) { index, acc, it ->  acc + (cards.size - index) * it }

    fun copy(nrOfCards: Int): PlayerCards = PlayerCards(cards.take(nrOfCards))

    val hash get() = cards.joinToString("_")
}

fun day22a(input: List<String>): Long {
    val (player1, player2) = input.splitByDoubleNewLine()

    val player1Cards = player1.drop(1).map(String::toInt).let { PlayerCards(it) }
    val player2Cards = player2.drop(1).map(String::toInt).let { PlayerCards(it) }

    while (true) {
        if (player1Cards.hasNoCards()) {
            return player2Cards.computeScore()
        } else if (player2Cards.hasNoCards()) {
            return player1Cards.computeScore()
        }

        val player1Card = player1Cards.topCard
        val player2Card = player2Cards.topCard

        if (player1Card > player2Card) {
            player1Cards.grabCards(player1Card, player2Card)
        } else {
            player2Cards.grabCards(player2Card, player1Card)
        }
    }
}

private fun findWinner(player1Cards: PlayerCards, player2Cards: PlayerCards): Pair<Int, PlayerCards> {
    val seen = mutableSetOf<String>()
    fun seenStateBefore(): Boolean {
        val hash = player1Cards.hash + "___" + player2Cards.hash
        return !seen.add(hash)
    }

    while (true) {
        if (seenStateBefore()) {
            return 1 to player1Cards
        }

        if (player1Cards.hasNoCards()) {
            return 2 to player2Cards
        } else if (player2Cards.hasNoCards()) {
            return 1 to player1Cards
        }

        val player1Card = player1Cards.topCard
        val player2Card = player2Cards.topCard

        if (player1Card > player1Cards.size || player2Card > player2Cards.size) {
            if (player1Card > player2Card) {
                player1Cards.grabCards(player1Card, player2Card)
                continue
            } else {
                player2Cards.grabCards(player2Card, player1Card)
                continue
            }
        }

        val nextPlayer1Cards = player1Cards.copy(player1Card)
        val nextPlayer2Cards = player2Cards.copy(player2Card)

        val (winner, _) = findWinner(nextPlayer1Cards, nextPlayer2Cards)
        when (winner) {
            1 -> player1Cards.grabCards(player1Card, player2Card)
            2 -> player2Cards.grabCards(player2Card, player1Card)
        }
    }
}

fun day22b(input: List<String>): Long {
    val (player1, player2) = input.splitByDoubleNewLine()

    val player1Cards = player1.drop(1).map(String::toInt).let { PlayerCards(it) }
    val player2Cards = player2.drop(1).map(String::toInt).let { PlayerCards(it) }

    val (_, winnerCards) = findWinner(player1Cards, player2Cards)

    return winnerCards.computeScore()
}

