package v2015.days.day12

fun day12a(input: String): Long {
    return sequence {
        var i = 0
        while (i < input.length) {
            if (input[i] == '-' || input[i].isDigit()) {
                var j = i + 1
                val chars = mutableListOf(input[i])
                while (input[j].isDigit()) {
                    chars.add(input[j++])
                }
                yield(chars.joinToString("").toLong())
                i = j
            } else {
                i++
            }
        }
    }
        .sum()
}

private fun computeSum(input: String, startIndex: Int = 0, depth: Int = 0): Pair<Long, Int> {
    var sum = 0L
    var i = startIndex
    var foundRedValue = false
    while (i < input.length) {
        if (input[i] == '}') {
            break
        } else if (input[i] == '{') {
            val (sumDelta, lastIndex) = computeSum(input, i + 1, depth + 1)
            sum += sumDelta
            i = lastIndex + 1
        } else if (!foundRedValue && ((input[i] == '-'  && input[i + 1].isDigit()) || input[i].isDigit())) {
            val chars = mutableListOf<Char>()
            do {
                chars.add(input[i])
            } while (input[++i].isDigit())
            sum += chars.joinToString("").toLong()
        } else if (!foundRedValue && input[i] == ':' && input[i + 1] == '"' && input[i + 2] == 'r' && input[i + 3] == 'e' && input[i + 4] == 'd' && input[i + 5] == '"') {
            foundRedValue = true
            i += 6
        } else {
            i++
        }
    }
    if (foundRedValue) {
        println("Dropping sum: $sum (depth: $depth, index: $i)")
    }
    return (if (foundRedValue) 0L else sum) to i
}

fun day12b(input: String): Long {
    return computeSum(input).first
}