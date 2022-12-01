package v2019

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

fun day16a(input: String, nrOfPhases: Int = 100): String {
    val signal = input.map(Character::getNumericValue).toIntArray()

    repeat(nrOfPhases) { iterateSignal(signal) }

    return signal.take(8).joinToString("")
}

fun day16b(input: String, nrOfPhases: Int = 100): String {
    val baseSignal = input.map(Character::getNumericValue)

    val offset = input.take(7).toInt()

    val signal = sequence { repeat(10000) { yieldAll(baseSignal) } }.drop(offset).toList().toIntArray()

    repeat(nrOfPhases) {
        var partialSum = signal.sum()
        signal.forEachIndexed { index, value ->
            signal[index] = ((partialSum % 10) + 10) % 10
            partialSum -= value
        }
    }

    return signal.take(8).joinToString("")
}