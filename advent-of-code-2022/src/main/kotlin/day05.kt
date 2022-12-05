import java.util.Stack

import Day05.getTopCrates
import Day05.parse

fun day05a(input: List<String>): String {
    val (stacks, instructions) = input.parse()

    instructions.forEach { instruction ->
        repeat(instruction.amount) {
            stacks[instruction.to].add(stacks[instruction.from].pop())
        }
    }

    return stacks.getTopCrates()
}

fun day05b(input: List<String>): String {
    val (stacks, instructions) = input.parse()

    instructions.forEach { instruction ->
        val cratesToMove = (0 until instruction.amount).map { stacks[instruction.from].pop() }
        stacks[instruction.to].addAll(cratesToMove.reversed())
    }

    return stacks.getTopCrates()
}

object Day05 {

    class Instruction(val amount: Int, val from: Int, val to: Int)

    fun List<String>.parse(): Pair<List<Stack<Char>>, List<Instruction>> {
        val stackLines = this.takeWhile { it.trim().isNotEmpty() }
        val instructionLines = this.drop(stackLines.size + 1)

        val nrOfStacks = stackLines.last().trim().split("   ").size

        val stacks = (0 until nrOfStacks).map { Stack<Char>() }

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

    fun List<Stack<Char>>.getTopCrates() =
        map { it.pop() }.joinToString("")
}

