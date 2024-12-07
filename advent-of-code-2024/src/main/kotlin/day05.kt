import util.toPair

fun day05a(input: List<String>): Int {
    val (orderComparator, updates) = input.parseInput()

    return updates
        .filter { it.sortedWith(orderComparator) == it }
        .sumMiddleElements()
}

fun day05b(input: List<String>): Int {
    val (orderComparator, updates) = input.parseInput()

    return updates
        .filter { it.sortedWith(orderComparator) != it }
        .map { it.sortedWith(orderComparator) }
        .sumMiddleElements()
}

private fun List<String>.parseInput(): Pair<Comparator<Int>,List<List<Int>>> {
    val orderRules =
        this
            .takeWhile { it.isNotEmpty() }
            .map { it.split("|").map(String::toInt).toPair() }
            .toSet()

    val orderComparator = Comparator<Int> { a, b ->
        if (orderRules.contains(a to b)) -1
        else if (orderRules.contains(b to a)) 1
        else 0
    }

    val updates =
        this
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .map { it.split(",").map(String::toInt) }

    return orderComparator to updates
}

private fun List<List<Int>>.sumMiddleElements() =
    sumOf { it[it.size / 2] }