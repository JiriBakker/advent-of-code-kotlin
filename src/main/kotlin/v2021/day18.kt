package v2021

private data class SnailfishNumber(val value: Long, val depth: Int)

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

private fun MutableList<SnailfishNumber>.explode(index: Int) {
    val (value, depth) = this[index]
    if (index > 0) {
        this[index - 1] = SnailfishNumber(this[index - 1].value + value, this[index - 1].depth)
    }
    if (index < size - 2) {
        this[index + 2] = SnailfishNumber(this[index + 2].value + this[index + 1].value, this[index + 2].depth)
    }
    this[index + 1] = SnailfishNumber(0, depth - 1)
    this.removeAt(index)
}

private fun MutableList<SnailfishNumber>.split(index: Int) {
    val (value, depth) = this[index]

    val halfRoundedDown = value / 2
    val halfRoundedUp = value - halfRoundedDown

    this.insertAt(index + 1, SnailfishNumber(halfRoundedUp, depth + 1))
    this[index] = SnailfishNumber(halfRoundedDown, depth + 1)
}

private fun List<SnailfishNumber>.reduce(): List<SnailfishNumber> {
    val nextNrs = this.toMutableList()
    while (true) {
        if (nextNrs.getMaxDepth() >= 5) {
            for (i in nextNrs.indices) {
                if (nextNrs[i].depth >= 5) {
                    nextNrs.explode(i)
                    break
                }
            }
        } else if (nextNrs.getMaxValue() >= 10) {
            for (i in nextNrs.indices) {
                if (nextNrs[i].value >= 10) {
                    nextNrs.split(i)
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
        .map { line -> line.parseSnailfishNrs().reduce() }
        .reduce { leftNrs, rightNrs ->
            val nextLeftNrs = leftNrs.increaseDepth()
            val nextRightNrs = rightNrs.increaseDepth()
            val nextNrs = nextLeftNrs.plus(nextRightNrs)
            nextNrs.reduce()
        }
        .computeMagnitude()

fun day18b(input: List<String>): Long {
    val nrs = input.map { line -> line.parseSnailfishNrs().reduce() }

    val magnitudes =
        (0 until nrs.size - 1).flatMap { i ->
            (i + 1 until nrs.size).flatMap { i2 ->
                val left  = nrs[i].increaseDepth()
                val right = nrs[i2].increaseDepth()
                listOf(
                    left.plus(right).reduce().computeMagnitude(),
                    right.plus(left).reduce().computeMagnitude()
                )
            }
        }

    return magnitudes.maxOrNull()!!
}