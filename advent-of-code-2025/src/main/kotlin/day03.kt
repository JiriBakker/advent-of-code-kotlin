import util.sumOfLong
import kotlin.collections.map
import kotlin.text.map

fun day03a(input: List<String>) =
    parseInput(input)
        .findLargestTotalJoltage(nrOfDigits = 2)

fun day03b(input: List<String>) =
    parseInput(input)
        .findLargestTotalJoltage(nrOfDigits = 12)

private fun List<List<Int>>.findLargestTotalJoltage(nrOfDigits: Int) =
    this.sumOfLong { findLargestJoltage(it, nrOfDigits) }

private fun findLargestJoltage(batteryBank: List<Int>, nrOfDigits: Int): Long {
    val digits = mutableListOf<Int>()
    var remainingBank = batteryBank
    for (margin in (nrOfDigits - 1) downTo 0) {
        val indexOfMax = findFirstIndexOfMax(remainingBank, margin)
        digits.add(remainingBank[indexOfMax])
        remainingBank = remainingBank.drop(indexOfMax + 1)
    }
    return digits.combineDigitsToNumber()
}

private fun List<Int>.combineDigitsToNumber() =
    this.joinToString("").toLong()

private fun findFirstIndexOfMax(batteryBank: List<Int>, margin: Int): Int {
    val searchSpace = batteryBank.subList(0, batteryBank.size - margin)
    return searchSpace.indexOfFirst { it == searchSpace.max() }
}

private fun parseInput(input: List<String>) =
    input.map { line -> line.map { it.digitToInt() }}
