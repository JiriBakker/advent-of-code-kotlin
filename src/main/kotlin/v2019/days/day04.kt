package v2019.days.day04

import v2019.toOrderedGroups

private fun isValidNr(value: Long, condition: (List<Int>) -> Boolean): Boolean {
    var hasDecreased = false

    val digits = value.toString().map(Char::toInt).toOrderedGroups()

    digits.reduce { prev, cur ->
        if (prev.first() > cur.first()) hasDecreased = true
        cur
    }

    return digits.any(condition) && !hasDecreased
}

fun day04a(input: String): Int {
    val (min, max) = input.split("-").map(String::toLong)

    return LongRange(min, max).filter { nr -> isValidNr(nr) { it.size >= 2 } }.size
}

fun day04b(input: String): Int {
    val (min, max) = input.split("-").map(String::toLong)

    return LongRange(min, max).filter { nr -> isValidNr(nr) { it.size == 2 } }.size
}

