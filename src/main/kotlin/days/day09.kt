package days.day09

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

private fun placeMarbles(nrOfPlayers: Int, nrOfMarbles: Int): Array<Long> {
    val playerScores = Array(nrOfPlayers) { 0L }

    var currentMarble = Marble(0)
    for (marbleNr in 1..nrOfMarbles) {
        if (marbleNr % 23 == 0) {
            repeat(7) { currentMarble = currentMarble.prev }

            val currentPlayerNr = (marbleNr - 1) % nrOfPlayers
            playerScores[currentPlayerNr] += (marbleNr + currentMarble.number).toLong()

            currentMarble.disconnectSelf()
            currentMarble = currentMarble.next
        } else {
            currentMarble = currentMarble.next
            val newMarble = Marble(marbleNr)
            newMarble.insertAfter(currentMarble)
            currentMarble = newMarble
        }
    }

    return playerScores
}

private fun parse(inputLine: String): Pair<Int, Int> {
    val inputParts = inputLine.split(" ")
    val nrOfPlayers = inputParts[0].toInt()
    val nrOfMarbles = inputParts[6].toInt()
    return Pair(nrOfPlayers, nrOfMarbles)
}

fun day09a(inputLine: String): Long {
    val (nrOfPlayers, nrOfMarbles) = parse(inputLine)

    val scores = placeMarbles(nrOfPlayers, nrOfMarbles)
    return scores.max()!!
}

fun day09b(inputLine: String): Long {
    val (nrOfPlayers, nrOfMarbles) = parse(inputLine)

    val scores = placeMarbles(nrOfPlayers, nrOfMarbles * 100)
    return scores.max()!!
}
