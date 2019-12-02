package v2019.days.day02

private fun parseIntCodes(input: String): List<Int> {
    return input.split(",").map(String::toInt)
}

private fun runProgram(initialIntCodes: List<Int>, overrides: List<Pair<Int, Int>> = listOf()): Int {
    val intCodes = initialIntCodes.toMutableList()
    overrides.forEach { intCodes[it.first] = it.second }

    val updateIntCodes = { operation: (Int, Int) -> Int, pointer: Int ->
        intCodes[intCodes[pointer + 3]] = operation(intCodes[intCodes[pointer + 1]], intCodes[intCodes[pointer + 2]])
    }

    var pointer = 0

    while (true) {
        when (intCodes[pointer]) {
            1 -> updateIntCodes(Int::plus, pointer)
            2 -> updateIntCodes(Int::times, pointer)
            99 -> return intCodes.first()
            else -> throw Exception("Unknown opCode")
        }
        pointer += 4
    }
}

fun day02a(input: String, overrides: List<Pair<Int, Int>> = listOf()): Int {
    val intCodes = parseIntCodes(input)
    return runProgram(intCodes, overrides)
}

private class SearchRange(var min: Int, var max: Int) {
    fun median(): Int {
        return ((max - min) / 2) + min
    }
    fun consolidateMin() {
        min = median() + 1
    }
    fun consolidateMax() {
        max = median() - 1
    }
}

fun day02b_binarySearch(input: String): Int {
    val target = 19690720

    val intCodes = parseIntCodes(input)

    val range1 = SearchRange(0, 99)
    val range2 = SearchRange(0, 99)

    fun findOptimal(range: SearchRange) {
        while (range.max >= range.min) {
            val result = runProgram(intCodes.toList(), listOf(Pair(1, range1.median()), Pair(2, range2.median())))

            when {
                result == target -> return
                result > target  -> range.consolidateMax()
                else             -> range.consolidateMin()
            }
        }
    }

    findOptimal(range1)
    findOptimal(range2)

    return 100 * range1.median() + range2.median()
}

fun day02b_bruteForce(input: String): Int {
    val intCodes = parseIntCodes(input)

    for (i1 in 0..99) {
        for (i2 in 0..99) {
            if (runProgram(intCodes.toList(), listOf(Pair(1, i1), Pair(2, i2))) == 19690720) {
                return 100 * i1 + i2
            }
        }
    }

    throw Exception("No answer found")
}
