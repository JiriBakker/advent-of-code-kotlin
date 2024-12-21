fun day21a(input: List<String>): Long {
    var complexity = 0L

    for (code in input) {
        val results1 = apply(code, numericKeypadLookup)

        val shortestLength = results1.minOf {
            findLength('A', it, directionalKeypadLookup, 2)
        }

        complexity += shortestLength * code.take(3).toLong()
    }
    return complexity
}

fun day21b(input: List<String>): Long {
    var complexity = 0L

    for (code in input) {
        val results1 = apply(code, numericKeypadLookup)

        val shortestLength = results1.minOf {
            findLength('A', it, directionalKeypadLookup, 25)
        }

        complexity += shortestLength * code.take(3).toLong()
    }
    return complexity
}

private fun apply(code: String, lookup: Map<Char, Map<Char, List<String>>>): List<String> {
    var outputs = mutableListOf("")

    var curKey = 'A'
    for (char in code) {
        val moves = lookup[curKey]!![char]!!
        val newOutputs = mutableListOf<String>()
        for (output in outputs) {
            for (move in moves) {
                newOutputs.add(output + move + 'A')
            }
        }
        outputs = newOutputs
        curKey = char
    }

    return outputs
}

private val lengthCache = mutableMapOf<Triple<Char, String, Int>, Long>()
private val lengthCache2 = mutableMapOf<Triple<Char, Char, Int>, Long>()

private fun findLength(start: Char, chars: String, lookup: Map<Char, Map<Char, List<String>>>, depth: Int): Long {
    // if (lengthCache.containsKey(Triple(start, chars, depth))) {
    //     return lengthCache[Triple(start, chars, depth)]!!
    // }

    if (depth == 0) {
        return chars.length.toLong()
    }

    var totalLength = 0L
    var curChar = start
    for (char in chars) {
        if (lengthCache2.containsKey(Triple(curChar, char, depth))) {
            totalLength += lengthCache2[Triple(curChar, char, depth)]!!
            curChar = char
            continue
        }

        val options = lookup[curChar]!![char]!!
        val minValue =
            options
                .minOf { option ->
                    findLength('A', option + 'A', lookup, depth - 1)
                }

        lengthCache2[Triple(curChar, char, depth)] = minValue

        totalLength += minValue
        curChar = char
    }

    return totalLength
}




