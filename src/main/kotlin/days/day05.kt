package days.day05

import java.util.LinkedList
import java.util.Stack

private fun computeReactedPolymerLength(polymerChars: List<Char>): Int {
    val units = LinkedList<Char>(polymerChars)
    val buffer = Stack<Char>()

    while (units.isNotEmpty()) {
        val next = units.poll()

        if (buffer.isEmpty()) {
            buffer.push(next)
            continue
        }

        val prev = buffer.peek()

        if (prev.isLowerCase() != next.isLowerCase() && prev.equals(next, true)) {
            buffer.pop()
        } else {
            buffer.push(next)
        }
    }

    return buffer.size
}

fun day05a(polymer: String): Int {
    return computeReactedPolymerLength(polymer.toList())
}

fun day05b(polymer: String): Int {
    val alphabet = 'a'..'z'

    val polymerChars = polymer.toList()

    return alphabet.map { letter ->
            val polymerWithoutLetter = polymerChars.filter { char -> char.toLowerCase() != letter }
            computeReactedPolymerLength(polymerWithoutLetter)
        }
        .min()!!
}