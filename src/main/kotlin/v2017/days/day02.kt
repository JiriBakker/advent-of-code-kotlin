package v2017.days.day02

import kotlin.math.min
import kotlin.math.max

private fun parseSpreadsheet(input: List<String>): List<List<Int>> {
    return input.map { line -> line.split(' ', '\t').map { it.toInt() } }
}

fun day02a(input: List<String>): Int {
    val spreadsheet = parseSpreadsheet(input)

    return spreadsheet.sumOf { row ->
        row.fold(Pair(Int.MAX_VALUE, 0)) { (min, max), value ->
            Pair(min(min, value), max(max, value))
        }.let { it.second -it.first}
    }
}

private fun findCleanDivision(nrs: List<Int>): Int {
    nrs.forEach { value ->
        val cleanDivisor = nrs.lastOrNull { value != it && value % it == 0 }
        if (cleanDivisor != null) {
            return value / cleanDivisor
        }
    }
    error("No clean divisor found")
}

fun day02b(input: List<String>): Int {
    val spreadsheet = parseSpreadsheet(input)
    return spreadsheet.sumOf(::findCleanDivision)
}
