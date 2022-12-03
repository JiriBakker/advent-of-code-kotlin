import util.countLetters

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

private fun String.hasCharAtPos(char: Char, base1Pos: Int): Boolean {
    return this.getOrNull(base1Pos - 1) == char
}

fun day02a(input: List<String>): Int {
    return input
        .map(::parseLine)
        .count { (policy, password) ->
            val nrOfCharOccurrences =
                password.countLetters()
                    .getOrDefault(policy.char, 0)

            nrOfCharOccurrences in policy.nr1 .. policy.nr2
        }
}

fun day02b(input: List<String>): Int {
    return input
        .map(::parseLine)
        .count { (policy, password) ->
            val count =
                (if (password.hasCharAtPos(policy.char, policy.nr1)) 1 else 0) +
                (if (password.hasCharAtPos(policy.char, policy.nr2)) 1 else 0)

            count == 1
        }
}

