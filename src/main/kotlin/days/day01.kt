package days

fun day01a(changes: List<String>): Int {
    return changes.sumBy { it.toInt() }
}

fun day01b(changes: List<String>): Int {
    val visited: MutableSet<Int> = mutableSetOf()
    val changesInfiniteSequence = generateSequence(0) { (it + 1) % changes.count() }.map { changes[it] }
    return changesInfiniteSequence.fold(0) { frequency, change ->
        if (visited.contains(frequency))
            return frequency
        visited.add(frequency)
        frequency + change.toInt()
    }
}
