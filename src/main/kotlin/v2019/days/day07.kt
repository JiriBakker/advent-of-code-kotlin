package v2019.days.day07

import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram
import v2019.permute

fun day07a(input: String): Int {
    val intCodes = parseIntCodes(input)

    val phases = listOf(0, 1, 2, 3, 4)
    val phasePermutations = permute(phases)

    return phasePermutations.map {
        it.fold(0) { input2, phase ->
            val (_, output) = runProgram(intCodes.toList(), listOf(phase, input2))
            output.last()
        }
    }.max()!!
}

fun day07b(input: String, inputValue: Int = 5): Int {
    val intCodes = parseIntCodes(input)

    val (_, output) = runProgram(intCodes, listOf(inputValue))

    return output.last()
}