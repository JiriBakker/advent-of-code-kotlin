package v2019

import java.io.File

fun readInputLines(fileName: String): List<String> {
    return File("input/2019/$fileName").readLines()
}

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

