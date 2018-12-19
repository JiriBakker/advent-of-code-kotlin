package days.day19

import Opcode
import OperationInput
import opcodeToApplyFuncMapping

private val opnameToOpcodeMapping = mapOf(
    Pair("addr", Opcode.ADDR),
    Pair("addi", Opcode.ADDI),
    Pair("mulr", Opcode.MULR),
    Pair("muli", Opcode.MULI),
    Pair("banr", Opcode.BANR),
    Pair("bani", Opcode.BANI),
    Pair("borr", Opcode.BORR),
    Pair("bori", Opcode.BORI),
    Pair("setr", Opcode.SETR),
    Pair("seti", Opcode.SETI),
    Pair("gtir", Opcode.GTIR),
    Pair("gtri", Opcode.GTRI),
    Pair("gtrr", Opcode.GTRR),
    Pair("eqir", Opcode.EQIR),
    Pair("eqri", Opcode.EQRI),
    Pair("eqrr", Opcode.EQRR)
)

private class Operation(val opcode: Opcode, val input: OperationInput)

private class Registers(private val instructionPointerRegisterIndex: Int, nrOfRegisters: Int) {
    var registers = Array(nrOfRegisters) { 0L }
        private set

    var instructionPointer = 0
        private set

    fun apply(op: Operation, shouldPrint: Boolean = false) {
        val applyFunc = opcodeToApplyFuncMapping[op.opcode]!!

        registers[instructionPointerRegisterIndex] = instructionPointer.toLong()

        if (shouldPrint) print("ip=$instructionPointer [${registers.joinToString(", ")}] ")
        registers = applyFunc(registers, op.input)
        if (shouldPrint) println("${op.opcode} ${op.input.valueA} ${op.input.valueB} ${op.input.valueC} [${registers.joinToString(", ")}]")

        instructionPointer = registers[instructionPointerRegisterIndex].toInt() + 1
    }
}

private fun parse(inputLines: List<String>): Pair<Int, List<Operation>> {
    val instructionPointerRegisterIndex = inputLines[0].split(" ")[1].toInt()

    val operations = mutableListOf<Operation>()
    for (i in 1 until inputLines.size) {
        val (opname, inputA, inputB, inputC) = inputLines[i].split(" ")
        val opcode = opnameToOpcodeMapping[opname]!!
        val operation = Operation(opcode, OperationInput(inputA.toInt(), inputB.toInt(), inputC.toInt()))
        operations.add(operation)
    }

    return Pair(instructionPointerRegisterIndex, operations)
}

fun day19a(inputLines: List<String>): Int {
    val (instructionPointerRegisterIndex, operations) = parse(inputLines)
    val registers = Registers(instructionPointerRegisterIndex, 6)
    while (registers.instructionPointer >= 0 && registers.instructionPointer < operations.size) {
        val op = operations[registers.instructionPointer]
        registers.apply(op)
    }
    return registers.registers[0].toInt()
}

fun day19b(inputLines: List<String>): Long {
    // TODO is there a way to accurately calculate this for ANY input (instead of given input for day19)?

    // Based on given input for Day 19, we notice the following:
    // - Register 2 converges to a value (10551386)
    // - Register 5 increases by 1 every so often (slowly)
    // - Register 3 loops through values 1-10551386 (?)
    // - Operation (7) 'addr 5 0 0' is only applied if the sum of register 5 and register 3 equals register 2
    // - Since register 3 can basically be any number, but register 5 slowly increases, every time operation 7 is called
    //   it will be a factor of 10551386 (= register [5] is a factor of 10551386). Register 0 is then increased
    //   by this value, so will end up being the sum of all factors
    //
    // Approach:
    // - Find converging value for register 2
    // - Sum all factors of that number

    val (instructionPointerRegisterIndex, operations) = parse(inputLines)
    val registers = Registers(instructionPointerRegisterIndex, 6)
    registers.registers[0] = 1
    var nrOfIterationsSinceLastChangeInRegister2 = 0
    var lastValueInRegister2 = 0L
    var iteration = 0
    while (nrOfIterationsSinceLastChangeInRegister2 < 1000000) {
        val op = operations[registers.instructionPointer]
        registers.apply(op)
        if (registers.registers[2] != lastValueInRegister2) {
            lastValueInRegister2 = registers.registers[2]
            nrOfIterationsSinceLastChangeInRegister2 = 0
        }
        iteration++
        nrOfIterationsSinceLastChangeInRegister2++
    }

    var factorSum = 0L
    for (i in 1..10551386) {
        if (10551386 % i == 0) factorSum += i
    }
    return factorSum
}