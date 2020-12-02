package v2020.days.day02

import util.product

private data class Policy(
    val nr1: Int,
    val nr2: Int,
    val char: Char
)

private fun parseLine(line: String): Pair<Policy, String> {
    val (policy, password) = line.split(": ")

    val (nr1, rest) = policy.split("-")
    val (nr2, char) = rest.split(" ")

    return Policy(nr1.toInt(), nr2.toInt(), char.first()) to password
}

private fun countLetters(password: String): Map<Char, Int> {
    return password.fold(mutableMapOf(), { counts, char ->
        counts.put(char, counts.getOrDefault(char, 0) + 1)
        counts
    })
}

fun day02a(input: List<String>): Int {
    return input
        .map(::parseLine)
        .count { (policy, password) ->
            val nrOfCharOccurrences =
                countLetters(password)
                    .getOrDefault(policy.char, 0)

            nrOfCharOccurrences in policy.nr1 .. policy.nr2
        }
}

fun day02b(input: List<String>): Int {
    return input
        .map(::parseLine)
        .count { (policy, password) ->
            var count = 0
            if (password.getOrNull(policy.nr1 - 1) == policy.char) {
                count++
            }
            if (password.getOrNull(policy.nr2 - 1) == policy.char) {
                count++
            }

            count == 1
        }
}

