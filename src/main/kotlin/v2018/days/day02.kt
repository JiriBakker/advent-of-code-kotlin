package v2018.days.day02

import util.forEachCombinationPair

fun day02a(ids: List<String>): Int {
    val countLetters = { id: String ->
        id.toCharArray()
            .fold(mutableMapOf()) { counts: MutableMap<Char, Int>, char ->
                counts[char] = counts.getOrDefault(char, 0) + 1
                counts
            }
    }

    val sums = ids.map(countLetters)
        .fold(Pair(0, 0)) { sums, letterCounts ->
                Pair(
                    sums.first + (if (letterCounts.containsValue(2)) 1 else 0),
                    sums.second + (if (letterCounts.containsValue(3)) 1 else 0)
                )
            }

    return sums.first * sums.second
}

fun day02b(ids: List<String>): String {
    val computeIdIntersection = { id1: String, id2: String ->
        val chars1 = id1.toCharArray()
        val chars2 = id2.toCharArray()
        val intersection: MutableList<Char> = mutableListOf()
        var differenceFound = false
        for (i in 0 until chars1.size) {
            if (chars1[i] != chars2[i]) {
                if (differenceFound) {
                    break
                }
                differenceFound = true
            } else {
                intersection.add(chars1[i])
            }
        }
        intersection
    }

    ids.forEachCombinationPair { first, second ->
        val intersection = computeIdIntersection(first, second)
        if (intersection.size == first.length - 1) {
            return String(intersection.toCharArray())
        }
    }

    throw Exception("No matching ID's found")
}
