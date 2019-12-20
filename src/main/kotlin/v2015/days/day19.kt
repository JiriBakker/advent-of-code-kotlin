package v2015.days.day19

import java.util.PriorityQueue

private fun parseInput(input: List<String>): Pair<String, Map<String, List<String>>> {
    val startMolecule = input.last()
    val replacements =
        input
            .dropLast(2)
            .map {
                val (atom, output) = it.split(" => ")
                atom to output
            }
            .groupBy { it.first }
            .mapValues { it.value.map { output -> output.second } }

    return startMolecule to replacements
}

fun day19a(input: List<String>): Int {
    val (startMolecule, replacements) = parseInput(input)

    return sequence {
        var i = 0
        while (i < startMolecule.length) {
            val prefix = startMolecule.take(i)
            val curChar = startMolecule[i]

            if (replacements.containsKey("$curChar")) {
                val suffix = startMolecule.drop(i + 1)
                replacements["$curChar"]!!.forEach {
                    yield(prefix + it + suffix)
                }
            } else {
                val nextChar = startMolecule.getOrElse(i + 1) { ' ' }
                if (replacements.containsKey("$curChar$nextChar")) {
                    val suffix = startMolecule.drop(i + 2)
                    replacements["$curChar$nextChar"]!!.forEach {
                        yield(prefix + it + suffix)
                    }
                    i++
                }
            }
            i++
        }
    }.distinct().count()
}

private class State(val string: String, val count: Int)

private fun isAtIndex(toMatch: String, startIndex: Int, string: String): Boolean {
    if (startIndex + toMatch.length > string.length) {
        return false
    }

    for (i in toMatch.indices) {
        if (string[startIndex + i] != toMatch[i]) {
            return false
        }
    }
    return true
}

fun day19b_quick(input: List<String>): Int {
    val (startMolecule, _) = parseInput(input)

    val elements = sequence {
        var i = 0
        while (i < startMolecule.length) {
            if (i < startMolecule.length - 1 && startMolecule[i + 1].isLowerCase()) {
                yield("${startMolecule[i++]}${startMolecule[i++]}")
            } else {
                yield("${startMolecule[i++]}")
            }
        }
    }.toList()

    return elements.size - elements.count { it == "Ar" || it == "Rn" } - elements.count { it == "Y" } * 2 - 1
}

fun day19b(input: List<String>): Int {
    val (startMolecule, replacements) = parseInput(input)
    val invertedReplacements = replacements.flatMap { replacement -> replacement.value.map { it to replacement.key } }

    while (true) {
        var string = startMolecule
        var count = 0
        val shuffledReplacements = invertedReplacements.shuffled()
        while (string != "e") {
            val prevCount = count
            shuffledReplacements.forEach { (from, to) ->
                string = string.replace (from.toRegex()) {
                    count++
                    to
                }
            }
            if (prevCount == count) {
                break
            }
        }
        if (string == "e") {
            return count
        }
    }
}