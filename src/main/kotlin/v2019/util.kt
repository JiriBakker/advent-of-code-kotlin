package v2019

import java.io.File
import java.util.PriorityQueue

fun readInputLines(fileName: String): List<String> {
    return File("input/2019/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Long.toDigits(): List<Int> {
    return this.toString().map(Character::getNumericValue)
}

fun List<Int>.joinToLong(): Long {
    return this.joinToString("").toLong()
}

fun Long.firstDigit(): Int {
    return Character.getNumericValue(this.toString().first())
}