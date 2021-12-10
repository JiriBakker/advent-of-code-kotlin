package v2021

private val openingChars = setOf('[', '(', '{', '<')

private val opposingChars =
    mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

private val illegalCharScores =
    mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
   )

private val incompleteCharScores =
    mapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4
    )

private class CorruptedInputException(val corruptedChar: Char) : Exception()

private fun String.findIncompleteChars(): List<Char> {
    val openChars = ArrayDeque<Char>()

    fun String.validate(startPos: Int, nextValidClosingChar: Char): Int {
        var pos = startPos
        while (pos < this.length) {
            val char = this[pos]
            when {
                openingChars.contains(char) -> {
                    openChars.addFirst(this[pos])
                    pos = validate(pos + 1, opposingChars[char]!!)
                }
                char != nextValidClosingChar -> {
                    throw CorruptedInputException(char)
                }
                else -> {
                    openChars.removeFirst()
                    return pos + 1
                }
            }
        }
        return pos
    }

    validate(0, ' ')

    return openChars
}

fun day10a(input: List<String>): Int {
    return input.sumOf { line ->
        try {
            line.findIncompleteChars()
            0
        } catch (e: CorruptedInputException) {
            illegalCharScores[e.corruptedChar]!!
        }
    }
}

private fun List<Char>.computeIncompleteCharsScore() =
    map { incompleteCharScores[it]!!}
        .fold(0L) { total, score -> total * 5L + score }

private fun <T : Comparable<T>> List<T>.getMiddle() =
    sorted()[size / 2]

fun day10b(input: List<String>): Long {
    val scores = input.mapNotNull { line ->
        try {
            line
                .findIncompleteChars()
                .computeIncompleteCharsScore()
        } catch (e: CorruptedInputException) {
            null
        }
    }

    return scores.getMiddle()
}