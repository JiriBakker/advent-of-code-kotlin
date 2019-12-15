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