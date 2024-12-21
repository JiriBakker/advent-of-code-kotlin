fun day21a(input: List<String>) =
    input.findShortestPathComplexity(2)

fun day21b(input: List<String>) =
    input.findShortestPathComplexity(25)

private fun List<String>.findShortestPathComplexity(keypadCount: Int): Long {
    var complexity = 0L

    for (code in this) {
        val shortestLength =
            findShortsPathThroughNumericPad(code)
                .minOf {
                    findShortestPathLengthThroughKeypads('A', it, keypadCount)
                }

        complexity += shortestLength * code.take(3).toLong()
    }
    return complexity
}

private fun findShortsPathThroughNumericPad(code: String): List<String> {
    var outputs = mutableListOf("")

    var curKey = 'A'
    for (char in code) {
        val moves = numericKeypadLookup[curKey]!![char]!!
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

private val lengthCache = mutableMapOf<Pair<String, Int>, Long>()

private fun findShortestPathLengthThroughKeypads(start: Char, chars: String, depth: Int): Long {
    if (lengthCache.containsKey(chars to depth)) {
        return lengthCache[chars to depth]!!
    }

    if (depth == 0) {
        return chars.length.toLong()
    }

    var totalLength = 0L
    var curChar = start
    for (char in chars) {
        val minValue =
            directionalKeypadLookup[curChar]!![char]!!
                .minOf { option ->
                    findShortestPathLengthThroughKeypads('A', option + 'A', depth - 1)
                }

        totalLength += minValue
        curChar = char
    }

    lengthCache[chars to depth] = totalLength
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