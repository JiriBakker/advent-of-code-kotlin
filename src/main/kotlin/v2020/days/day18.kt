package v2020.days.day18

import kotlin.math.max

private fun compute(expression: String, startIndex: Int): Pair<Int, Long> {
    val plus = { it: Long, other: Long -> it.plus(other) }
    val times = { it: Long, other: Long -> it.times(other) }

    var index = startIndex
    var total = 0L
    var lastNrChars = mutableListOf<Char>()
    var curOperator = plus

    while (index in expression.indices) {
        when (val char = expression[index]) {
            '(' -> {
                val (newIndex, subtotal) = compute(expression, index + 1)
                total = curOperator(total, subtotal)
                index = newIndex
            }
            ')' -> {
                if (lastNrChars.isNotEmpty()) {
                    return index to curOperator(total, lastNrChars.joinToString().toLong())
                }
                return index to total
            }
            ' ' -> {
                if (lastNrChars.isNotEmpty()) {
                    total = curOperator(total, lastNrChars.joinToString().toLong())
                    lastNrChars = mutableListOf()
                }
            }
            '+' -> {
                curOperator = plus
                index++
            }
            '*' -> {
                curOperator = times
                index++
            }
            else -> lastNrChars.add(char)
        }
        index++
    }

    if (lastNrChars.isNotEmpty()) {
        return index to curOperator(total, lastNrChars.joinToString().toLong())
    }
    return index to total
}

private fun compute2(expression: String, startIndex: Int): Pair<Int, Long> {
    val sums = mutableListOf<Long>()

    var lastNrChars = mutableListOf<Char>()
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
            '+' -> {
                index++
            }
            ' ' -> {
                sum += lastNrChars.joinToString().toLongOrNull() ?: 0L
                lastNrChars = mutableListOf()
            }
            else -> {
                lastNrChars.add(char)
            }
        }
        index++
    }

    sum += lastNrChars.joinToString().toLongOrNull() ?: 0
    sums.add(max(sum, 1))

    val total = sums.fold(1L) { acc, it -> acc * it }
    return index to total
}

fun day18a(input: List<String>): Long {
    return input.fold(0L) { acc, it -> compute(it, 0).second + acc }
}

fun day18b(input: List<String>): Long {
    return input.fold(0L) { acc, it -> compute2(it, 0).second + acc }
}

