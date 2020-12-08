package v2020.days.day08

private fun runProgram(input: List<String>): Pair<Boolean, Int> {
    val instructions = input.map { line -> line.split(" ").map { it.trimEnd(',') } }

    val seen = mutableSetOf<Int>()
    var accumulator = 0
    var pointer = 0

    while (true) {
        if (!seen.add(pointer)) {
            return false to accumulator
        }

        if (pointer !in instructions.indices) {
            return true to accumulator
        }

        val instruction = instructions[pointer]
        when (instruction[0]) {
            "nop" -> pointer++
            "acc" -> {
                accumulator += instruction[1].toInt()
                pointer++
            }
            "jmp" -> pointer += instruction[1].toInt()
            else -> error("Unknown instruction")
        }
    }
}

private fun swapNopJmp(instruction: String): String {
    return instruction
        .replace("nop", "tmp")
        .replace("jmp", "nop")
        .replace("tmp", "jmp")
}

fun day08a(input: List<String>): Int {
    return runProgram(input).second
}

fun day08b(input: List<String>): Int {
    input.mapIndexed { index, line ->
        if (line.startsWith("nop") || line.startsWith("jmp")) {
            val inputCopy = input.toMutableList()
            inputCopy[index] = swapNopJmp(inputCopy[index])
            val (terminated, accumulator) = runProgram(inputCopy)
            if (terminated) {
                return accumulator
            }
        }
    }

    error("Program never terminated")
}

