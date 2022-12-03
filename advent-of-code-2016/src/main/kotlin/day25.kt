import runAssembunnyProgram

fun day25a(input: List<String>): Int {
    return (0..Int.MAX_VALUE).first { a ->
        val registers = mutableMapOf("a" to a, "b" to 0, "c" to 0, "d" to 0)
        runAssembunnyProgram(registers, input)
            .take(1000)
            .withIndex()
            .all { (index, output) -> index % 2 == output }
    }
}