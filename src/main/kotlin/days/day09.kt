package days.day09

private class Marble(val number: Int) {
    var left: Marble = this
        private set

    var right: Marble = this
        private set

    fun connectLeft(other: Marble) {
        this.left = other
        other.right = this
    }

    fun connectRight(other: Marble) {
        other.connectLeft(this)
    }
}

private fun placeMarbles(nrOfPlayers: Int, nrOfMarbles: Int): Array<Long> {
    val playerScores = Array(nrOfPlayers) { 0L }

    var currentMarble = Marble(0)
    for (marbleNr in 1..nrOfMarbles) {
        if (marbleNr % 23 == 0) {
            repeat(7) { currentMarble = currentMarble.left }

            val currentPlayerNr = (marbleNr - 1) % nrOfPlayers
            playerScores[currentPlayerNr] += (marbleNr + currentMarble.number).toLong()

            currentMarble.left.connectRight(currentMarble.right)
            currentMarble = currentMarble.right
        } else {
            currentMarble = currentMarble.right
            val newMarble = Marble(marbleNr)
            newMarble.connectRight(currentMarble.right)
            newMarble.connectLeft(currentMarble)
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
