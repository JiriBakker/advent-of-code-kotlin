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

fun readInputLines(fileName: String): List<String> {
    return File("input/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}
