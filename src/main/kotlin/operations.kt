enum class Opcode {
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

val opcodeToApplyFuncMapping = mapOf(
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

class OperationInput(val valueA: Int, val valueB: Int, val valueC: Int)

class Operation(val opcode: Opcode, val input: OperationInput)

class Registers(private val instructionPointerRegisterIndex: Int, nrOfRegisters: Int) {
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

fun parseOperations(inputLines: List<String>): Pair<Int, List<Operation>> {
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

private fun applyOperatorR(registers: Array<Long>, input: OperationInput, operator: (Long, Long) -> Long): Array<Long> {
    val registerA = registers[input.valueA]
    val registerB = registers[input.valueB]
    registers[input.valueC] = operator(registerA, registerB)
    return registers
}

private fun applyOperatorI(registers: Array<Long>, input: OperationInput, operator: (Long, Long) -> Long): Array<Long> {
    val registerA = registers[input.valueA]
    registers[input.valueC] = operator(registerA, input.valueB.toLong())
    return registers
}

private fun applyCompareIR(registers: Array<Long>, input: OperationInput, comparator: (Long, Long) -> Boolean): Array<Long> {
    val registerB = registers[input.valueB]
    registers[input.valueC] = if (comparator(input.valueA.toLong(), registerB)) 1 else 0
    return registers
}

private fun applyCompareRI(registers: Array<Long>, input: OperationInput, comparator: (Long, Long) -> Boolean): Array<Long> {
    val registerA = registers[input.valueA]
    registers[input.valueC] = if (comparator(registerA, input.valueB.toLong())) 1 else 0
    return registers
}

private fun applyCompareRR(registers: Array<Long>, input: OperationInput, comparator: (Long, Long) -> Boolean): Array<Long> {
    val registerA = registers[input.valueA]
    val registerB = registers[input.valueB]
    registers[input.valueC] = if (comparator(registerA, registerB)) 1 else 0
    return registers
}

fun applyAddr(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorR(registers, input, Long::plus)
}

fun applyAddi(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorI(registers, input, Long::plus)
}

fun applyMulr(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorR(registers, input, Long::times)
}

fun applyMuli(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorI(registers, input, Long::times)
}

fun applyBanr(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorR(registers, input, Long::and)
}

fun applyBani(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorI(registers, input, Long::and)
}

fun applyBorr(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorR(registers, input, Long::or)
}

fun applyBori(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyOperatorI(registers, input, Long::or)
}

fun applySetr(registers: Array<Long>, input: OperationInput): Array<Long> {
    val registerA = registers[input.valueA]
    registers[input.valueC] = registerA
    return registers
}

fun applySeti(registers: Array<Long>, input: OperationInput): Array<Long> {
    registers[input.valueC] = input.valueA.toLong()
    return registers
}

fun applyGtir(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyCompareIR(registers, input) { a, b -> a > b }
}

fun applyGtri(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyCompareRI(registers, input) { a, b -> a > b }
}

fun applyGtrr(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyCompareRR(registers, input) { a, b -> a > b }
}

fun applyEqir(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyCompareIR(registers, input, Long::equals)
}

fun applyEqri(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyCompareRI(registers, input, Long::equals)
}

fun applyEqrr(registers: Array<Long>, input: OperationInput): Array<Long> {
    return applyCompareRR(registers, input, Long::equals)
}