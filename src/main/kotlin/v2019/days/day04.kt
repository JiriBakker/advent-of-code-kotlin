package v2019.days.day04

private fun hasDouble(digits: List<Int>, condition: (Int) -> Boolean): Boolean {
    return digits
        .zipWithNext()
        .filter { (a, b) -> a == b }
        .groupBy { it.first }
        .any { condition(it.value.size) }
}

private fun Long.toDigits(): List<Int> {
    return this.toString().map(Character::getNumericValue)
}

private fun List<Int>.joinToLong(): Long {
    return this.joinToString("").toLong()
}

private fun generateAscendingNumbers(digits: List<Int>, maxLength: Int): List<Long> {
    if (digits.size == maxLength) {
        return listOf(digits.joinToLong())
    }

    return IntRange(digits.last(), 9)
        .flatMap { digit -> generateAscendingNumbers(digits.toMutableList().plus(digit), maxLength) }
}

private fun firstDigit(number: Long): Int {
    return Character.getNumericValue(number.toString().first())
}

private fun findAscendingNumbersInRange(min: Long, max: Long): List<Long> {
    val maxLength = max.toString().length
    val lowestDigit = if (maxLength > min.toString().length) 0 else firstDigit(min)
    return IntRange(lowestDigit, firstDigit(max))
        .flatMap { digit -> generateAscendingNumbers(listOf(digit), maxLength) }
        .filter { nr -> nr in min..max }
}

fun day04a(input: String): Int {
    val (min, max) = input.split("-").map(String::toLong)

    val ascendingNumbers = findAscendingNumbersInRange(min, max)

    return ascendingNumbers.filter { nr -> hasDouble(nr.toDigits()) { it == it } }.size
}

fun day04b(input: String): Int {
    val (min, max) = input.split("-").map(String::toLong)

    val ascendingNumbers = findAscendingNumbersInRange(min, max)

    return ascendingNumbers.filter { nr -> hasDouble(nr.toDigits()) { it == 1 } }.size
}

