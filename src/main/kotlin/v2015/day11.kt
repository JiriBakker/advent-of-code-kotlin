package v2015

private fun hasIncreasingStraight(password: List<Int>): Boolean {
    password.fold(-1 to -1) { (a, b), cur ->
        if (a == b - 1 && b == cur - 1) {
            return true
        }
        b to cur
    }
    return false
}

private val disallowedChars = setOf('i'.code, 'o'.code, 'l'.code)
private const val lowerA = 'a'.code
private const val lowerZ = 'z'.code

private fun findPairsIndices(password: List<Int>): List<Int> {
    return password
        .zipWithNext()
        .withIndex()
        .filter { (_, pair) -> pair.first == pair.second }
        .map { (index, _) -> index }
}

object Day11Utils {
    fun isValidPassword(password: List<Int>): Boolean {
        val pairIndices = findPairsIndices(password)

        return hasIncreasingStraight(password)
            && pairIndices.any { index -> pairIndices.any { it - index >= 2 } }
    }
}

private fun generateNextPassword(password: List<Int>): List<Int> {
    val nextPassword = password.toMutableList()

    val indexOfFirstDisallowedChar = password.indexOfFirst { disallowedChars.contains(it) }
    if (indexOfFirstDisallowedChar >= 0) {
        nextPassword[indexOfFirstDisallowedChar]++
        for (i in indexOfFirstDisallowedChar + 1 until password.size) {
            nextPassword[i] = lowerA
        }
        return nextPassword
    }

    for (i in password.size - 1 downTo 0) {
        do {
            nextPassword[i]++
        } while (disallowedChars.contains(nextPassword[i]))

        if (nextPassword[i] > lowerZ) {
            nextPassword[i] = lowerA
        } else {
            break
        }
    }

    return nextPassword
}

private fun String.toInts(): List<Int> = this.map { it.code }
private fun List<Int>.intsToString(): String = this.map(Int::toChar).joinToString("")

fun day11a(input: String): String {
    return generateSequence(input.toInts(), ::generateNextPassword)
        .first(Day11Utils::isValidPassword)
        .intsToString()

}

fun day11b(input: String): String {
    val nextPassword =
        generateSequence(input.toInts(), ::generateNextPassword)
            .first(Day11Utils::isValidPassword)

    return generateSequence(nextPassword, ::generateNextPassword)
        .drop(1)
        .first(Day11Utils::isValidPassword)
        .intsToString()
}