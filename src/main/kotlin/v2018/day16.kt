package v2018

import v2018.Day16.Operation
import v2018.Day16.Registers

private object Day16 {

    class Operation(val opcodeNr: Int, val input: OperationInput)

    class Registers {
        var registers = Array(4) { 0L }
            private set

        fun apply(opcode: Opcode, op: Operation) {
            val applyFunc = opcodeToApplyFuncMapping[opcode]!!
            registers = applyFunc(registers, op.input)
        }
    }
}

private class Sample(val op: Operation, val before: Array<Long>, val after: Array<Long>)

private fun parse(inputLines: List<String>): Pair<List<Sample>, List<Operation>> {
    val samples = mutableListOf<Sample>()

    fun parseOperation(inputLine: String): Operation {
        val (opcodeNr, inputA, inputB, inputC) = inputLine.split(" ").map(String::toInt)
        return Operation(opcodeNr, OperationInput(inputA, inputB, inputC))
    }

    var i = 0
    while (i < inputLines.size) {
        if (inputLines[i].isBlank()) {
            break
        }

        val registersBefore = inputLines[i].removePrefix("Before: [").removeSuffix("]").split(", ").map(String::toLong).toTypedArray()
        val op = parseOperation(inputLines[i + 1])
        val registersAfter = inputLines[i + 2].removePrefix("After:  [").removeSuffix("]").split(", ").map(String::toLong).toTypedArray()

        samples.add(Sample(op, registersBefore, registersAfter))

        i += 4
    }

    val operations = mutableListOf<Operation>()
    i += 2
    while (i < inputLines.size) {
        operations.add(parseOperation(inputLines[i]))
        i++
    }

    return Pair(samples, operations)
}

private fun findPossibleOpcodes(sample: Sample): List<Opcode> {
    val possibleOpcodes = mutableListOf<Opcode>()
    opcodeToApplyFuncMapping.forEach { (opcode, applyFunc) ->
        if (applyFunc(sample.before.copyOf(), sample.op.input) contentEquals sample.after) {
            possibleOpcodes.add(opcode)
        }
    }
    return possibleOpcodes
}

fun day16a(inputLines: List<String>): Int {
    val (samples, _) = parse(inputLines)
    var unclearSampleCount = 0

    for (sample in samples) {
        val possibleOpcodes = findPossibleOpcodes(sample)
        if (possibleOpcodes.size >= 3) {
            unclearSampleCount++
        }
    }

    return unclearSampleCount
}

fun day16b(inputLines: List<String>): Long {
    val (samples, operations) = parse(inputLines)
    val opcodePossibilities = Array(16) { Opcode.values().toList() }

    fun updateOpcodePossibilities(opcodeNr: Int) {
        if (opcodePossibilities[opcodeNr].size == 1) {
            val opcode = opcodePossibilities[opcodeNr].single()

            opcodePossibilities
                .forEachIndexed { otherOpcodeNr, opcodes ->
                    if (otherOpcodeNr != opcodeNr && opcodes.contains(opcode)) {
                        opcodePossibilities[otherOpcodeNr] = opcodePossibilities[otherOpcodeNr].filter { it != opcode }
                        updateOpcodePossibilities(otherOpcodeNr)
                    }
                }
        }
    }

    for (sample in samples) {
        val possibleOpcodes = findPossibleOpcodes(sample)
        opcodePossibilities[sample.op.opcodeNr] = opcodePossibilities[sample.op.opcodeNr].intersect(possibleOpcodes).toList()
    }

    for (opcodeNr in 0..15) {
        updateOpcodePossibilities(opcodeNr)
    }

    val registers = Registers()
    operations.forEach {
        registers.apply(opcodePossibilities[it.opcodeNr].single(), it)
    }
    return registers.registers[0]
}