package days.day19

import Opcode
import OperationInput
import opcodeToApplyFuncMapping

private class Operation(val opcode: Opcode, val input: OperationInput)

private class Registers(private val instructionPointerRegisterIndex: Int, nrOfRegisters: Int) {
    var registers = Array(nrOfRegisters) { 0L }
        private set

    var instructionPointer = 0
        private set

    fun apply(op: Operation) {
        registers[instructionPointerRegisterIndex] = instructionPointer.toLong()

        val applyFunc = opcodeToApplyFuncMapping[op.opcode]!!
        registers = applyFunc(registers, op.input)

        instructionPointer = registers[instructionPointerRegisterIndex].toInt() + 1
    }
}

private fun parse(inputLines: List<String>): Pair<Int, List<Operation>> {
    val instructionPointerRegisterIndex = inputLines[0].split(" ")[1].toInt()

    val operations = mutableListOf<Operation>()
    for (i in 1 until inputLines.size) {
        val (opname, inputA, inputB, inputC) = inputLines[i].split(" ")

        val opcode = when (opname) {
            "addr" -> Opcode.ADDR
            "addi" -> Opcode.ADDI
            "mulr" -> Opcode.MULR
            "muli" -> Opcode.MULI
            "banr" -> Opcode.BANR
            "bani" -> Opcode.BANI
            "borr" -> Opcode.BORR
            "bori" -> Opcode.BORI
            "setr" -> Opcode.SETR
            "seti" -> Opcode.SETI
            "gtir" -> Opcode.GTIR
            "gtri" -> Opcode.GTRI
            "gtrr" -> Opcode.GTRR
            "eqir" -> Opcode.EQIR
            "eqri" -> Opcode.EQRI
            else -> Opcode.EQRR
        }

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
    while (nrOfIterationsSinceLastChangeInRegister2++ < 1000000) {
        registers.apply(operations[registers.instructionPointer])

        if (registers.registers[2] != lastValueInRegister2) {
            lastValueInRegister2 = registers.registers[2]
            nrOfIterationsSinceLastChangeInRegister2 = 0
        }
        iteration++
    }

    var factorSum = 0L
    for (i in 1..lastValueInRegister2) {
        if (lastValueInRegister2 % i == 0L) factorSum += i
    }
    return factorSum
}