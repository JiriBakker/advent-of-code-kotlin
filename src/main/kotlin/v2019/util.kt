package v2019.util

import java.io.File
import java.math.BigInteger
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

fun Int.safeMod(mod: Int): Int {
    return (this + mod) % mod
}

fun Long.safeMod(mod: Long): Long {
    var result = this
    do {
        result = (result + mod) % mod
    } while (result < 0)
    return result
}

operator fun BigInteger.rem(m: Long): BigInteger = this.mod(BigInteger.valueOf(m))
operator fun BigInteger.times(other: Long): BigInteger = this.times(BigInteger.valueOf(other))
operator fun BigInteger.times(other: Int): BigInteger = this.times(BigInteger.valueOf(other.toLong()))
operator fun BigInteger.plus(other: Int): BigInteger = this.plus(BigInteger.valueOf(other.toLong()))
operator fun Int.minus(other: BigInteger): BigInteger = BigInteger.valueOf(this.toLong()).minus(other)
fun BigInteger.modPow(e: Long, m: Long): BigInteger = this.modPow(BigInteger.valueOf(e), BigInteger.valueOf(m))
fun BigInteger.modInverse(m: Long): BigInteger = this.modInverse(BigInteger.valueOf(m))
fun Long.modInverse(m: Long): BigInteger = BigInteger.valueOf(this).modInverse(BigInteger.valueOf(m))
fun Int.modInverse(m: Long): BigInteger = BigInteger.valueOf(this.toLong()).modInverse(BigInteger.valueOf(m))
fun Long.pow(e: Long): Long = this.toDouble().pow(e.toDouble()).toLong()

