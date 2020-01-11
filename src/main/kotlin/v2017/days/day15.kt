package v2017.days.day15

private fun countMatches(initialA: Long, initialB: Long, nrOfPairsToCheck: Int, multipleFactorA: Long, multipleFactorB: Long): Int {
    val mask16 = 65535L
    val div = 2147483647L

    fun generateNumbers(initial: Long, factor: Long, multipleFactor: Long): Sequence<Long> {
        return sequence {
            var value = initial
            while (true) {
                value = (value * factor) % div
                if (value % multipleFactor == 0L) {
                    yield(value)
                }
            }
        }
    }

    val seqA = generateNumbers(initialA, 16807, multipleFactorA)
    val seqB = generateNumbers(initialB, 48271, multipleFactorB)

    val nrs = seqA.zip(seqB).iterator()
    return (1 .. nrOfPairsToCheck).count {
        val (a, b) = nrs.next()
        a.and(mask16) == b.and(mask16)
    }
}

fun day15a(input: List<String>, nrOfPairsToCheck: Int = 40000000): Int {
    val (initialA, initialB) = input.map { it.split(' ').last().toLong() }
    return countMatches(initialA, initialB, nrOfPairsToCheck, 1, 1)
}

fun day15b(input: List<String>, nrOfPairsToCheck: Int = 5000000): Int {
    val (initialA, initialB) = input.map { it.split(' ').last().toLong() }
    return countMatches(initialA, initialB, nrOfPairsToCheck, 4, 8)
}
