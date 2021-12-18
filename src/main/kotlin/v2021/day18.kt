package v2021

private class SnailfishNumber(value: Long, depth: Int) {
    var value: Long = value
        private set

    var depth: Int = depth
        private set

    operator fun component1() = value
    operator fun component2() = depth
}

private fun String.parseSnailfishNrs(): List<SnailfishNumber> {
    val numbers = mutableListOf<SnailfishNumber>()
    var curIndex = 0
    var curDepth = 0
    while (curIndex in indices) {
        val char = get(curIndex)
        when (char) {
            '[' -> curDepth++
            ']' -> curDepth--
            ',' -> Unit
            else -> numbers.add(SnailfishNumber(char.toString().toLong(), curDepth))
        }
        curIndex++
    }
    return numbers
}

private fun List<SnailfishNumber>.increaseDepth() =
    map { SnailfishNumber(it.value, it.depth + 1) }

private fun List<SnailfishNumber>.getMaxDepth() =
    maxOf { it.depth }

private fun List<SnailfishNumber>.getMaxValue() =
    maxOf { it.value }

private fun <T> MutableList<T>.insertAt(index: Int, element: T) {
    add(this[size - 1])
    for (i in size - 2 downTo index + 1) {
        this[i] = this[i - 1]
    }
    this[index] = element
}

private fun List<SnailfishNumber>.reduceNrs(): List<SnailfishNumber> {
    val nextNrs = toMutableList()
    while (true) {
        if (nextNrs.getMaxDepth() >= 5) {
            for (i in nextNrs.indices) {
                val (value, depth) = nextNrs[i]
                if (depth >= 5) {
                    if (i > 0) {
                        nextNrs[i-1] = SnailfishNumber(nextNrs[i-1].value + value, nextNrs[i-1].depth)
                        if (i < nextNrs.size - 2) {
                            nextNrs[i+2] = SnailfishNumber(nextNrs[i+2].value + nextNrs[i+1].value, nextNrs[i+2].depth)
                        }
                        nextNrs[i+1] = SnailfishNumber(0, depth - 1)
                        nextNrs.removeAt(i)
                    } else {
                        nextNrs[i] = SnailfishNumber(0, depth - 1)
                        nextNrs[i+2] = SnailfishNumber(nextNrs[i+2].value + nextNrs[i+1].value, nextNrs[i+2].depth)
                        nextNrs.removeAt(i + 1)
                    }
                    break
                }
            }
        } else if (nextNrs.getMaxValue() >= 10) {
            for (i in nextNrs.indices) {
                val (value, depth) = nextNrs[i]
                if (value >= 10) {
                    nextNrs.insertAt(i + 1, SnailfishNumber(value - (value / 2), depth + 1))
                    nextNrs[i] = SnailfishNumber(value / 2, depth + 1)
                    break
                }
            }
        } else {
            break
        }
    }
    return nextNrs
}

private fun List<SnailfishNumber>.computeMagnitude(): Long {
    val nrs = toMutableList()
    while (true) {
        val maxDepth = nrs.getMaxDepth()
        if (maxDepth == 0) {
            return nrs.sumOf { it.value }
        }

        for (i in nrs.indices) {
            val (value, depth) = nrs[i]
            if (depth == maxDepth) {
                nrs[i] = SnailfishNumber(value * 3 + nrs[i+1].value * 2, depth - 1)
                nrs.removeAt(i + 1)
                break
            }
        }
    }
}

fun day18a(input: List<String>) =
    input
        .map { line -> line.parseSnailfishNrs().reduceNrs() }
        .reduce { leftNrs, rightNrs ->
            val nextLeftNrs = leftNrs.increaseDepth()
            val nextRightNrs = rightNrs.increaseDepth()
            val nextNrs = nextLeftNrs.plus(nextRightNrs)
            nextNrs.reduceNrs()
        }
        .computeMagnitude()

fun day18b(input: List<String>): Long {
    val nrs = input.map { line -> line.parseSnailfishNrs().reduceNrs() }

    val magnitudes =
        (0 until nrs.size - 1).flatMap { i ->
            (i + 1 until nrs.size).flatMap { i2 ->
                val left = nrs[i].increaseDepth()
                val right = nrs[i2].increaseDepth()
                listOf(
                    left.plus(right).reduceNrs().computeMagnitude(),
                    right.plus(left).reduceNrs().computeMagnitude()
                )
            }
        }

    return magnitudes.maxOrNull()!!
}