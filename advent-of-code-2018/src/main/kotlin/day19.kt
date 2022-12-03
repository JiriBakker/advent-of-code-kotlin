import Registers
import parseOperations

fun day19a(inputLines: List<String>): Int {
    val (instructionPointerRegisterIndex, operations) = parseOperations(inputLines)
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
    // - v2018.Operation (7) 'addr 5 0 0' is only applied if the sum of register 5 and register 3 equals register 2
    // - Since register 3 can basically be any number, but register 5 slowly increases, every time operation 7 is called
    //   it will be a factor of 10551386 (= register [5] is a factor of 10551386). Register 0 is then increased
    //   by this value, so will end up being the sum of all factors
    //
    // Approach:
    // - Find converging value for register 2
    // - Sum all factors of that number

    val (instructionPointerRegisterIndex, operations) = parseOperations(inputLines)

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