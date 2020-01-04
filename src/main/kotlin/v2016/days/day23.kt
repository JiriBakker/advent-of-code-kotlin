package v2016.days.day23

import v2016.runAssembunnyProgram

private fun factorial(n: Long): Long = if (n == 0L) 1 else n * factorial(n - 1)

fun day23a(input: List<String>, registerAValue: Int = 7): Int {
    val registers = mutableMapOf("a" to registerAValue, "b" to 0, "c" to 0, "d" to 0)
    return runAssembunnyProgram(registers, input)
}

fun day23b(input: List<String>, registerAValue: Long = 12): Long {
    val registers = mutableMapOf("a" to 7, "b" to 0, "c" to 0, "d" to 0)
    val factorialOffset = runAssembunnyProgram(registers, input) - factorial(7)

    return factorial(registerAValue) + factorialOffset
}