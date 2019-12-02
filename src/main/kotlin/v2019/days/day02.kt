package v2019.days.day02

private fun parseIntCodes(input: String): List<Int> {
    return input.split(",").map(String::toInt)
}

private fun runProgram(initialIntCodes: List<Int>, overrides: List<Pair<Int, Int>> = listOf()): Int {
    val intCodes = initialIntCodes.toMutableList()
    overrides.forEach { intCodes[it.first] = it.second }

    val updateIntCodes = { operation: (Int, Int) -> Int, pointer: Int -> intCodes[intCodes[pointer + 3]] = operation(intCodes[intCodes[pointer + 1]], intCodes[intCodes[pointer + 2]]) }

    var pointer = 0

    loop@ while (true) {
        when (intCodes[pointer]) {
            1 -> updateIntCodes(Int::plus, pointer)
            2 -> updateIntCodes(Int::times, pointer)
            99 -> break@loop
            else -> throw Exception("Unknown opCode")
        }
        pointer += 4
    }

    return intCodes.first()
}

fun day02a(input: String, overrides: List<Pair<Int, Int>> = listOf()): Int {
    val intCodes = parseIntCodes(input)
    return runProgram(intCodes, overrides)
}

fun day02b(input: String): Int {
    val intCodes = parseIntCodes(input)

    for (i1 in 0..99)
        for (i2 in 0..99) {
            if (runProgram(intCodes.toList(), listOf(Pair(1, i1), Pair(2, i2))) == 19690720) {
                return 100 * i1 + i2
            }
        }

    throw Exception("No answer found")
}
