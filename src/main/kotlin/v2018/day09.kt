package v2018

private class Marble(val number: Int) {
    var prev: Marble = this
        private set

    var next: Marble = this
        private set

    fun disconnectSelf() {
        this.prev.next = this.next
        this.next.prev = this.prev
    }

    fun insertAfter(other: Marble) {
        this.prev = other
        this.next = other.next
        other.next.prev = this
        other.next = this
    }
}

private fun parse(inputLine: String): Pair<Int, Int> {
    val inputParts = inputLine.split(" ")
    val nrOfPlayers = inputParts[0].toInt()
    val nrOfMarbles = inputParts[6].toInt()
    return Pair(nrOfPlayers, nrOfMarbles)
}

private fun findMaxAfterPlacingMarbles(nrOfPlayers: Int, nrOfMarbles: Int): Long {
    val playerScores = Array(nrOfPlayers) { 0L }

    var maxScore = 0L

    fun registerScore(playerNr: Int, score: Long) {
        val newScore = playerScores[playerNr] + score
        if (newScore > maxScore) {
            maxScore = newScore
        }
        playerScores[playerNr] = newScore
    }

    var currentMarble = Marble(0)
    for (marbleNr in 1..nrOfMarbles) {
        if (marbleNr % 23 == 0) {
            repeat(6) { currentMarble = currentMarble.prev }

            val marbleToRemove = currentMarble.prev
            marbleToRemove.disconnectSelf()

            val playerNr = (marbleNr - 1) % nrOfPlayers
            val score = (marbleNr + marbleToRemove.number).toLong()
            registerScore(playerNr, score)
        } else {
            val newMarble = Marble(marbleNr)
            newMarble.insertAfter(currentMarble.next)
            currentMarble = newMarble
        }
    }

    return maxScore
}

fun day09a(inputLine: String): Long {
    val (nrOfPlayers, nrOfMarbles) = parse(inputLine)
    return findMaxAfterPlacingMarbles(nrOfPlayers, nrOfMarbles)
}

fun day09b(inputLine: String): Long {
    val (nrOfPlayers, nrOfMarbles) = parse(inputLine)
    return findMaxAfterPlacingMarbles(nrOfPlayers, nrOfMarbles * 100)
}
