package v2015.days.day07

import v2015.util.safeMod

private class Instruction(val valueOrKey1: String, val valueOrKey2: String?, val compute: (Int, Int) -> Int)

private fun parseInstructions(input: List<String>): Map<String, Instruction> {
    return input.associate {
        val (instruction, key) = it.split(" -> ")
        val segments = instruction.split(" ")

        key to when (segments.getOrNull(1)) {
            "AND" -> Instruction(segments[0], segments[2], Int::and)
            "OR" -> Instruction(segments[0], segments[2], Int::or)
            "LSHIFT" -> Instruction(segments[0], segments[2], Int::shl)
            "RSHIFT" -> Instruction(segments[0], segments[2], Int::shr)
            null -> Instruction(segments[0], null) { int, _ -> int }
            else -> Instruction(segments[1], null) { int, _ -> int.inv() }
        }
    }
}

private fun compute(
    key: String,
    instructions: Map<String, Instruction>,
    cache: MutableMap<String, Int> = mutableMapOf()
): Int {
    fun get(valueOrKey: String?): Int {
        if (valueOrKey == null) {
            return 0
        }

        return valueOrKey.toIntOrNull()
            ?: cache.getOrPut(valueOrKey, {
                compute(valueOrKey, instructions, cache)
            })
    }

    val instruction = instructions[key] ?: error("Unknown instruction key: $key")
    return instruction.compute(get(instruction.valueOrKey1), get(instruction.valueOrKey2))
}

fun day07a(input: List<String>, targetIndex: String = "a"): Int {
    val instructions = parseInstructions(input)

    return compute(targetIndex, instructions, mutableMapOf()).safeMod(65536)
}

fun day07b(input: List<String>, targetIndex: String = "a", overrideIndex: String = "b"): Int {
    val instructions = parseInstructions(input)

    val overrideValue = compute(targetIndex, instructions).safeMod(65536)
    val overrides = mutableMapOf(overrideIndex to overrideValue)

    return compute(targetIndex, instructions, overrides).safeMod(65536)
}
