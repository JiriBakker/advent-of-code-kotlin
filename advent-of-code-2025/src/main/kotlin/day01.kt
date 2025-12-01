fun day01a(input: List<String>) =
    countZeroes(input)

fun day01b(input: List<String>) =
    countZeroes(input, countIntermediate = true)

private fun countZeroes(input: List<String>, countIntermediate: Boolean = false): Int {
    // Not super efficient because we iterate for each tick, but an easier implementation to comprehend
    var zeroCounts = 0
    var dial = 50

    for (instruction in input) {
        val nrOfTicks = instruction.drop(1).toInt()
        val direction = if (instruction[0] == 'R') 1 else -1

        repeat(nrOfTicks) {
            dial += direction

            if (dial < 0) dial += 100
            if (dial > 99) dial -= 100

            if (countIntermediate && dial == 0) zeroCounts++
        }

        if (!countIntermediate && dial == 0) zeroCounts++
    }
    return zeroCounts
}