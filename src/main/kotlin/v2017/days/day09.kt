package v2017.days.day09

fun day09a(input: String): Int {
    fun countGroups(startIndex: Int, depth: Int): Pair<Int, Int> {
        var score = 0
        var index = startIndex
        var isInGarbage = false

        while (index < input.length) {
            if (input[index] == '!') {
                index += 2
                continue
            }
            if (isInGarbage && input[index] != '>') {
                index++
                continue
            }

            when (input[index]) {
                '{' -> {
                    val (childScore, endIndex) = countGroups(index + 1, depth + 1)
                    score += childScore + depth + 1
                    index = endIndex
                }
                '}' -> return score to index
                '<' -> isInGarbage = true
                '>' -> isInGarbage = false
            }
            index++
        }

        return score to index
    }

    return countGroups(0, 0).first
}

fun day09b(input: String): Int {
    var index = 0
    var isInGarbage = false
    var garbageCount = 0
    while (index < input.length) {
        if (input[index] == '!') {
            index++
        } else if (isInGarbage) {
            if (input[index] == '>') {
                isInGarbage = false
            } else {
                garbageCount++
            }
        } else if (input[index] == '<') {
            isInGarbage = true
        }
        index++
    }
    return garbageCount
}
