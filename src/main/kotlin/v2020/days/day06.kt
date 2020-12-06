package v2020.days.day06

import util.countLetters as countAnswers
import util.splitByDoubleNewLine

fun day06a(input: List<String>): Int {
    return input.splitByDoubleNewLine()
        .map { group -> group.countAnswers() }
        .sumBy { it.size }
}

fun day06b(input: List<String>): Int {
    return input.splitByDoubleNewLine()
        .map { group -> group.size to group.countAnswers() }
        .sumBy { (groupSize, answerCounts) -> answerCounts.values.count { it == groupSize }}
}

