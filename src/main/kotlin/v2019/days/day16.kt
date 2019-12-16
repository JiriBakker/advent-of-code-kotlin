package v2019.days.day16

import kotlin.math.abs

private fun iterateSignal(signal: IntArray) {
    for (index in signal.indices) {
        signal[index] = abs(computeDigit(signal, index)) % 10
    }
}

private fun computeDigit(signal: IntArray, outputIndex: Int): Int {
    val stepSize = outputIndex + 1
    var i = outputIndex
    var sum = 0
    var multiplier = 1
    while (true) {
        for (i2 in 0 until stepSize) {
            val index = i + i2
            if (index >= signal.size) {
                return sum
            }
            sum += signal[index] * multiplier
        }
        i += stepSize * 2
        multiplier *= -1
    }
}

fun day16a(input: String, nrOfIterations: Int = 100): String {
    val signal = input.map(Character::getNumericValue).toIntArray()

    for (i in 0 until nrOfIterations) {
        iterateSignal(signal)
    }

    return signal.take(8).joinToString("")
}

fun day16b(input: String): String {
    val baseDigits = input.map(Character::getNumericValue)
    val signal = sequence { repeat(10000) { yieldAll(baseDigits) } }.toList().toIntArray()

    for (i in 0 until 100) {
        iterateSignal(signal)
        println("Step $i")
    }

    val offset = input.take(8).toInt()

    return signal.drop(offset).take(8).joinToString("")
}