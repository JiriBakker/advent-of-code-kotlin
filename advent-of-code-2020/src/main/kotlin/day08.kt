private fun runProgram(input: List<String>, allowSwap: Boolean = false): Pair<Boolean, Int> {
    val instructions = input.map { line -> line.split(" ") }
    val seenUnswappedPointers = mutableSetOf<Int>()
    val seenSwappedPointers = mutableSetOf<Int>()

    val toCheck = ArrayDeque<Triple<Int, Int, Boolean>>()
    toCheck.add(Triple(0, 0, !allowSwap))

    while (toCheck.isNotEmpty()) {
        val (pointer, accumulator, hasSwapped) = toCheck.removeFirst()

        if ((!hasSwapped && !seenUnswappedPointers.add(pointer)) || (hasSwapped && !seenSwappedPointers.add(pointer))) {
            if (allowSwap) {
                continue
            }
            return false to accumulator
        }

        if (pointer !in instructions.indices) {
            return true to accumulator
        }

        val instruction = instructions[pointer]
        when (instruction[0]) {
            "nop" -> {
                if (allowSwap && !hasSwapped) {
                    toCheck.addLast(Triple(pointer + instruction[1].toInt(), accumulator, true))
                }
                toCheck.addLast(Triple(pointer + 1, accumulator, hasSwapped))
            }
            "jmp" -> {
                if (allowSwap && !hasSwapped) {
                    toCheck.addLast(Triple(pointer + 1, accumulator, true))
                }
                toCheck.addLast(Triple(pointer + instruction[1].toInt(), accumulator, hasSwapped))
            }
            "acc" -> {
                toCheck.addLast(Triple(pointer + 1, accumulator + instruction[1].toInt(), hasSwapped))
            }
            else -> error("Unknown instruction")
        }
    }

    error("Program never terminated")
}


fun day08a(input: List<String>): Int {
    return runProgram(input).second
}

fun day08b(input: List<String>): Int {
    val (terminated, accumulator) = runProgram(input, allowSwap = true)
    if (terminated) {
        return accumulator
    }

    error("Program never terminated")
}


