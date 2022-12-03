import util.insertAt

private data class SnailfishNumber(var value: Long, var depth: Int)

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

private fun List<SnailfishNumber>.hasToExplode() =
    any { it.depth >= 5 }

private fun List<SnailfishNumber>.hasToSplit() =
    any { it.value >= 10 }

private fun MutableList<SnailfishNumber>.increase(index: Int, amount: Long) {
    if (index in indices) {
        this[index] = SnailfishNumber(this[index].value + amount, this[index].depth)
    }
}

private fun MutableList<SnailfishNumber>.empty(index: Int, depth: Int) {
    this[index] = SnailfishNumber(0, depth)
}

private fun MutableList<SnailfishNumber>.explodeFirst() =
    this.indexOfFirst { it.depth >= 5 }
        .let { this.explode(it) }

private fun MutableList<SnailfishNumber>.explode(index: Int) {
    this.increase(index - 1, this[index].value)
    this.increase(index + 2, this[index + 1].value)
    this.empty(index + 1, this[index].depth - 1)
    this.removeAt(index)
}

private fun MutableList<SnailfishNumber>.splitFirst() =
    this.indexOfFirst { it.value >= 10 }
        .let { this.split(it) }

private fun MutableList<SnailfishNumber>.split(index: Int) {
    val (value, depth) = this[index]

    val halfRoundedDown = value / 2
    val halfRoundedUp   = value - halfRoundedDown

    this[index] = SnailfishNumber(halfRoundedDown, depth + 1)
    this.insertAt(index + 1, SnailfishNumber(halfRoundedUp, depth + 1))
}

private fun MutableList<SnailfishNumber>.magnitudifyFirst(maxDepth: Int) =
    this.indexOfFirst { it.depth == maxDepth }
        .let { this.magnitudify(it) }

private fun MutableList<SnailfishNumber>.magnitudify(index: Int) {
    val (value, depth) = this[index]
    this[index] = SnailfishNumber(value * 3 + this[index + 1].value * 2, depth - 1)
    this.removeAt(index + 1)
}

private fun List<SnailfishNumber>.reduce(): List<SnailfishNumber> {
    val nrs = this.toMutableList()
    while (true) {
        when {
            nrs.hasToExplode() -> nrs.explodeFirst()
            nrs.hasToSplit()   -> nrs.splitFirst()
            else               -> return nrs
        }
    }
}

private fun List<SnailfishNumber>.computeMagnitude(): Long {
    val nrs = toMutableList()
    while (true) {
        val maxDepth = nrs.getMaxDepth()
        if (maxDepth == 0) {
            return nrs.sumOf { it.value }
        }

        nrs.magnitudifyFirst(maxDepth)
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

    return magnitudes.maxOf { it }
}