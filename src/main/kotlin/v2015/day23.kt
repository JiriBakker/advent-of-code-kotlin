package v2015

private fun runProgram(input: List<String>, initialAValue: Int): Int {
    val instructions = input.map { line -> line.split(" ").map { it.trimEnd(',') } }

    val registers = mutableMapOf("a" to initialAValue, "b" to 0)

    fun modifyRegister(key: String, operation: (Int) -> Int) { registers[key] = operation(registers[key]!!)}
    fun jumpIf(offset: Int, key: String, condition: (Int) -> Boolean): Int = if (condition(registers[key]!!)) offset else 1

    var pointer = 0
    while (true) {
        if (pointer !in instructions.indices) {
            return registers["b"]!!
        }

        val instruction = instructions[pointer]
        when (instruction[0]) {
            "hlf" -> {
                modifyRegister(instruction[1]) { it / 2 }
                pointer++
            }
            "tpl" -> {
                modifyRegister(instruction[1]) { it * 3 }
                pointer++
            }
            "inc" -> {
                modifyRegister(instruction[1]) { it + 1 }
                pointer++
            }
            "jmp" -> {
                pointer += instruction[1].toInt()
            }
            "jie" -> {
                pointer += jumpIf(instruction[2].toInt(), instruction[1]) { it % 2 == 0 }
            }
            "jio" -> {
                pointer += jumpIf(instruction[2].toInt(), instruction[1]) { it == 1 }
            }
            else -> error("Unknown instruction")
        }
    }
}

fun day23a(input: List<String>): Int {
    return runProgram(input, 0)
}

fun day23b(input: List<String>): Int {
    return runProgram(input, 1)
}
