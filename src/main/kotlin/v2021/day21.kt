package v2021

import util.max
import util.wrap

private data class Player(val id: Int, var position: Int, var score: Int = 0) {
    fun move(curDiceNr: Int) {
        position = (position + curDiceNr * 3 + 3).wrap(10)
        score += position
    }
}

fun day21a(input: List<String>): Long {
    val players = input.mapIndexed { id, line -> Player(id, line.split(" ").last().toInt()) }
    var curPlayerId = 0
    var curDiceNr = 1
    var diceRollCount = 0L

    while (true) {
        val curPlayer = players[curPlayerId]
        curPlayer.move(curDiceNr)

        diceRollCount += 3

        if (curPlayer.score >= 1000) {
            return players.minOf { it.score } * diceRollCount
        }

        curDiceNr = (curDiceNr + 3).wrap(1000)
        curPlayerId = (curPlayerId + 1) % players.size
    }
}

private val dimensionOdds =
    mapOf(
        3 to 1L,
        4 to 3L,
        5 to 6L,
        6 to 7L,
        7 to 6L,
        8 to 3L,
        9 to 1L
    )

private fun Pair<Long, Long>.times(amount: Long) =
    (first * amount) to (second * amount)

fun day21b(input: List<String>): Long {
    val players = input.mapIndexed { id, line -> Player(id, line.split(" ").last().toInt()) }

    fun play(playerAtTurn: Int, player1Score: Int, player1Pos: Int, player2Score: Int, player2Pos: Int): Pair<Long, Long> =
        dimensionOdds
            .entries
            .fold(0L to 0L) { acc, dimensionOdds ->
                val (steps, dimensions) = dimensionOdds
                var nextPlayer1Pos = player1Pos
                var nextPlayer2Pos = player2Pos
                var nextPlayer1Score = player1Score
                var nextPlayer2Score = player2Score

                if (playerAtTurn == 0) {
                    nextPlayer1Pos = (player1Pos + steps).wrap(10)
                    nextPlayer1Score = player1Score + nextPlayer1Pos
                } else if (playerAtTurn == 1) {
                    nextPlayer2Pos = (player2Pos + steps).wrap(10)
                    nextPlayer2Score = player2Score + nextPlayer2Pos
                }

                val (first, second) =
                    when {
                        nextPlayer1Score >= 21 -> dimensions to 0L
                        nextPlayer2Score >= 21 -> 0L to dimensions
                        else ->
                            play(
                                (playerAtTurn + 1) % 2,
                                nextPlayer1Score,
                                nextPlayer1Pos,
                                nextPlayer2Score,
                                nextPlayer2Pos
                            ).times(dimensions)
                    }

                (acc.first + first) to (acc.second + second)
            }

    val result =
        play(
            0,
            players[0].score,
            players[0].position,
            players[1].score,
            players[1].position
        )

    return max(result.first, result.second)
}