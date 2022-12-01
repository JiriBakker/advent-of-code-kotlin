package v2017

import java.util.ArrayDeque
import java.util.Stack

private fun runProgramA(input: List<String>): Sequence<Long> {
    val registers = mutableMapOf<String, Long>()

    fun getValue(registerOrNumber: String): Long {
        return registerOrNumber.toLongOrNull() ?: registers.getOrPut(registerOrNumber, { 0 })
    }

    return sequence {
        var index = 0
        val sentFrequencies = Stack<Long>()
        while (index in input.indices) {
            val segments = input[index].split(' ')
            when (segments[0]) {
                "snd" -> sentFrequencies.push(getValue(segments[1]))
                "set" -> registers[segments[1]] = getValue(segments[2])
                "add" -> registers[segments[1]] = getValue(segments[1]) + getValue(segments[2])
                "mul" -> registers[segments[1]] = getValue(segments[1]) * getValue(segments[2])
                "mod" -> registers[segments[1]] = getValue(segments[1]) % getValue(segments[2])
                "rcv" -> if (getValue(segments[1]) != 0L) yield(sentFrequencies.pop())
                "jgz" -> if (getValue(segments[1]) > 0L) index += getValue(segments[2]).toInt() - 1
                else -> error("Unknown instruction: ${input[index]}")
            }
            index++
        }
    }
}

fun day18a(input: List<String>): Long {
    return runProgramA(input).first()
}

private fun runProgramB(registerPValue: Long, instructions: List<String>, receiveQueue: ArrayDeque<Long>): Sequence<Long?> {
    val registers = mutableMapOf<String, Long>()
    registers["p"] = registerPValue

    fun getValue(registerOrNumber: String): Long {
        return registerOrNumber.toLongOrNull() ?: registers.getOrPut(registerOrNumber, { 0 })
    }

    return sequence {
        var index = 0
        while (index in instructions.indices) {
            val segments = instructions[index].split(' ')
            when (segments[0]) {
                "snd" -> yield(getValue(segments[1]))
                "set" -> registers[segments[1]] = getValue(segments[2])
                "add" -> registers[segments[1]] = getValue(segments[1]) + getValue(segments[2])
                "mul" -> registers[segments[1]] = getValue(segments[1]) * getValue(segments[2])
                "mod" -> registers[segments[1]] = getValue(segments[1]) % getValue(segments[2])
                "rcv" -> {
                    while (receiveQueue.isEmpty()) {
                        yield(null)
                    }
                    registers[segments[1]] = receiveQueue.pop()
                }
                "jgz" -> if (getValue(segments[1]) > 0L) index += getValue(segments[2]).toInt() - 1
                else -> error("Unknown instruction: ${instructions[index]}")
            }
            index++
        }
    }
}

fun day18b(input: List<String>): Int {
    var program1SentCount = 0

    val program0SentFrequencies = ArrayDeque<Long>()
    val program1SentFrequencies = ArrayDeque<Long>()

    val program0 = runProgramB(0, input, program1SentFrequencies).iterator()
    val program1 = runProgramB(1, input, program0SentFrequencies).iterator()

    do {
        while (program0.hasNext()) {
            program0SentFrequencies.add(program0.next() ?: break)
        }

        while (program1.hasNext()) {
            program1SentFrequencies.add(program1.next() ?: break)
            program1SentCount++
        }
    } while (program0SentFrequencies.isNotEmpty() || program1SentFrequencies.isNotEmpty())

    return program1SentCount
}
