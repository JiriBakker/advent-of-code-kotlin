import util.sumOfLong

fun day07a(input: List<String>) =
    input.findCalibrationResult(baseOperators)

fun day07b(input: List<String>) =
    input.findCalibrationResult(baseOperators + ::concatLong)


private fun concatLong(a: Long, b: Long): Long {
    return (a.toString() + b.toString()).toLong()
}

private val baseOperators = listOf<((Long, Long) -> Long)>(
    Long::plus,
    Long::times,
)

private fun List<String>.findCalibrationResult(operators: List<(Long, Long) -> Long>): Long {
    return this.sumOfLong { line ->
        val (testValue, numbersString) = line.split(": ")
        val numbers = numbersString.split(" ").map { it.toLong() }

        if (hasValidOperatorCombination(numbers, 1, numbers[0], testValue.toLong(), operators)) {
            testValue.toLong()
        } else {
            0
        }
    }
}

private fun hasValidOperatorCombination(
    numbers: List<Long>,
    curIndex: Int,
    valueSoFar: Long,
    testValue: Long,
    operators: List<((Long, Long) -> Long)>
): Boolean {
    if (curIndex == numbers.size) {
        return valueSoFar == testValue
    }

    for (op in operators) {
        if (hasValidOperatorCombination(
            numbers,
            curIndex + 1,
            op(valueSoFar, numbers[curIndex]),
            testValue,
            operators
        )) {
            return true
        }
    }

    return false
}

