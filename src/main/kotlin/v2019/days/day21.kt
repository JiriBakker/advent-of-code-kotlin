package v2019.days.day21

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes
import java.util.ArrayDeque

private const val NEWLINE = 10L

private fun runSpringDroidProgram(intCodes: MutableMap<Long, Long>, vararg instructions: String): Long {
    val inputQueue = ArrayDeque<Long>()
    inputQueue.addAll(instructions.toList().flatMap { instruction -> instruction.map { it.code.toLong() }.plus(NEWLINE) })

    return generateProgramOutput(intCodes) { inputQueue.poll() }
        .firstOrNull { it > 256 }
            ?: error("Springdroid fell into space")
}

fun day21a(input: String): Long {
    val intCodes = parseIntCodes(input)

    return runSpringDroidProgram(intCodes,
        "NOT A T", // 1 is hole
        "NOT C J", // 3 is hole
        "AND D J", // 3 is hole and 4 is ground
        "OR T J",  // JUMP IF (1 is hole or (3 is hole and 4 is ground)
        "WALK"
    )
}

fun day21b(input: String): Long {
    val intCodes = parseIntCodes(input)

    return runSpringDroidProgram(intCodes,
        "NOT A T", // 1 is hole
        "NOT B J", // 2 is hole
        "OR J T", // 1 or 2 are hole
        "NOT C J", // 3 is hole
        "OR J T", // 1 or 2 or 3 are hole

        "OR I J", // 9 is ground
        "AND E J", // 5 and 9 are ground
        "OR H J", // (5 and 9 are ground) or 8 is ground
        "AND D J", // ((5 and 9 are ground) or 8 is ground) and 4 is ground

        "AND T J", // JUMP IF (1 or 2 or 3 are hole) and (((5 and 9 are ground) or 8 is ground) and 4 is ground)

        "RUN"
    )
}
