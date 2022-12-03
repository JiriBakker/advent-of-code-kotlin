fun runAssembunnyProgram(registers: MutableMap<String, Int>, initialInstructions: List<String>): Sequence<Int> {
    fun getValue(registerOrValue: String): Int = registerOrValue.toIntOrNull() ?: registers[registerOrValue]!!

    val instructions = initialInstructions.toMutableList()

    fun toggle(index: Int) {
        if (index !in instructions.indices) {
            return
        }

        val segments = instructions[index].split(' ')
        instructions[index] = when (segments[0]) {
            "cpy" -> "jnz ${segments[1]} ${segments[2]}"
            "inc" -> "dec ${segments[1]}"
            "dec" -> "inc ${segments[1]}"
            "jnz" -> "cpy ${segments[1]} ${segments[2]}"
            "tgl" -> "inc ${segments[1]}"
            else -> error("Unknown instruction: ${segments[0]}")
        }
    }

    return sequence {
        var pointer = 0
        while (pointer in instructions.indices) {
            val instruction = instructions[pointer]
            val segments = instruction.split(' ')
            when (segments[0]) {
                "cpy" -> if (segments[2].toIntOrNull() == null) registers[segments[2]] = getValue(segments[1])
                "inc" -> registers[segments[1]] = getValue(segments[1]) + 1
                "dec" -> registers[segments[1]] = getValue(segments[1]) - 1
                "jnz" -> if (getValue(segments[1]) != 0) pointer += getValue(segments[2]) - 1
                "tgl" -> toggle(getValue(segments[1]) + pointer)
                "out" -> yield(registers[segments[1]]!!)
                else -> Unit
            }
            pointer++
            //println(registers["a"]!!)
        }

        yield(registers["a"]!!)
    }
}