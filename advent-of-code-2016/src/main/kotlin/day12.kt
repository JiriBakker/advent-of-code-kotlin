import runAssembunnyProgram

fun day12a(input: List<String>): Int {
    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
    return runAssembunnyProgram(registers, input).first()
}

fun day12b(input: List<String>): Int {
    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 1, "d" to 0)
    return runAssembunnyProgram(registers, input).first()
}