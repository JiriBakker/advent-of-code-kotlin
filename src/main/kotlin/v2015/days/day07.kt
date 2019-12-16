package v2015.days.day07

private class Instruction(val destination: String, val operation: (Map<String, Instruction>) -> Long)

private fun parseInstructions(input: List<String>, cache: MutableMap<String, Long> = mutableMapOf()): Map<String, Instruction> {

    return input.associate {
        val (operation, destination) = it.split(" -> ")
        val operationSegments = operation.split(" ")

        fun getLiteralValueOrCompute(segmentIndex: Int, instructions: Map<String, Instruction>): Long {
            val value = operationSegments[segmentIndex].toLongOrNull()
            if (value != null) {
                return value
            }

            val destination = operationSegments[segmentIndex]
            return cache.getOrPut(destination, {
                instructions[destination]!!.operation(instructions)
            })
        }

        val operationFunc: (Map<String, Instruction>) -> Long = when (operationSegments.getOrNull(1)) {
            null -> { instructions -> getLiteralValueOrCompute(0, instructions) }
            "AND" -> { instructions -> getLiteralValueOrCompute(0, instructions) and getLiteralValueOrCompute(2, instructions) }
            "OR" -> { instructions -> getLiteralValueOrCompute(0, instructions) or getLiteralValueOrCompute(2, instructions) }
            "LSHIFT" -> { instructions -> getLiteralValueOrCompute(0, instructions) shl getLiteralValueOrCompute(2, instructions).toInt() }
            "RSHIFT" -> { instructions -> getLiteralValueOrCompute(0, instructions) shr getLiteralValueOrCompute(2, instructions).toInt() }
            else -> { instructions -> getLiteralValueOrCompute(1, instructions).inv()}
        }
        destination to Instruction(destination, operationFunc)
    }
}

fun day07a(input: List<String>, targetIndex: String = "a"): Long {
    val instructions = parseInstructions(input)

    return (instructions[targetIndex]!!.operation(instructions) + 65536) % 65536
}

fun day07b(input: List<String>, targetIndex: String = "a", overrideIndex: String = "b"): Long {
    val instructions = parseInstructions(input)

    val overrideValue = (instructions[targetIndex]!!.operation(instructions) + 65536) % 65536

    val instructions2 = parseInstructions(input, mutableMapOf(overrideIndex to overrideValue))

    return (instructions2[targetIndex]!!.operation(instructions2) + 65536) % 65536
}
