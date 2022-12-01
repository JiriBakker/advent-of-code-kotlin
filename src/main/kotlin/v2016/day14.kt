package v2016

import util.DoNotAutoExecute
import java.security.MessageDigest
import java.util.ArrayDeque

private fun md5Hash(string: String): String {
    return MessageDigest.getInstance("MD5").digest(string.toByteArray())
        .joinToString("")
        { String.format("%02x", it) }
}

private fun stretchedMd5Hash(string: String): String {
    var hash = string
    repeat(2017) { hash = md5Hash(hash) }
    return hash
}

private fun positiveNrs(start: Int = 0): Sequence<Int> = generateSequence(start) { it + 1 }

private fun findCharGroups(string: String, length: Int): List<Char> {
    return sequence {
        (0..string.length - length)
            .forEach { index ->
                if ((index + 1 until index + length).all { string[it] == string[index] }) {
                    yield(string[index])
                }
            }
    }.toList().distinct()
}

private fun findValidKeyIndices(salt: String, hashFunc: (String) -> String): Sequence<Int> {
    return sequence {
        val triples = ArrayDeque<Pair<Char, Int>>()
        val quintuples = mutableMapOf<Char, MutableList<Int>>()

        fun populateCharGroups(nr: Int) {
            val hash = hashFunc("$salt$nr")
            val charsWithQuintuple = findCharGroups(hash, 5)

            charsWithQuintuple.forEach { char -> quintuples.getOrPut(char, ::mutableListOf).add(nr) }

            val charsWithTriple = findCharGroups(hash, 3)
            if (charsWithTriple.isNotEmpty()) {
                triples.add(charsWithTriple.first() to nr)
            }
        }

        (0 until 1000).forEach { populateCharGroups(it) }

        positiveNrs(1000).forEach { nr ->
            populateCharGroups(nr)

            if (triples.isNotEmpty() && triples.peek().second == nr - 1000) {
                val (char, tripleIndex) = triples.pop()
                if (quintuples[char]?.any { quintIndex -> tripleIndex >= quintIndex - 1000 && tripleIndex < quintIndex } == true) {
                    yield(tripleIndex)
                }
            }
        }
    }
}

fun day14a(input: String): Int {
    return findValidKeyIndices(input, ::md5Hash)
        .take(64)
        .last()
}

@DoNotAutoExecute
fun day14b(input: String): Int {
    return findValidKeyIndices(input, ::stretchedMd5Hash)
        .take(64)
        .last()
}
