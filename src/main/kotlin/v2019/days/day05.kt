package v2019.days.day05

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram
import java.util.ArrayDeque

fun day05a(input: String): Int {
    val intCodes = parseIntCodes(input)

    var state = ProgramState(intCodes)
    while (true) {
        state = runProgram(ProgramState(state.intCodes, state.pointer, ArrayDeque(listOf(1))))
        if (state.output != null && state.output != 0) {
            return state.output!!
        }
    }
}

fun day05b(input: String, inputValue: Int = 5): Int {
    val intCodes = parseIntCodes(input)

    var state = ProgramState(intCodes, 0, ArrayDeque(listOf(inputValue)))
    while (true) {
        state = runProgram(state)
        if (state.output != null && state.output != 0) {
            return state.output!!
        }
    }
}