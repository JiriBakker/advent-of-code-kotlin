import util.countLetters

fun day07a(input: List<String>): Int {
    val hands = input.map { line -> line.split(" ").let { it[0] to it[1].toInt() } }
        .map { Triple(it.first, it.second, determineHandRating(it.first)) }

    return hands
        .sortedWith(constructHandSorter(valueOfJ = 11))
        .map { it.second }
        .reduceIndexed { index, winnings, handScore -> winnings + handScore * (index + 1) }
}

fun day07b(input: List<String>): Int {
    val hands = input.map { line -> line.split(" ").let { it[0] to it[1].toInt() } }
        .map { Triple(it.first, it.second, determineOptimizedHandRating(it.first)) }

    return hands
        .sortedWith(constructHandSorter(valueOfJ = 1))
        .map { it.second }
        .reduceIndexed { index, winnings, handScore -> winnings + handScore * (index + 1) }
}

private fun constructHandSorter(valueOfJ: Int): Comparator<Triple<String, Int, Int>> {
    return Comparator { hand1, hand2 ->
        val (cards1, _, rating1) = hand1
        val (cards2, _, rating2) = hand2

        if (rating1 == rating2) {
            determineCardsScore(cards1, valueOfJ).compareTo(determineCardsScore(cards2, valueOfJ))
        } else {
            rating1.compareTo(rating2)
        }
    }
}

private fun determineCardsScore(hand: String, valueOfJ: Int = 11): Int {
    val cardScores = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to valueOfJ, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2)

    return (
        cardScores[hand[0]]!! * 100_000_000 +
        cardScores[hand[1]]!! * 1_000_000 +
        cardScores[hand[2]]!! * 10_000 +
        cardScores[hand[3]]!! * 100 +
        cardScores[hand[4]]!!
    )
}

private fun determineHandRating(hand: String): Int {
    val cardCounts = hand.countLetters()

    // 5 of a kind
    if (cardCounts.size == 1) {
        return 6
    }

    // 4 of a kind
    if (cardCounts.any { it.value == 4 }) {
        return 5
    }

    if (cardCounts.any { it.value == 3 }) {
        // full house
        if (cardCounts.any { it.value == 2 }) {
            return 4
        }
        // 3 of a kind
        return 3
    }

    // two pair
    if (cardCounts.count { it.value == 2 } == 2) {
        return 2
    }

    // one pair
    if (cardCounts.any { it.value == 2 }) {
        return 1
    }

    return 0
}

fun determineOptimizedHandRating(hand: String): Int {
    if ('J' !in hand) return determineHandRating(hand)

    val indexOfJoker = hand.indexOfFirst { it == 'J' }

    return listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        .maxOf { replacementCard ->
            val newHand = hand.take(indexOfJoker) + replacementCard + hand.drop(indexOfJoker + 1)
            determineOptimizedHandRating(newHand)
        }
}