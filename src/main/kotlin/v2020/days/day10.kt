package v2020.days.day10

private fun getJoltDiffs(input: List<String>): List<Int> {
    val adapters = input
        .map(String::toInt)
        .sorted()

    return listOf(0).plus(adapters).plus(adapters.max()!! + 3)
        .zipWithNext { prev, next ->
            next - prev
        }
}

fun day10a(input: List<String>): Int {
    val joltDiffs = getJoltDiffs(input)

    return joltDiffs.count { it == 1 } * joltDiffs.count { it == 3 }
}

fun day10b(input: List<String>): Long {
    val joltDiffs = getJoltDiffs(input)

    return joltDiffs
        .fold(Pair(1L, 0))
            { (comboCount, seqOfOnesLength), joltDiff ->
                if (joltDiff == 1) {
                    comboCount to seqOfOnesLength + 1
                } else {
                    when (seqOfOnesLength) {
                        // Expect input to never produce longer sequences of ones than length 4
                        4 -> comboCount * 7 to 0
                        3 -> comboCount * 4 to 0
                        2 -> comboCount * 2 to 0
                        else -> comboCount to 0
                    }
                }
            }
        .first
}

