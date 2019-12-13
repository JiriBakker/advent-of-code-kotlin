package v2019.intCoder

import java.util.ArrayDeque

private enum class ParamMode {
    Position,
    Immediate,
    Relative
}

private class Instruction(val opcode: Int, val modes: List<ParamMode>)

fun parseIntCodes(input: String): MutableMap<Long, Long> {
    return input.split(",").mapIndexed { index, nr -> index.toLong() to nr.toLong() }.toMap().toMutableMap()
}

private fun parseInstruction(value: Long): Instruction {
    val opcode = (value % 100).toInt()

    val modes =
        value.toString()
            .dropLast(2)
            .padStart(3, '0')
            .map { when (it) {
                '0'  -> ParamMode.Position
                '1'  -> ParamMode.Immediate
                else -> ParamMode.Relative
            } }
            .reversed()

    return Instruction(opcode, modes)
}

fun generateProgramOutput(intCodes: MutableMap<Long, Long>, inputProvider: () -> Long = { 0L }): Sequence<Long> {
    return sequence {
        var relativeBase = 0L

        fun readOrSet(pointer: Long): Long {
            return intCodes.getOrPut(pointer, { 0L })
        }

        fun get(pointer: Long, mode: ParamMode): Long {
            return when (mode) {
                ParamMode.Position -> readOrSet(readOrSet(pointer))
                ParamMode.Immediate -> readOrSet(pointer)
                ParamMode.Relative -> readOrSet(relativeBase + readOrSet(pointer))
            }
        }

        fun set(pointer: Long, value: Long, mode: ParamMode) {
            when (mode) {
                ParamMode.Position -> intCodes[readOrSet(pointer)] = value
                ParamMode.Immediate -> error("Set in Immediate mode not allowed")
                ParamMode.Relative -> intCodes[relativeBase + readOrSet(pointer)] = value
            }
        }

        fun applyModification(operation: (Long, Long) -> Long, pointer: Long, modes: List<ParamMode>) {
            set(
                pointer + 3,
                operation(
                    get(pointer + 1, modes[0]),
                    get(pointer + 2, modes[1])
                ),
                modes[2]
            )
        }

        fun applyCompare(compare: (Long, Long) -> Boolean, pointer: Long, modes: List<ParamMode>) {
            val firstParam = get(pointer + 1, modes[0])
            val secondParam = get(pointer + 2, modes[1])

            set(pointer + 3, if (compare(firstParam, secondParam)) 1 else 0, modes[2])
        }

        fun applyJump(condition: (Long) -> Boolean, pointer: Long, modes: List<ParamMode>): Long {
            return if (condition(get(pointer + 1, modes[0]))) {
                get(pointer + 2, modes[1])
            } else {
                pointer + 3
            }
        }

        var pointer = 0L

        while (true) {
            val instruction = parseInstruction(readOrSet(pointer))
            when (instruction.opcode) {
                1 -> {
                    applyModification(Long::plus, pointer, instruction.modes)
                    pointer += 4
                }
                2 -> {
                    applyModification(Long::times, pointer, instruction.modes)
                    pointer += 4
                }
                3 -> {
                    set(pointer + 1, inputProvider(), instruction.modes[0])
                    pointer += 2
                }
                4 -> {
                    yield(get(pointer + 1, instruction.modes[0]))
                    pointer += 2
                }
                5 -> {
                    pointer = applyJump({ it != 0L }, pointer, instruction.modes)
                }
                6 -> {
                    pointer = applyJump({ it == 0L }, pointer, instruction.modes)
                }
                7 -> {
                    applyCompare({ a, b -> a < b }, pointer, instruction.modes)
                    pointer += 4
                }
                8 -> {
                    applyCompare({ a, b -> a == b }, pointer, instruction.modes)
                    pointer += 4
                }
                9 -> {
                    relativeBase += get(pointer + 1, instruction.modes[0])
                    pointer += 2
                }
                99 -> return@sequence
                else -> throw Exception("Unknown opcode ${instruction.opcode} from instruction ${intCodes[pointer]} at index $pointer")
            }
        }
    }
}