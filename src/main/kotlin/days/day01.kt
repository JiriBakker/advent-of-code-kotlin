package days

fun day01a(changes: List<String>): Int {
    return changes.fold(0) { frequency, change ->
        frequency + change.toInt()
    }
}

fun day01b(changes: List<String>): Int {
    val visited: MutableSet<Int> = mutableSetOf()
    return generateSequence(0) { (it + 1) % changes.count() }
        .fold(0) { frequency, changeIndex ->
            if (visited.contains(frequency))
                return frequency
            visited.add(frequency)
            frequency + changes[changeIndex].toInt()
        }
}
