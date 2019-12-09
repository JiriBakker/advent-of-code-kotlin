package v2019.days.day09

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgramUntilNonZeroOutput

fun day09a(input: String): Long {
    val intCodes = parseIntCodes(input)

    return runProgramUntilNonZeroOutput(ProgramState(intCodes).withInputs(listOf(1L)))
}

fun day09b(input: String): Long {
    val intCodes = parseIntCodes(input)

    return runProgramUntilNonZeroOutput(ProgramState(intCodes).withInputs(listOf(2L)))
}

// fun day09b(input: String, inputValue: Int = 5): Int {
//     val intCodes = parseIntCodes(input)
//
//     var state = ProgramState(intCodes, 0, ArrayDeque(listOf(inputValue)))
//     while (true) {
//         state = runProgram(state)
//         if (state.output != null && state.output != 0) {
//             return state.output!!
//         }
//     }
// }