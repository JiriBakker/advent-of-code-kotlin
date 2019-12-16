package v2019.days.day16

import kotlin.math.abs

private fun iterateSignal(digits: IntArray): IntArray {
    for (i in digits.indices) {
        digits[i] = abs(computeDigit2(digits, i)) % 10
    }
    return digits
}

private fun computeDigit2(digits: IntArray, outputIndex: Int): Int {
    val stepSize = outputIndex + 1
    var i = outputIndex
    var sum = 0
    while (i < digits.size) {
        for (i2 in i until i + stepSize) {
            if (i2 >= digits.size) {
                return sum
            }
            sum += digits[i2]
        }
        i += stepSize * 2
        for (i2 in i until i + stepSize) {
            if (i2 >= digits.size) {
                return sum
            }
            sum -= digits[i2]
        }
        i += stepSize * 2
    }
    return sum
}

// private fun computeDigit(digits: Sequence<Int>, outputIndex: Int): Int {
//     return digits.foldIndexed(0) { index, sum, digit ->
//         if (index < outputIndex) sum
//
//         //val offset = if (outputIndex == 0) 1 else if (index == 0) 0 else if ((index + 1) % (outputIndex + 1) == 0) 1 else 0
//         val patternIndex = ((index / (outputIndex + 1)) + offset) % 4
//         when (patternIndex) {
//             1 -> sum + digit
//             3 -> sum - digit
//             else -> sum
//         }
//     }
// }

fun day16a(input: String, nrOfIterations: Int = 100): String {
    var digits = input.map(Character::getNumericValue).toIntArray()

    for (i in 0 until nrOfIterations) {
        digits = iterateSignal(digits)
        println("Step $i: ${digits.joinToString("")}")
    }

    return digits.take(8).joinToString("")
}

fun day16b(input: String): String {
    val baseDigits = input.map(Character::getNumericValue)
    var digits = sequence { repeat(10000) { yieldAll(baseDigits) } }.toList().toIntArray()

    for (i in 0 until 100) {
        digits = iterateSignal(digits)
        println("Step $i")
    }

    val offset = input.take(8).toInt()

    return digits.drop(offset).take(8).joinToString("")
}