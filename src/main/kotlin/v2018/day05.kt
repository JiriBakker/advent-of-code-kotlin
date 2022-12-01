package v2018

import java.util.Stack

private fun computeReactedPolymerLength(polymerChars: List<Char>, letterToIgnore: Char? = null): Int {
    val leftBuffer = Stack<Char>()
    val rightBuffer = Stack<Char>()
    rightBuffer.addAll(polymerChars.reversed().filter { it.lowercaseChar() != letterToIgnore })

    while (rightBuffer.isNotEmpty()) {
        val next = rightBuffer.pop()

        if (leftBuffer.isEmpty()) {
            leftBuffer.push(next)
            continue
        }

        val prev = leftBuffer.peek()
        if (prev.isLowerCase() != next.isLowerCase() && prev.equals(next, true)) {
            leftBuffer.pop()
        } else {
            leftBuffer.push(next)
        }
    }

    return leftBuffer.size
}

fun day05a(polymer: String): Int {
    return computeReactedPolymerLength(polymer.toList())
}

fun day05b(polymer: String): Int {
    val polymerChars = polymer.toList()
    return ('a'..'z').minOf { computeReactedPolymerLength(polymerChars, it) }
}