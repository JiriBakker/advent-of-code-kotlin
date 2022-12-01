package v2017

private fun countSteps(initialJumps: List<Int>, computeDelta: (Int) -> Int): Int {
    val jumps = initialJumps.toMutableList()

    var index = 0
    var steps = 0
    while (index in jumps.indices) {
        val prevVal = jumps[index]
        jumps[index] = computeDelta(prevVal)
        index += prevVal
        steps++
    }

    return steps
}

fun day05a(input: List<String>): Int {
    val jumps = input.map { it.toInt() }
    return countSteps(jumps) { it + 1 }
}

fun day05b(input: List<String>): Int {
    val jumps = input.map { it.toInt() }
    return countSteps(jumps) { it + if (it >= 3) -1 else 1 }
}
