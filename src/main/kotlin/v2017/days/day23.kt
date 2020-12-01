package v2017.days.day23

private fun runProgram(initialRegisters: Map<String, Long>, instructions: List<String>): Int {
    val registers = initialRegisters.toMutableMap()
    fun getValue(registerKeyOrNumber: String): Long {
        return registerKeyOrNumber.toLongOrNull() ?: registers[registerKeyOrNumber]!!
    }

    var multiplicationCount = 0
    var index = 0

    do {
        val segments = instructions[index].split(" ")
        when (segments[0]) {
            "set" -> registers[segments[1]] = getValue(segments[2])
            "sub" -> registers[segments[1]] = registers[segments[1]]!! - getValue(segments[2])
            "mul" -> {
                registers[segments[1]] = registers[segments[1]]!! * getValue(segments[2])
                multiplicationCount++
            }
            "jnz" -> {
                if (getValue(segments[1]) != 0L) {
                    index += getValue(segments[2]).toInt() - 1
                }
            }
            else -> error("Unknown instruction ${instructions[index]}")
        }
    } while (++index in instructions.indices)

    return multiplicationCount
}

fun day23a(input: List<String>): Int {
    val registers = ('a' .. 'h').associate { it.toString() to 0L }
    return runProgram(registers, input)
}

// Decompiled input, assuming instructions remain the same, but numbers on specific lines can differ per input
fun day23b(input: List<String>): Int {
    fun getInt(line: Int): Int = input[line].split(' ').last().toInt()

    val min = getInt(0) * getInt(4) - getInt(5)
    val max = min - getInt(7)
    val step = - getInt(30)

    fun isNotPrime(n: Int): Boolean = (2 until n).any { n % it == 0 }

    return (min .. max step step).count(::isNotPrime)
}