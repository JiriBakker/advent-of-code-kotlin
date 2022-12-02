package v2022

fun day02a(input: List<String>) =
    input
        .computeRockPaperScissorScore()

fun day02b(input: List<String>) =
    input
        .convertToOptimalStrategy()
        .computeRockPaperScissorScore()

private fun List<String>.computeRockPaperScissorScore() =
    this.sumOf { line ->
        when (line) {
            "A X" -> 4 // 3 + 1
            "A Y" -> 8 // 6 + 2
            "A Z" -> 3 // 0 + 3

            "B X" -> 1 // 0 + 1
            "B Y" -> 5 // 3 + 2
            "B Z" -> 9 // 6 + 3

            "C X" -> 7 // 6 + 1
            "C Y" -> 2 // 0 + 2
            "C Z" -> 6 // 3 + 3

            else -> throw IllegalArgumentException("Rock, Paper, What?!")
        }.toLong()
    }

private fun List<String>.convertToOptimalStrategy() =
    this.map { line ->
        when (line) {
            "A X" -> "A Z"
            "A Y" -> "A X"
            "A Z" -> "A Y"

            "B X" -> "B X"
            "B Y" -> "B Y"
            "B Z" -> "B Z"

            "C X" -> "C Y"
            "C Y" -> "C Z"
            "C Z" -> "C X"

            else -> throw IllegalArgumentException("Rock, Paper, What?!")
        }
    }
