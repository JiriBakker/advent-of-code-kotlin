package v2019.days.day07

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram
import v2019.permute
import java.util.ArrayDeque

fun day07a(input: String): Int {
    val intCodes = parseIntCodes(input)

    val phases = listOf(0, 1, 2, 3, 4)
    val phasePermutations = permute(phases)

    return phasePermutations.map {
        it.fold(0) { input2, phase ->
            val inputs = ArrayDeque(listOf(phase, input2))
            val state = runProgram(intCodes.toList(), 0) { inputs.poll() }
            state.output.last()
        }
    }.max()!!
}

fun day07b(input: String): Int {
    val intCodes = parseIntCodes(input)

    val phases = listOf(5, 6, 7, 8, 9)
    val phasePermutations = permute(phases)

    return phasePermutations.map map@ {
        var amplifierIndex = 0
        var lastOutput = 0

        val programStates = generateSequence { ProgramState(intCodes) }.take(5).toMutableList()
        val signals = generateSequence { ArrayDeque<Int>() }.take(5).toList()

        it.forEachIndexed { index, phase -> signals[index].add(phase) }
        signals[0].add(0)

        while (true) {
            val resultState = runProgram(
                programStates[amplifierIndex].intCodes.toList(),
                programStates[amplifierIndex].pointer
            ) { signals[amplifierIndex].poll() }

            if (resultState.output.isEmpty()) {
                break
            }

            programStates[amplifierIndex] = resultState
            amplifierIndex = (amplifierIndex + 1) % 5
            signals[amplifierIndex].add(resultState.output.last())
            lastOutput = resultState.output.last()
        }

        lastOutput
    }.max()!!
}