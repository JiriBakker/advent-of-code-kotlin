package days.day16

private enum class Opcode {
    ADDR, // (add register) stores into register C the result of adding register A and register B.
    ADDI, // (add immediate) stores into register C the result of adding register A and value B.
    MULR, // (multiply register) stores into register C the result of multiplying register A and register B.
    MULI, // (multiply immediate) stores into register C the result of multiplying register A and value B.
    BANR, // (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
    BANI, // (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
    BORR, // (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
    BORI, // (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
    SETR, // (set register) copies the contents of register A into register C. (Input B is ignored.)
    SETI, // (set immediate) stores value A into register C. (Input B is ignored.)
    GTIR, // (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
    GTRI, // (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
    GTRR, // (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
    EQIR, // (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
    EQRI, // (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
    EQRR // (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
}

private val opcodeToApplyFuncMapping = mutableMapOf(
    Pair(Opcode.ADDR, ::applyAddr),
    Pair(Opcode.ADDI, ::applyAddi),
    Pair(Opcode.MULR, ::applyMulr),
    Pair(Opcode.MULI, ::applyMuli),
    Pair(Opcode.BANR, ::applyBanr),
    Pair(Opcode.BANI, ::applyBani),
    Pair(Opcode.BORR, ::applyBorr),
    Pair(Opcode.BORI, ::applyBori),
    Pair(Opcode.SETR, ::applySetr),
    Pair(Opcode.SETI, ::applySeti),
    Pair(Opcode.GTIR, ::applyGtir),
    Pair(Opcode.GTRI, ::applyGtri),
    Pair(Opcode.GTRR, ::applyGtrr),
    Pair(Opcode.EQIR, ::applyEqir),
    Pair(Opcode.EQRI, ::applyEqri),
    Pair(Opcode.EQRR, ::applyEqrr)
)

private class Operation(val opcodeNr: Int, val valueA: Int, val valueB: Int, val valueC: Int)

private class Sample(val op: Operation, val before: Array<Int>, val after: Array<Int>)

private class Registers {
    var registers = Array(4) { 0 }
        private set

    fun apply(opcode: Opcode, op: Operation) {
        val applyFunc = opcodeToApplyFuncMapping[opcode]!!
        registers = applyFunc(registers, op)
    }
}

private fun parse(inputLines: List<String>): Pair<List<Sample>, List<Operation>> {
    val samples = mutableListOf<Sample>()

    fun parseOperation(inputLine: String): Operation {
        val (opcode, inputA, inputB, inputC) = inputLine.split(" ").map(String::toInt)
        return Operation(opcode, inputA, inputB, inputC)
    }

    var i = 0
    while (i < inputLines.size) {
        if (inputLines[i].isBlank()) {
            break
        }

        val registersBefore = inputLines[i].removePrefix("Before: [").removeSuffix("]").split(", ").map(String::toInt).toTypedArray()
        val op = parseOperation(inputLines[i + 1])
        val registersAfter = inputLines[i + 2].removePrefix("After:  [").removeSuffix("]").split(", ").map(String::toInt).toTypedArray()

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

private fun applyOperatorR(registers: Array<Int>, op: Operation, operator: (Int, Int) -> Int): Array<Int> {
    val registerA = registers[op.valueA]
    val registerB = registers[op.valueB]
    registers[op.valueC] = operator(registerA, registerB)
    return registers
}

private fun applyOperatorI(registers: Array<Int>, op: Operation, operator: (Int, Int) -> Int): Array<Int> {
    val registerA = registers[op.valueA]
    registers[op.valueC] = operator(registerA, op.valueB)
    return registers
}

private fun applyCompareIR(registers: Array<Int>, op: Operation, comparator: (Int, Int) -> Boolean): Array<Int> {
    val registerB = registers[op.valueB]
    registers[op.valueC] = if (comparator(op.valueA, registerB)) 1 else 0
    return registers
}

private fun applyCompareRI(registers: Array<Int>, op: Operation, comparator: (Int, Int) -> Boolean): Array<Int> {
    val registerA = registers[op.valueA]
    registers[op.valueC] = if (comparator(registerA, op.valueB)) 1 else 0
    return registers
}

private fun applyCompareRR(registers: Array<Int>, op: Operation, comparator: (Int, Int) -> Boolean): Array<Int> {
    val registerA = registers[op.valueA]
    val registerB = registers[op.valueB]
    registers[op.valueC] = if (comparator(registerA, registerB)) 1 else 0
    return registers
}

private fun applyAddr(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorR(registers, op, Int::plus)
}

private fun applyAddi(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorI(registers, op, Int::plus)
}

private fun applyMulr(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorR(registers, op, Int::times)
}

private fun applyMuli(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorI(registers, op, Int::times)
}

private fun applyBanr(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorR(registers, op, Int::and)
}

private fun applyBani(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorI(registers, op, Int::and)
}

private fun applyBorr(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorR(registers, op, Int::or)
}

private fun applyBori(registers: Array<Int>, op: Operation): Array<Int> {
    return applyOperatorI(registers, op, Int::or)
}

private fun applySetr(registers: Array<Int>, op: Operation): Array<Int> {
    val registerA = registers[op.valueA]
    registers[op.valueC] = registerA
    return registers
}

private fun applySeti(registers: Array<Int>, op: Operation): Array<Int> {
    registers[op.valueC] = op.valueA
    return registers
}

private fun applyGtir(registers: Array<Int>, op: Operation): Array<Int> {
    return applyCompareIR(registers, op) { a, b -> a > b }
}

private fun applyGtri(registers: Array<Int>, op: Operation): Array<Int> {
    return applyCompareRI(registers, op) { a, b -> a > b }
}

private fun applyGtrr(registers: Array<Int>, op: Operation): Array<Int> {
    return applyCompareRR(registers, op) { a, b -> a > b }
}

private fun applyEqir(registers: Array<Int>, op: Operation): Array<Int> {
    return applyCompareIR(registers, op, Int::equals)
}

private fun applyEqri(registers: Array<Int>, op: Operation): Array<Int> {
    return applyCompareRI(registers, op, Int::equals)
}

private fun applyEqrr(registers: Array<Int>, op: Operation): Array<Int> {
    return applyCompareRR(registers, op, Int::equals)
}

private fun findPossibleOpcodes(sample: Sample): List<Opcode> {
    val possibleOpcodes = mutableListOf<Opcode>()
    opcodeToApplyFuncMapping.forEach { (opcode, applyFunc) ->
        if (applyFunc(sample.before.copyOf(), sample.op) contentEquals sample.after) {
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

fun day16b(inputLines: List<String>): Int {
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