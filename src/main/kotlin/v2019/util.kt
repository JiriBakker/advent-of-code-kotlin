package v2019.util

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

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

fun <T> List<T>.permute(): List<List<T>> {
    fun <T> permute(available: List<T>, used: List<T>): List<List<T>> {
        if (available.isEmpty()) {
            return listOf(used)
        }

        return available.flatMap { current ->
            permute( available.minus(current), used + current)
        }
    }

    return permute(this, listOf())
}

fun pythDistance(x1: Int, y1: Int, x2: Int, y2: Int): Double {
    return pythDistance(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble())
}

fun pythDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
    return sqrt((x1 - x2).pow(2.0) + (y1 - y2).pow(2.0))
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

suspend fun <A> Iterable<A>.forEachParallel(f: suspend (A) -> Unit) = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

fun greatestCommonDenominator(a: Long, b: Long): Long = if (b == 0L) a else greatestCommonDenominator(b, a % b)
fun leastCommonMultiple(a: Long, b: Long): Long = a / greatestCommonDenominator(a, b) * b
fun leastCommonMultiple(nrs: List<Long>): Long = nrs.reduce(::leastCommonMultiple)
