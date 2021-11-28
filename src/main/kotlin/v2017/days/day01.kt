package v2017.days.day01

import util.safeMod

private fun sumWithOffset(digits: List<Int>, offset: Int): Int {
    return digits.withIndex().sumOf { (index, digit) ->
        if (digits[(index + offset).safeMod(digits.size)] == digit) digit
        else 0
    }
}

fun day01a(input: String): Int {
    return sumWithOffset(input.map { it.toString().toInt() }, 1)
}

fun day01b(input: String): Int {
    return sumWithOffset(input.map { it.toString().toInt() }, input.length / 2)
}
