package v2015.days.day05

import kotlin.math.abs

private val vowels = setOf('a', 'e', 'i', 'o', 'u')
private val naughtyStrings = setOf(Pair('a', 'b'), Pair('c', 'd'), Pair('p', 'q'), Pair('x', 'y'))

private fun String.countVowels(): Int = this.count { vowels.contains(it) }
private fun String.hasDouble(): Boolean = this.zipWithNext().any { (cur, next) -> cur == next }
private fun String.hasNaughtyString(): Boolean = this.zipWithNext().any { naughtyStrings.contains(it) }

private fun String.hasDoublePair(): Boolean {
    val pairs = this.zipWithNext()

    return pairs.withIndex().any { (index, pair) ->
        pairs.withIndex().any { (otherIndex, otherPair) ->
            abs(index - otherIndex) > 1 && otherPair == pair
        }
    }
}

private fun String.hasPalindrome(): Boolean {
    return (0 until this.length - 2).any { index ->
        this[index] == this[index + 2]
    }
}

fun day05a(input: List<String>): Int {
    return input.count { !it.hasNaughtyString() && it.countVowels() >= 3 && it.hasDouble() }
}

fun day05b(input: List<String>): Int {
    return input.count { it.hasDoublePair() && it.hasPalindrome() }
}
