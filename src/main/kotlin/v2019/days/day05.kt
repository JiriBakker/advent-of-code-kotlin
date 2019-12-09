package v2019.days.day05

import v2019.intCoder.ProgramState
import v2019.intCoder.parseIntCodes
import v2019.intCoder.runProgram
import v2019.intCoder.runProgramUntilNonZeroOutput
import java.util.ArrayDeque

fun day05a(input: String): Long {
    val intCodes = parseIntCodes(input)

    return runProgramUntilNonZeroOutput(ProgramState(intCodes).withInputs(listOf(1L)))
}

fun day05b(input: String, inputValue: Long = 5): Long {
    val intCodes = parseIntCodes(input)

    return runProgramUntilNonZeroOutput(ProgramState(intCodes).withInputs(listOf(inputValue)))
}