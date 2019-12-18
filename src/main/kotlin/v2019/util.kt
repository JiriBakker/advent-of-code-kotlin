package v2019.util

import java.io.File
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun readInputLines(fileName: String): List<String> {
    return File("input/2019/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}

fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
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

fun <T> List<T>.permute(): List<List<T>> {
    fun permute(available: List<T>, used: List<T>): List<List<T>> {
        if (available.isEmpty()) {
            return listOf(used)
        }

        return available.flatMap { current ->
            permute( available.minus(current), used + current)
        }
    }

    return permute(this, listOf())
}

fun <T> List<T>.combine(size: Int): List<List<T>> {
    val available = this

    fun combine(used: List<T>): List<List<T>> {
        if (used.size == size) {
            return listOf(used)
        }

        return available.flatMap { current: T -> combine( used + current) }
    }

    return combine(listOf())
}

fun pythDistance(x1: Int, y1: Int, x2: Int, y2: Int): Double {
    return pythDistance(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble())
}

fun pythDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
    return sqrt((x1 - x2).pow(2.0) + (y1 - y2).pow(2.0))
}

fun manhattanDistance(x1: Long, y1: Long, x2: Long, y2: Long): Long {
    return abs(x2 - x1) + abs(y2 - y1)
}

inline fun <T, TOut : Comparable<TOut>> List<T>.getBounds(selector: (T) -> TOut): Pair<TOut, TOut> {
    return Pair(selector(this.minBy(selector)!!), selector(this.maxBy(selector)!!))
}

inline fun <T> List<T>.forEachCombinationPair(action: (T, T) -> Unit) {
    for (i1 in this.indices) {
        for (i2 in (i1 + 1) until this.size) {
            action(this[i1], this[i2])
        }
    }
}

fun greatestCommonDenominator(a: Long, b: Long): Long = if (b == 0L) a else greatestCommonDenominator(b, a % b)
fun leastCommonMultiple(a: Long, b: Long): Long = a / greatestCommonDenominator(a, b) * b
fun leastCommonMultiple(nrs: Collection<Long>): Long = nrs.reduce(::leastCommonMultiple)

class BinarySearchRange(var min: Long, var max: Long) {
    fun median(): Long {
        return ((max - min) / 2) + min
    }
    fun consolidateMin() {
        min = median() + 1
    }
    fun consolidateMax() {
        max = median() - 1
    }
}

fun doBinarySearch(range: BinarySearchRange, target: Long, compute: (Long) -> Long): Long {
    while (range.max >= range.min) {
        val value = compute(range.median())
        when {
            value == target -> return range.median()
            value > target  -> range.consolidateMax()
            else            -> range.consolidateMin()
        }
    }
    return range.median()
}