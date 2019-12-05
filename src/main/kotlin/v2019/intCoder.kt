package v2019

enum class ParamMode {
    Position,
    Immediate;

    companion object {
        fun from(int: Int): ParamMode {
            return when (int) {
                0 -> Position
                else -> Immediate
            }
        }
    }
}

class Instruction(val opcode: Int, val paramModes: Pair<ParamMode, ParamMode>)

fun parseIntCodes(input: String): List<Int> {
    return input.split(",").map(String::toInt)
}

private fun parseInstruction(value: Int): Instruction {
    val ints = value.toString().map(Character::getNumericValue).reversed()

    val opcode = when (ints.size) {
        1 -> ints[0]
        else -> ints[1] * 10 + ints[0]
    }

    val paramModes = when (ints.size) {
        4 -> Pair(ParamMode.from(ints[2]), ParamMode.from(ints[3]))
        3 -> Pair(ParamMode.from(ints[2]), ParamMode.Position)
        else -> Pair(ParamMode.Position, ParamMode.Position)
    }

    return Instruction(opcode, paramModes)
}

fun runProgram(initialIntCodes: List<Int>, inputValue: Int = 0): Pair<List<Int>, List<Int>> {
    val intCodes = initialIntCodes.toMutableList()

    fun get(pointer: Int, paramMode: ParamMode): Int {
        return when (paramMode) {
            ParamMode.Position -> intCodes[intCodes[pointer]]
            ParamMode.Immediate -> intCodes[pointer]
        }
    }

    fun set(pointer: Int, value: Int) {
        intCodes[intCodes[pointer]] = value
    }

    fun apply4Operation(
        operation: (Int, Int) -> Int,
        pointer: Int,
        paramModes: Pair<ParamMode, ParamMode>
    ) {
        set(pointer + 3,
            operation(
                get(pointer + 1, paramModes.first),
                get(pointer + 2, paramModes.second)
            )
        )
    }

    fun applyCompareOperation(compare: (Int, Int) -> Boolean, pointer: Int, paramModes: Pair<ParamMode, ParamMode>) {
        val firstParam = get(pointer + 1, paramModes.first)
        val secondParam = get(pointer + 2, paramModes.second)

        set(pointer + 3,
            if (compare(firstParam, secondParam)) {
                1
            } else {
                0
            }
        )
    }

    fun applyJump(condition: (Int) -> Boolean, pointer: Int, paramModes: Pair<ParamMode, ParamMode>): Int {
        return if (condition(get(pointer + 1, paramModes.first))) {
            get(pointer + 2, paramModes.second)
        } else {
            pointer + 3
        }
    }

    var pointer = 0
    val output = mutableListOf<Int>()
    var loopNr = 0

    while (true) {
        val instruction = parseInstruction(intCodes[pointer])
        when (instruction.opcode) {
            1 -> {
                apply4Operation(Int::plus, pointer, instruction.paramModes)
                pointer += 4
            }
            2 -> {
                apply4Operation(Int::times, pointer, instruction.paramModes)
                pointer += 4
            }
            3 -> {
                set(pointer + 1, inputValue)
                pointer += 2
            }
            4 -> {
                output.add(get(pointer + 1, instruction.paramModes.first))
                pointer += 2
            }
            5 -> {
                pointer = applyJump({ it != 0 }, pointer, instruction.paramModes)
            }
            6 -> {
                pointer = applyJump({ it == 0 }, pointer, instruction.paramModes)
            }
            7 -> {
                applyCompareOperation({ a, b -> a < b }, pointer, instruction.paramModes)
                pointer += 4
            }
            8 -> {
                applyCompareOperation({ a, b -> a == b }, pointer, instruction.paramModes)
                pointer += 4
            }
            99 -> return Pair(intCodes, output)
            else -> throw Exception("Unknown opcode ${instruction.opcode} from instruction ${intCodes[pointer]} at index $pointer (loop $loopNr)")
        }
        loopNr++
    }
}