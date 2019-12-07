package v2019.days.day05

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram

fun day05a(input: String): Int {
    val intCodes = parseIntCodes(input)

    var state = ProgramState(intCodes)
    while (true) {
        state = runProgram(state.intCodes, state.pointer) { 1 }
        if (state.output.last() != 0) {
            return state.output.last()
        }
    }
}

fun day05b(input: String, inputValue: Int = 5): Int {
    val intCodes = parseIntCodes(input)

    var state = ProgramState(intCodes, 0, listOf())
    while (true) {
        state = runProgram(state.intCodes, state.pointer) { inputValue }
        if (state.output.last() != 0) {
            return state.output.last()
        }
    }
}