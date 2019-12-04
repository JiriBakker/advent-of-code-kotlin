package v2019.days.day04

private fun isValidNr(number: Long, condition: (Int) -> Boolean): Boolean {
    val digits = number.toString().map(Character::getNumericValue)

    val hasDouble =
        digits
            .zipWithNext()
            .filter { (a, b) -> a == b }
            .groupBy { it.first }
            .any { condition(it.value.size) }

    val isIncreasing = digits.sorted() == digits

    return hasDouble && isIncreasing
}

fun day04a(input: String): Int {
    val (min, max) = input.split("-").map(String::toLong)

    return LongRange(min, max).filter { nr -> isValidNr(nr) { it == it } }.size
}

fun day04b(input: String): Int {
    val (min, max) = input.split("-").map(String::toLong)

    return LongRange(min, max).filter { nr -> isValidNr(nr) { it == 1 } }.size
}

