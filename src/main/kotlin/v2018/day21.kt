package v2018

import v2018.Registers
import v2018.parseOperations

fun day21a(inputLines: List<String>): Long {
    val (instructionPointerRegisterIndex, operations) = parseOperations(inputLines)

    val registers = Registers(instructionPointerRegisterIndex, 6)

    while (true) {
        registers.apply(operations[registers.instructionPointer])
        if (registers.instructionPointer == 28) { // TODO will this work for all possible (expected) inputs?
            return registers.registers[4]
        }
    }
}

fun day21b(): Long {
    val nrsFound = mutableSetOf<Long>()
    var nr = 0L

    while (true) {
        // Reverse engineered implementation of the instructions in the input file

        var reg1 = nr or 65536L // Line 6
        nr = 678134L // Line 7

        while (true) { // Loop from lines 8-27
            nr += reg1 % 256L // Line 8-9
            nr %= 16777216L // Line 10
            nr *= 65899L // Line 11
            nr %= 16777216L // Line 12
            if (reg1 < 256) { // Line 13-15
                break // Line 16
            }
            reg1 /= 256L // Equivalent to loop from lines 18-25
        }

        if (nrsFound.contains(nr)) {
            return nrsFound.last()
        }
        nrsFound.add(nr)
    }
}