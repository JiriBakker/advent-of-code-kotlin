package v2019.intCoder

private enum class ParamMode {
    Position,
    Immediate
}

private class Instruction(val opcode: Int, val modes: List<ParamMode>)

fun parseIntCodes(input: String): List<Int> {
    return input.split(",").map(String::toInt)
}

private fun parseInstruction(value: Int): Instruction {
    val opcode = value % 100

    val modes =
        value.toString()
            .dropLast(2)
            .padStart(2, '0')
            .map { if (it == '0') ParamMode.Position else ParamMode.Immediate }
            .reversed()

    return Instruction(opcode, modes)
}

fun runProgram(initialIntCodes: List<Int>, inputValue: Int = 0): Pair<List<Int>, List<Int>> {
    val intCodes = initialIntCodes.toMutableList()

    fun get(pointer: Int, mode: ParamMode): Int {
        return when (mode) {
            ParamMode.Position -> intCodes[intCodes[pointer]]
            ParamMode.Immediate -> intCodes[pointer]
        }
    }

    fun set(pointer: Int, value: Int) {
        intCodes[intCodes[pointer]] = value
    }

    fun applyModification(operation: (Int, Int) -> Int, pointer: Int, modes: List<ParamMode>) {
        set(pointer + 3,
            operation(
                get(pointer + 1, modes[0]),
                get(pointer + 2, modes[1])
            )
        )
    }

    fun applyCompare(compare: (Int, Int) -> Boolean, pointer: Int, modes: List<ParamMode>) {
        val firstParam = get(pointer + 1, modes[0])
        val secondParam = get(pointer + 2, modes[1])

        set(pointer + 3, if (compare(firstParam, secondParam)) 1 else 0)
    }

    fun applyJump(condition: (Int) -> Boolean, pointer: Int, modes: List<ParamMode>): Int {
        return if (condition(get(pointer + 1, modes[0]))) {
            get(pointer + 2, modes[1])
        } else {
            pointer + 3
        }
    }

    var pointer = 0
    val output = mutableListOf<Int>()

    while (true) {
        val instruction = parseInstruction(intCodes[pointer])
        when (instruction.opcode) {
            1 -> {
                applyModification(Int::plus, pointer, instruction.modes)
                pointer += 4
            }
            2 -> {
                applyModification(Int::times, pointer, instruction.modes)
                pointer += 4
            }
            3 -> {
                set(pointer + 1, inputValue)
                pointer += 2
            }
            4 -> {
                output.add(get(pointer + 1, instruction.modes[0]))
                pointer += 2
            }
            5 -> {
                pointer = applyJump({ it != 0 }, pointer, instruction.modes)
            }
            6 -> {
                pointer = applyJump({ it == 0 }, pointer, instruction.modes)
            }
            7 -> {
                applyCompare({ a, b -> a < b }, pointer, instruction.modes)
                pointer += 4
            }
            8 -> {
                applyCompare({ a, b -> a == b }, pointer, instruction.modes)
                pointer += 4
            }
            99 -> return Pair(intCodes, output)
            else -> throw Exception("Unknown opcode ${instruction.opcode} from instruction ${intCodes[pointer]} at index $pointer")
        }
    }
}