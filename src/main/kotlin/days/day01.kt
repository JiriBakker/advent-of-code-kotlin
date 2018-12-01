package days

fun day01a(changes: List<String>): Int {
    return changes.fold(0) { frequency, change ->
        frequency + change.toInt()
    }
}

fun day01b(changes: List<String>): Int {
    val visited: MutableSet<Int> = mutableSetOf()
    var currentIndex = 0;
    var currentFrequency = 0;
    while (true) {
        if (visited.contains(currentFrequency))
            return currentFrequency
        visited.add(currentFrequency)
        currentFrequency += changes[currentIndex].toInt()
        currentIndex = (currentIndex + 1) % changes.count()
    }
}
