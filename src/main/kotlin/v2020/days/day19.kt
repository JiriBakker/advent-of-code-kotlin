package v2020.days.day19

import util.combine

private data class Rule(val index: Int, val options: List<List<Int>>)

private fun parseInput(input: List<String>): Triple<Map<Int, Rule>, List<String>, Pair<Int, Int>> {
    var indexA = -1
    var indexB = -1
    val rules = input.takeWhile { it.isNotEmpty() }
        .mapNotNull { line ->
            val (id, rest) = line.split(": ")
            when (rest) {
                "\"a\"" -> {
                    indexA = id.toInt()
                    null
                }
                "\"b\"" -> {
                    indexB = id.toInt()
                    null
                }
                else -> {
                    val options = rest.split(" | ")

                    id.toInt() to Rule(id.toInt(), options.map { option ->
                        option.split(" ").map { it.toInt() }
                    })
                }
            }
        }.toMap()

    val messages = input.dropWhile { it.isNotEmpty() }.drop(1)

    return Triple(rules, messages, indexA to indexB)
}

private fun match(message: String, rules: Map<Int, Rule>, ruleIndex: Int, letterIndices: Pair<Int, Int>, startIndex: Int = 0): Pair<Boolean, Int> {
    if (startIndex !in message.indices) {
        return false to startIndex
    }

    val (indexA, indexB) = letterIndices

    val rule = rules[ruleIndex]!!
    rule.options.forEach checkOption@{ option ->
        var index = startIndex
        option.forEach {
            when (it) {
                indexA -> {
                    if (message[index] != 'a') {
                        return@checkOption
                    }
                    index++
                }
                indexB -> {
                    if (message[index] != 'b') {
                        return@checkOption
                    }
                    index++
                }
                else -> {
                    val (matched, newIndex) = match(message, rules, it, letterIndices, index)
                    if (!matched) {
                        return@checkOption
                    }
                    index = newIndex
                }
            }
        }
        return true to index
    }
    return false to startIndex
}

fun day19a(input: List<String>): Int {
    val (rules, messages, letterIndices) = parseInput(input)

    return messages.count { message ->
        val (matched, index) = match(message, rules, 0, letterIndices)
        matched && index == message.length
    }
}

fun day19b(input: List<String>, rule31and42MatchLength: Int = 8): Int {
    val (rules, messages, letterIndices) = parseInput(input)

    val options31 = mutableSetOf<String>()
    val options42 = mutableSetOf<String>()

    listOf('a','b').combine(rule31and42MatchLength).forEach { chars ->
        val message = chars.joinToString("")
        if (match(message, rules, 31, letterIndices).first) options31.add(message)
        if (match(message, rules, 42, letterIndices).first) options42.add(message)
    }

    val matches = messages.filter { message ->
        var remainder = message

        var countRemove31s = 0
        do {
            val lastLength = remainder.length
            options31.forEach { option ->
                if (remainder.endsWith(option)) {
                    remainder = remainder.dropLast(option.length)
                    countRemove31s++
                }
            }
        } while (remainder.length < lastLength)

        var countRemove42s = 0

        do {
            val lastLength = remainder.length
            options42.forEach { option ->
                if (remainder.endsWith(option)) {
                    remainder = remainder.dropLast(option.length)
                    countRemove42s++
                }
            }
        } while (remainder.length < lastLength)

        remainder.isEmpty() && countRemove31s > 0 && countRemove42s > countRemove31s
    }

    return matches.count()
}

fun day19b2(input: List<String>, rule31and42MatchLength: Int = 8): Int {
    val (rules, messages, letterIndices) = parseInput(input)

    val options31 = mutableSetOf<String>()
    val options42 = mutableSetOf<String>()

    listOf('a','b').combine(rule31and42MatchLength).forEach { chars ->
        val message = chars.joinToString("")
        if (match(message, rules, 31, letterIndices).first) options31.add(message)
        if (match(message, rules, 42, letterIndices).first) options42.add(message)
    }

    val matches = messages.filter { message ->
        val chunks = message.chunked(5)
        val remainder = chunks.dropWhile { chunk -> options42.contains(chunk) }
        remainder.size <= chunks.size / 2
            && remainder.isNotEmpty()
            && remainder.dropWhile { chunk -> options31.contains(chunk) }.isEmpty()
    }
    return matches.count()
}
