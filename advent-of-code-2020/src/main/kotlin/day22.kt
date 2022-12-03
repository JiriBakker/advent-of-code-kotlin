import util.splitByDoubleNewLine

private fun parseInput(input: List<String>): Pair<Player, Player> {
    val (player1Lines, player2Lines) = input.splitByDoubleNewLine()

    val player1Cards = player1Lines.drop(1).map(String::toInt).let { Player(1, it) }
    val player2Cards = player2Lines.drop(1).map(String::toInt).let { Player(2, it) }

    return player1Cards to player2Cards
}

private class Player(val nr: Int, cards: Collection<Int>) {
    private val cards = ArrayDeque(cards)

    val nrOfCards get() = cards.size

    val score get() = cards.foldIndexed(0L) { index, acc, it ->  acc + (cards.size - index) * it }

    val hash get() = cards.joinToString("_")

    fun hasNoCards() = cards.isEmpty()

    fun popCard() = cards.removeFirst()

    fun addCards(card1: Int, card2: Int) {
        cards.addLast(card1)
        cards.addLast(card2)
    }

    fun copy(nrOfCards: Int): Player = Player(nr, cards.take(nrOfCards))
}

fun day22a(input: List<String>): Long {
    val (player1, player2) = parseInput(input)

    while (true) {
        if (player1.hasNoCards()) {
            return player2.score
        } else if (player2.hasNoCards()) {
            return player1.score
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
        val hash = "${player1.score}___${player2.score}"
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

        if (player1Card > player1.nrOfCards || player2Card > player2.nrOfCards) {
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

    return winner.score
}

