import util.combine

private data class Rule(val index: Int, val options: List<List<Int>>)

private typealias Rules = Map<Int, Rule>
private typealias Messages = List<String>
private typealias LetterIndices = Pair<Int, Int>

private fun parseInput(input: List<String>): Triple<Rules, Messages, LetterIndices> {
    var indexA = -1
    var indexB = -1

    val rules = mutableMapOf<Int, Rule>()

    input.takeWhile { it.isNotEmpty() }
        .forEach { line ->
            val (indexString, rest) = line.split(": ")
            val index = indexString.toInt()

            when (rest[1]) {
                'a' -> indexA = index
                'b' -> indexB = index
                else -> {
                    val options = rest.split(" | ")
                    rules[index] =
                        Rule(index, options.map { option ->
                            option.split(" ").map { it.toInt() }
                        })
                }
            }
        }

    val messages = input.dropWhile { it.isNotEmpty() }.drop(1)

    return Triple(rules, messages, indexA to indexB)
}

private fun match(message: String, rules: Rules, ruleToMatch: Int, letterIndices: LetterIndices): Boolean {
    val (indexA, indexB) = letterIndices

    fun matchRecur(rule: Rule, startIndex: Int = 0): Pair<Boolean, Int> {
        rule.options.forEach checkOption@{ option ->
            var index = startIndex
            option.forEach {
                when (it) {
                    indexA -> if (message[index] != 'a') return@checkOption
                    indexB -> if (message[index] != 'b') return@checkOption
                    else -> {
                        val (matched, newIndex) = matchRecur(rules[it]!!, index)
                        if (!matched) return@checkOption
                        index = newIndex - 1
                    }
                }
                index++
            }
            return true to index
        }
        return false to startIndex
    }

    val (matched, index) = matchRecur(rules[ruleToMatch]!!)
    return matched && index == message.length
}

fun day19a(input: List<String>): Int {
    val (rules, messages, letterIndices) = parseInput(input)

    return messages.count { message ->
        match(message, rules, 0, letterIndices)
    }
}

fun day19b(input: List<String>, rule31and42MatchLength: Int = 8): Int {
    val (rules, messages, letterIndices) = parseInput(input)

    val options31 = mutableSetOf<String>()
    val options42 = mutableSetOf<String>()

    listOf('a','b').combine(rule31and42MatchLength).forEach { chars ->
        val message = chars.joinToString("")
        if (match(message, rules, 31, letterIndices)) options31.add(message)
        if (match(message, rules, 42, letterIndices)) options42.add(message)
    }

    val matches = messages.filter { message ->
        val chunks = message.chunked(rule31and42MatchLength)
        val remainder = chunks.dropWhile { chunk -> options42.contains(chunk) }
        val remainder2 = remainder.dropWhile { chunk -> options31.contains(chunk) }

        remainder.size < (chunks.size + 1) / 2
            && remainder.isNotEmpty()
            && remainder2.isEmpty()
    }
    return matches.count()
}
