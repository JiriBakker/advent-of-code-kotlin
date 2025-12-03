import util.sumOfLong
import kotlin.collections.map
import kotlin.text.map

fun day03a(input: List<String>): Long {
    val batteryBanks = parseInput(input)
    return batteryBanks.sumOfLong { findLargestJoltage(it, nrOfDigits = 2)}
}

fun day03b(input: List<String>): Long {
    val batteryBanks = parseInput(input)
    return batteryBanks.sumOfLong { findLargestJoltage(it, nrOfDigits = 12)}
}

private fun findLargestJoltage(batteryBank: List<Int>, nrOfDigits: Int): Long {
    val digits = mutableListOf<Int>()
    var remainingBank = batteryBank
    for (margin in (nrOfDigits - 1) downTo 0) {
        val indexOfMax = findFirstIndexOfMax(remainingBank, margin)
        digits.add(remainingBank[indexOfMax])
        remainingBank = remainingBank.drop(indexOfMax + 1)
    }
    return digits.joinToString("").toLong()
}

private fun findFirstIndexOfMax(batteryBank: List<Int>, margin: Int): Int {
    var indexOfMax = 0
    for (i in 1 until batteryBank.size - margin) {
        if (batteryBank[i] > batteryBank[indexOfMax]) {
            indexOfMax = i
        }
    }
    return indexOfMax
}

private fun parseInput(input: List<String>) =
    input.map { line -> line.map { it.digitToInt() }}
