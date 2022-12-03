private fun List<String>.parseRules(): Map<String, List<String>> {
    return associate { line ->
        line
            .split(" -> ")
            .let { rule ->
                rule[0] to
                    listOf(
                        "${rule[0][0]}${rule[1]}",
                        "${rule[1]}${rule[0][1]}",
                    )
            }
    }
}

private fun String.polymerize(rules: Map<String, List<String>>, iterations: Int): Map<String, Long> {
    var pairCounts = rules.keys.associateWith { 0L }.toMutableMap()

    this.zipWithNext().forEach { (a, b) ->
        pairCounts["$a$b"] = pairCounts.getOrDefault("$a$b", 0L) + 1
    }

    repeat(iterations) {
        val newPairCounts = mutableMapOf<String, Long>()

        pairCounts.forEach { (key, count) ->
            val newPairs = rules[key]!!
            newPairCounts[newPairs[0]] = newPairCounts.getOrDefault(newPairs[0], 0L) + count
            newPairCounts[newPairs[1]] = newPairCounts.getOrDefault(newPairs[1], 0L) + count
        }

        pairCounts = newPairCounts
    }

    return pairCounts
}

private fun Map<String, Long>.countChars(leftMostChar: Char): Map<Char, Long> {
    return this.entries.fold(
        mapOf(leftMostChar to 1L) // Since we're only counting second chars of the pairs, make sure to count the leftmost char of the template once as well
    ) { counts, (key, count) ->
        counts.plus(key[1] to counts.getOrDefault(key[1], 0L) + count)
    }
}

private fun Map<Char, Long>.computeScore(): Long {
    val maxLetterCount = maxByOrNull { it.value }!!.value
    val minLetterCount = minByOrNull { it.value }!!.value
    return maxLetterCount - minLetterCount
}

private fun computePolymerizationScore(input: List<String>, iterations: Int): Long {
    val template = input.first()
    val rules = input.drop(2).parseRules()

    return template
        .polymerize(rules, iterations)
        .countChars(template[0])
        .computeScore()
}

fun day14a(input: List<String>) =
    computePolymerizationScore(input, 10)

fun day14b(input: List<String>) =
    computePolymerizationScore(input, 40)