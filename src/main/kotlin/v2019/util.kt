package v2019

import java.io.File

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

inline fun Iterable<Int>.toOrderedGroups(): List<List<Int>> {
    val initial: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())
    return this.fold(initial, { acc, cur ->
        if (acc.last().isEmpty() || acc.last().first() == cur) {
            acc.last().add(cur)
        }
        else {
            acc.add(mutableListOf(cur))
        }
        acc
    })
}

inline fun Iterable<Int>.countDigits(): List<Pair<Int, Int>> {
    val groups = this.groupBy { it }
    return this.map { Pair(it, groups[it]!!.size) }
}
