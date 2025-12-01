fun day01a(input: List<String>): Int {
    var zeroCounts = 0
    var dial = 50
    for (rotation in input) {
        val sign = if (rotation.startsWith("R")) 1 else -1
        dial += sign * rotation.drop(1).toInt()
        while (dial !in 0..99) {
            dial = (dial + 100) % 100
        }
        if (dial == 0) zeroCounts++
    }
    return zeroCounts
}

fun day01b(input: List<String>): Int {
    var zeroCounts = 0
    var dial = 50
    for (instruction in input) {
        var rotation = instruction.drop(1).toInt()
        val sign = if (instruction.startsWith("R")) 1 else -1

        while (rotation > 0) {
            dial += sign
            if (dial == -1) dial = 99
            if (dial == 100) dial = 0
            if (dial == 0) zeroCounts++

            rotation -= 1
        }
    }
    return zeroCounts
}