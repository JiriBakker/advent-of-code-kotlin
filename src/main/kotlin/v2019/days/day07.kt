package v2019.days.day07

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram
import v2019.util.permute

fun day07a(input: String): Long {
    val intCodes = parseIntCodes(input)

    val phasePermutations = listOf(0L, 1, 2, 3, 4).permute()

    return phasePermutations.map {
        it.fold(0L) { input2, phase ->
            val inputs = listOf(phase, input2)
            val state = runProgram(ProgramState(intCodes).withInputs(inputs))
            state.output!!
        }
    }.max()!!
}

fun day07b(input: String): Long {
    val intCodes = parseIntCodes(input)

    val phasePermutations = listOf(5L, 6, 7, 8, 9).permute()

    return phasePermutations.map {
        var amplifierIndex = 0
        var lastOutput = 0L

        val programStates =
            generateSequence {
                ProgramState(intCodes)
            }
                .take(5)
                .toMutableList()

        it.forEachIndexed { index, phase -> programStates[index].inputs.add(phase) }
        programStates[0].inputs.add(0)

        while (true) {
            val resultState = runProgram(programStates[amplifierIndex])

            if (resultState.output == null) {
                break
            }

            programStates[amplifierIndex] = resultState
            amplifierIndex = (amplifierIndex + 1) % 5
            programStates[amplifierIndex].inputs.add(resultState.output)
            lastOutput = resultState.output
        }

        lastOutput
    }.max()!!
}