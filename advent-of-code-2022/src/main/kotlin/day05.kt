import java.util.Stack

import Day05.applyInstructions
import Day05.getTopCrates
import Day05.parse
import util.popMultiple

typealias CrateStack = Stack<Char>

fun day05a(input: List<String>): String {
    val (stacks, instructions) = input.parse()

    return stacks
        .applyInstructions(instructions)
        .getTopCrates()
}


fun day05b(input: List<String>): String {
    val (stacks, instructions) = input.parse()

    return stacks
        .applyInstructions(instructions, maintainOrder = true)
        .getTopCrates()
}

object Day05 {

    class Instruction(val amount: Int, val from: Int, val to: Int)

    fun List<String>.parse(): Pair<List<CrateStack>, List<Instruction>> {
        val stackLines = this.takeWhile { it.trim().isNotEmpty() }
        val instructionLines = this.drop(stackLines.size + 1)

        val nrOfStacks = stackLines.last().trim().split("   ").size

        val stacks = (0 until nrOfStacks).map { CrateStack() }

        stackLines.reversed().forEach { line ->
            var stackNr = 0

            do {
                val offset = stackNr * 4 + 1
                val char = line.getOrNull(offset)
                if (char?.isLetter() == true) {
                    stacks[stackNr].add(char)
                }
                stackNr++
            } while (stackNr < nrOfStacks)
        }

        val instructions =
            instructionLines
                .map { instruction ->
                    val parts = instruction.split(" ")
                    val amount = parts[1].toInt()
                    val from = parts[3].toInt() - 1
                    val to = parts[5].toInt() - 1

                    Instruction(amount, from, to)
                }

        return stacks to instructions
    }

    fun List<CrateStack>.applyInstructions(instructions: List<Instruction>, maintainOrder: Boolean = false): List<CrateStack> {
        instructions.forEach { instruction ->
            val stackFrom = this[instruction.from]
            val stackTo = this[instruction.to]
            val cratesToMove = stackFrom.popMultiple(instruction.amount)
            stackTo.addAll(
                if (maintainOrder) cratesToMove.reversed()
                else cratesToMove
            )
        }
        return this
    }

    fun List<CrateStack>.getTopCrates() =
        map { it.pop() }.joinToString("")

}