private val numericKeypadLookup = mapOf(
    '0' to mapOf(
        '0' to listOf(""),
        '1' to listOf("^<"),
        '2' to listOf("^"),
        '3' to listOf("^>", ">^"),
        '4' to listOf("^^<"),
        '5' to listOf("^^"),
        '6' to listOf("^^>", ">^^"),
        '7' to listOf("^^^<"),
        '8' to listOf("^^^"),
        '9' to listOf("^^^>", ">^^^"),
        'A' to listOf(">")
    ),
    '1' to mapOf(
        '0' to listOf(">v"),
        '1' to listOf(""),
        '2' to listOf(">"),
        '3' to listOf(">>"),
        '4' to listOf("^"),
        '5' to listOf("^>", ">^"),
        '6' to listOf("^>>", ">>^"),
        '7' to listOf("^^"),
        '8' to listOf("^^>", ">^^"),
        '9' to listOf("^^>>", ">>^^"),
        'A' to listOf(">>v", ">v>")
    ),
    '2' to mapOf(
        '0' to listOf("v"),
        '1' to listOf("<"),
        '2' to listOf(""),
        '3' to listOf(">"),
        '4' to listOf("<^", "^<"),
        '5' to listOf("^"),
        '6' to listOf("^>", ">^"),
        '7' to listOf("<^^", "^^<"),
        '8' to listOf("^^"),
        '9' to listOf(">^^", "^^>"),
        'A' to listOf("v>", ">v")
    ),
    '3' to mapOf(
        '0' to listOf("v<", "<v"),
        '1' to listOf("<<"),
        '2' to listOf("<"),
        '3' to listOf(""),
        '4' to listOf("<<^", "^<<"),
        '5' to listOf("<^", "^<"),
        '6' to listOf("^"),
        '7' to listOf("<<^^", "^^<<"),
        '8' to listOf("<^^", "^^<"),
        '9' to listOf("^^"),
        'A' to listOf("v")
    ),
    '4' to mapOf(
        '0' to listOf(">vv"),
        '1' to listOf("v"),
        '2' to listOf("v>", ">v"),
        '3' to listOf("v>>", ">>v"),
        '4' to listOf(""),
        '5' to listOf(">"),
        '6' to listOf(">>"),
        '7' to listOf("^"),
        '8' to listOf("^>", ">^"),
        '9' to listOf("^>>", ">>^"),
        'A' to listOf(">>vv")
    ),
    '5' to mapOf(
        '0' to listOf("vv"),
        '1' to listOf("<v", "v<"),
        '2' to listOf("v"),
        '3' to listOf("v>", ">v"),
        '4' to listOf("<"),
        '5' to listOf(""),
        '6' to listOf(">"),
        '7' to listOf("<^", "^<"),
        '8' to listOf("^"),
        '9' to listOf("^>", ">^"),
        'A' to listOf("vv>", ">vv")
    ),
    '6' to mapOf(
        '0' to listOf("<vv", "vv<"),
        '1' to listOf("<<v", "v<<"),
        '2' to listOf("<v", "v<"),
        '3' to listOf("v"),
        '4' to listOf("<<"),
        '5' to listOf("<"),
        '6' to listOf(""),
        '7' to listOf("<<^", "^<<"),
        '8' to listOf("<^", "^<"),
        '9' to listOf("^"),
        'A' to listOf("vv")
    ),
    '7' to mapOf(
        '0' to listOf(">vvv"),
        '1' to listOf("vv"),
        '2' to listOf("vv>", ">vv", "v>v"),
        '3' to listOf("vv>>", ">>vv"),
        '4' to listOf("v"),
        '5' to listOf("v>", ">v"),
        '6' to listOf("v>>", ">>v"),
        '7' to listOf(""),
        '8' to listOf(">"),
        '9' to listOf(">>"),
        'A' to listOf(">>vvv")
    ),
    '8' to mapOf(
        '0' to listOf("vvv"),
        '1' to listOf("<vv", "vv<"),
        '2' to listOf("vv"),
        '3' to listOf("vv>", ">vv"),
        '4' to listOf("<v", "v<"),
        '5' to listOf("v"),
        '6' to listOf("v>", ">v"),
        '7' to listOf("<"),
        '8' to listOf(""),
        '9' to listOf(">"),
        'A' to listOf("vvv>", ">vvv")
    ),
    '9' to mapOf(
        '0' to listOf("<vvv", "vvv<"),
        '1' to listOf("<<vv", "vv<<"),
        '2' to listOf("<vv", "vv<"),
        '3' to listOf("vv"),
        '4' to listOf("<<v", "v<<"),
        '5' to listOf("<v", "v<"),
        '6' to listOf("v"),
        '7' to listOf("<<"),
        '8' to listOf("<"),
        '9' to listOf(""),
        'A' to listOf("vvv")
    ),
    'A' to mapOf(
        '0' to listOf("<"),
        '1' to listOf("^<<"),
        '2' to listOf("<^", "^<"),
        '3' to listOf("^"),
        '4' to listOf("^^<<"),
        '5' to listOf("<^^", "^^<"),
        '6' to listOf("^^"),
        '7' to listOf("^^^<<"),
        '8' to listOf("<^^^","^^^<"),
        '9' to listOf("^^^"),
        'A' to listOf("")
    )
)

private val directionalKeypadLookup = mapOf(
    'A' to mapOf(
        'A' to listOf(""),
        '^' to listOf("<"),
        '<' to listOf("v<<"),
        'v' to listOf("<v", "v<"),
        '>' to listOf("v")
    ),
    '^' to mapOf(
        'A' to listOf(">"),
        '^' to listOf(""),
        '<' to listOf("v<"),
        'v' to listOf("v"),
        '>' to listOf("v>", ">v")
    ),
    '<' to mapOf(
        'A' to listOf(">>^"),
        '^' to listOf(">^"),
        '<' to listOf(""),
        'v' to listOf(">"),
        '>' to listOf(">>")
    ),
    'v' to mapOf(
        'A' to listOf(">^", "^>"),
        '^' to listOf("^"),
        '<' to listOf("<"),
        'v' to listOf(""),
        '>' to listOf(">")
    ),
    '>' to mapOf(
        'A' to listOf("^"),
        '^' to listOf("<^", "^<"),
        '<' to listOf("<<"),
        'v' to listOf("<"),
        '>' to listOf("")
    )
)