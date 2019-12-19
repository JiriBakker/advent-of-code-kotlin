package v2015.util

import java.io.File

fun readInputLines(fileName: String): List<String> {
    return File("input/2015/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}

fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

fun min(vararg ints: Int): Int {
    return ints.min()!!
}

fun max(vararg ints: Int): Int {
    return ints.max()!!
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Int.safeMod(mod: Int): Int {
    return (this + mod) % mod
}

fun <T> Collection<T>.permute(): List<List<T>> {
    fun <T> permute(available: Collection<T>, used: List<T>): List<List<T>> {
        if (available.isEmpty()) {
            return listOf(used)
        }

        return available.flatMap { current ->
            permute( available.minus(current), used + current)
        }
    }

    return permute(this, listOf())
}


fun <T> Collection<T>.combine(size: Int): List<List<T>> {
    val available = this

    fun combine(used: List<T>): List<List<T>> {
        if (used.size == size) {
            return listOf(used)
        }

        return available.flatMap { current: T -> combine( used + current) }
    }

    return combine(listOf())
}