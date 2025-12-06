import util.sumOfLong
import kotlin.collections.map
import kotlin.math.min

fun day06a(input: List<String>): Long {
    val problems = parseProblems(input)
    return problems.sumOfLong { problem ->
        val operator: ((Long, Long) -> Long) = if (problem.last().first() == '*') Long::times else Long::plus
        val numbers = problem.dropLast(1)

        numbers
            .map { it.trim().toLong() }
            .reduce(operator)
    }
}

fun day06b(input: List<String>): Long {
    val problems = parseProblems(input)
    return problems.sumOfLong { problem ->
        val operator: ((Long, Long) -> Long) = if (problem.last().first() == '*') Long::times else Long::plus
        val numbers = problem.dropLast(1)

        numbers[0].indices
            .map { index ->
                val digits = numbers.map { nr -> nr[index] }
                digits.joinToString("").trim().toLong()
            }
            .reduce(operator)
    }
}

private fun parseProblems(input: List<String>): List<List<String>> {
    val dividerIndices =
        input[0].indices
            .filter { i ->
                // Find indices where the entire colum is only whitespaces
                input.indices.all { j -> input[j][i] == ' ' }
            }

    val ranges =
        listOf(-1) // Divider before first problem
            .plus(dividerIndices)
            .plus(input[0].length) // Divider after last problem
            .zipWithNext()

    return ranges
        .map { (prevDividerIndex, nextDividerIndex) ->
            input.indices.map { i ->
                input[i].substring(
                    prevDividerIndex + 1,
                    min(nextDividerIndex, input[i].length) // IDE trims whitespace at end of line, so handle gracefully
                )
            }
        }
}