package v2020.days.day18

private fun compute(expression: String, startIndex: Int = 0): Pair<Int, Long> {
    var curOperator: (Long, Long) -> Long = Long::plus

    var lastNrChars = mutableListOf<Char>()
    fun popLastNr(): Long {
        val lastNr = lastNrChars.joinToString().toLongOrNull() ?: 0L
        lastNrChars = mutableListOf()
        return lastNr
    }

    var total = 0L

    var index = startIndex
    while (index in expression.indices) {
        when (val char = expression[index]) {
            ')' -> break
            '(' -> {
                val (newIndex, subtotal) = compute(expression, index + 1)
                total = curOperator(total, subtotal)
                index = newIndex
            }
            ' ' -> {
                if (lastNrChars.isNotEmpty()) {
                    total = curOperator(total, popLastNr())
                }
            }
            '+' -> {
                curOperator = Long::plus
                index++
            }
            '*' -> {
                curOperator = Long::times
                index++
            }
            else -> lastNrChars.add(char)
        }
        index++
    }

    if (lastNrChars.isNotEmpty()) {
        return index to curOperator(total, popLastNr())
    }
    return index to total
}

private fun compute2(expression: String, startIndex: Int = 0): Pair<Int, Long> {
    val sums = mutableListOf<Long>()

    var lastNrChars = mutableListOf<Char>()
    fun popLastNr(): Long {
        val lastNr = lastNrChars.joinToString().toLongOrNull() ?: 0L
        lastNrChars = mutableListOf()
        return lastNr
    }

    var sum = 0L

    var index = startIndex
    while (index in expression.indices) {
        when (val char = expression[index]) {
            ')' -> break
            '(' -> {
                val (newIndex, subSum) = compute2(expression, index + 1)
                sum += subSum
                index = newIndex
            }
            '*' -> {
                sums.add(sum)
                sum = 0L
                index++
            }
            '+' -> index++
            ' ' -> sum += popLastNr()
            else -> lastNrChars.add(char)
        }
        index++
    }

    sum += popLastNr()
    if (sum > 0) sums.add(sum)

    val total = sums.fold(1L) { acc, it -> acc * it }
    return index to total
}

private typealias ComputeFunc = (String) -> Pair<Int, Long>

private fun computeSums(input: List<String>, computeFunc: ComputeFunc): Long =
    input.fold(0L) { acc, it -> computeFunc(it).second + acc }

fun day18a(input: List<String>): Long {
    return computeSums(input, ::compute)
}

fun day18b(input: List<String>): Long {
    return computeSums(input, ::compute2)
}

