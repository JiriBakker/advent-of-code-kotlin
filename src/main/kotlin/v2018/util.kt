package v2018

import java.io.File

class Pos(val x: Int, val y: Int)

inline fun <T> List<T>.forEachCombinationPair(action: (Pair<T, T>) -> Unit) {
    val list = this
    return sequence {
        for (i1 in list.indices) {
            for (i2 in (i1 + 1) until list.size) {
                yield(Pair(list[i1], list[i2]))
            }
        }
    }.forEach(action)
}

inline fun <T, U : Comparable<U>> List<T>.sortMappedByDescending(selector: (T) -> U): List<T> {
    return this
        .map { Pair(it, selector(it)) }
        .sortedByDescending { it.second }
        .map { it.first }
}

inline fun <T> List<T>.getBounds(selector: (T) -> Int): Pair<Int, Int> {
    return Pair(selector(this.minBy(selector)!!), selector(this.maxBy(selector)!!))
}

inline fun <T> List<T>.getBounds(minSelector: (T) -> Int, maxSelector: ((T) -> Int)): Pair<Int, Int> {
    return Pair(minSelector(this.minBy(minSelector)!!), maxSelector(this.maxBy(maxSelector)!!))
}

fun readInputLines(fileName: String): List<String> {
    return File("input/2018/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}
