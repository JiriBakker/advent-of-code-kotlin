package v2016.days.day12

private fun runProgram(registers: MutableMap<String, Int>, instructions: List<String>): Int {
    fun getValue(registerOrValue: String): Int = registerOrValue.toIntOrNull() ?: registers[registerOrValue]!!

    var pointer = 0
    while (pointer in instructions.indices) {
        val instruction = instructions[pointer]
        val segments = instruction.split(' ')
        when (segments[0]) {
            "cpy" -> registers[segments[2]] = getValue(segments[1])
            "inc" -> registers[segments[1]] = getValue(segments[1]) + 1
            "dec" -> registers[segments[1]] = getValue(segments[1]) - 1
            "jnz" -> if (getValue(segments[1]) != 0) pointer += getValue(segments[2]) - 1
            else -> error("Unknown instruction: ${segments[0]}")
        }
        pointer++
    }

    return registers["a"]!!
}

fun day12a(input: List<String>): Int {
    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
    return runProgram(registers, input)
}

fun day12b(input: List<String>): Int {
    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0)
    return runProgram(registers, input)
}