import util.pow

fun day17a(input: List<String>): String {
    val initialA = input[0].split(" ").last().toLong()

    val instructions = input.drop(4).first().split(" ")[1].split(",").map { it.toInt() }

    return runProgram(initialA, instructions).toList().joinToString(",")
}

fun day17b(input: List<String>): Long {
    var initialA = 8*8L

    val instructions = input.drop(4).first().split(" ")[1].split(",").map { it.toInt() }

    var nrOfDigits = 3
    var targetOutput = instructions.takeLast(nrOfDigits)

    while (true) {
        if (runProgram(initialA, instructions).toList() != targetOutput) {
            initialA++
            continue
        }

        if (nrOfDigits == instructions.size) {
            break
        }
        initialA *= 8
        nrOfDigits++
        targetOutput = instructions.takeLast(nrOfDigits)
    }

    return initialA
}

private fun runProgram(initialA: Long, instructions: List<Int>): Sequence<Int> {
    return sequence {
        var registerA = initialA
        var registerB = 0L
        var registerC = 0L

        var instructionPointer = 0L

        fun incInstructionPointer(delta: Long = 1) {
            instructionPointer += delta
        }

        fun getLiteralOperand(): Int {
            return instructions[instructionPointer.toInt()]
        }

        fun getComboOperand(): Long {
            return when (getLiteralOperand()) {
                4 -> registerA
                5 -> registerB
                6 -> registerC
                else -> getLiteralOperand().toLong()
            }
        }

        fun adv() {
            incInstructionPointer()
            registerA /= 2L.pow(getComboOperand())
            incInstructionPointer()
        }

        fun bxl() {
            incInstructionPointer()
            registerB = registerB xor getLiteralOperand().toLong()
            incInstructionPointer()
        }

        fun bst() {
            incInstructionPointer()
            registerB = getComboOperand() % 8
            incInstructionPointer()
        }

        fun jnz() {
            incInstructionPointer()
            if (registerA != 0L) {
                instructionPointer = getLiteralOperand().toLong()
            } else {
                incInstructionPointer()
            }
        }

        fun bxc() {
            incInstructionPointer()
            registerB = registerB xor registerC
            incInstructionPointer()
        }

        fun out(): Int {
            incInstructionPointer()
            val output = (getComboOperand() % 8L).toInt()
            incInstructionPointer()
            return output
        }

        fun bdv() {
            incInstructionPointer()
            registerB = registerA / 2L.pow(getComboOperand())
            incInstructionPointer()
        }

        fun cdv() {
            incInstructionPointer()
            registerC = registerA / 2L.pow(getComboOperand())
            incInstructionPointer()
        }


        while (instructionPointer in instructions.indices) {
            when (getLiteralOperand()) {
                0 -> adv()
                1 -> bxl()
                2 -> bst()
                3 -> jnz()
                4 -> bxc()
                5 -> yield(out())
                6 -> bdv()
                7 -> cdv()
            }
        }
    }
}
