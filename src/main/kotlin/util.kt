import java.io.File

inline fun <T> List<T>.forEachCombinationPair(action: (Pair<T, T>) -> Unit) {
    val list = this
    return sequence {
        for (i1 in 0 until list.size) {
            for (i2 in (i1 + 1) until list.size) {
                yield(Pair(list[i1], list[i2]))
            }
        }
    }.forEach(action)
}

inline fun <T> List<T>.getBounds(selector: (T) -> Int): Pair<Int, Int> {
    return Pair(selector(this.minBy(selector)!!), selector(this.maxBy(selector)!!))
}

inline fun <T> List<T>.getBounds(minSelector: (T) -> Int, maxSelector: ((T) -> Int)): Pair<Int, Int> {
    return Pair(minSelector(this.minBy(minSelector)!!), maxSelector(this.maxBy(maxSelector)!!))
}

fun readInputLines(fileName: String): List<String> {
    return File("input/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}
