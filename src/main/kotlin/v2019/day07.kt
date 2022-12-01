package v2019

import util.permute
import java.util.ArrayDeque

fun day07a(input: String): Long {
    val intCodes = parseIntCodes(input)

    val phasePermutations = listOf(0L, 1, 2, 3, 4).permute()

    return phasePermutations.map {
        it.fold(0L) { input2, phase ->
            val inputs = ArrayDeque(listOf(phase, input2))
            generateProgramOutput(intCodes) { inputs.poll() }.first()
        }
    }.maxOrNull()!!
}

fun day07b(input: String): Long {
    val intCodes = parseIntCodes(input)

    val phasePermutations = listOf(5L, 6, 7, 8, 9).permute()

    return phasePermutations.map { phases ->
        val amplifiers = mutableListOf<Iterator<Long>>()
        val amplifierOutputs = MutableList<Long?>(5) { null }
        val inputQueues = MutableList<ArrayDeque<Long>>(5) { ArrayDeque() }

        phases.forEachIndexed { index, phase -> inputQueues[index].add(phase) }
        inputQueues[0].add(0)

        phases.forEachIndexed { index, _ ->
            amplifiers.add(
                generateProgramOutput(intCodes.toMutableMap()) {
                    if (inputQueues[index].isNotEmpty()) {
                        inputQueues[index].poll()
                    } else {
                        amplifierOutputs[(index + 4) % 5]!!
                    }
                }.iterator()
            )
        }

        var amplifierIndex = 0
        while (amplifiers[amplifierIndex].hasNext()) {
            amplifierOutputs[amplifierIndex] = amplifiers[amplifierIndex].next()
            amplifierIndex = (amplifierIndex + 1) % 5
        }

        amplifierOutputs[(amplifierIndex + 4) % 5]!!
    }.maxOrNull()!!
}