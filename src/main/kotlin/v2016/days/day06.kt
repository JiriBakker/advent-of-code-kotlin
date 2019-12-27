package v2016.days.day06

import util.transpose

private fun columnCharCounts(input: List<String>): List<Map<Char, Int>> {
    return input
        .transpose()
        .map { column -> column.groupingBy { it }.eachCount() }
        .toList()
}

fun day06a(input: List<String>): String {
    return columnCharCounts(input)
        .map { it.maxBy { (_, count) -> count }!!.key }
        .joinToString("")
}

fun day06b(input: List<String>): String {
    return columnCharCounts(input)
        .map { it.minBy { (_, count) -> count }!!.key }
        .joinToString("")
}