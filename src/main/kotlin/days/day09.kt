package days.day09

private class Marble(val number: Int) {
    var left: Marble = this
    var right: Marble = this
}

private fun placeMarbles(nrOfPlayers: Int, nrOfMarbles: Int): Array<Long> {
    val playerScores = Array(nrOfPlayers) { 0L }

    var currentMarble = Marble(0)
    for (marbleNr in 1..nrOfMarbles) {
        if (marbleNr % 23 == 0) {
            val currentPlayerNr = (marbleNr - 1) % nrOfPlayers
            for (i in 0..6) {
                currentMarble = currentMarble.left
            }

            playerScores[currentPlayerNr] += (marbleNr + currentMarble.number).toLong()

            currentMarble.left.right = currentMarble.right
            currentMarble.right.left = currentMarble.left
            currentMarble = currentMarble.right
        } else {
            currentMarble = currentMarble.right
            val newMarble = Marble(marbleNr)
            newMarble.right = currentMarble.right
            newMarble.left = currentMarble
            currentMarble.right.left = newMarble
            currentMarble.right = newMarble
            currentMarble = newMarble
        }
    }

    return playerScores
}

fun day09a(inputLine: String): Long {
    val inputParts = inputLine.split(" ")
    val nrOfPlayers = inputParts[0].toInt()
    val nrOfMarbles = inputParts[6].toInt()

    val scores = placeMarbles(nrOfPlayers, nrOfMarbles)
    return scores.max()!!
}

fun day09b(inputLine: String): Long {
    val inputParts = inputLine.split(" ")
    val nrOfPlayers = inputParts[0].toInt()
    val nrOfMarbles = inputParts[6].toInt()

    val scores = placeMarbles(nrOfPlayers, nrOfMarbles * 100)
    return scores.max()!!
}
