package util

inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun <T> Collection<T>.permute(maxSize: Int = Int.MAX_VALUE): List<List<T>> {
    fun permute(available: Collection<T>, used: List<T>): List<List<T>> {
        if (available.isEmpty() || used.size == maxSize) {
            return listOf(used)
        }

        return available.flatMap { current ->
            permute( available.minus(current), used + current)
        }
    }

    return permute(this, listOf())
}

fun <T> Collection<T>.combine(maxSize: Int): List<List<T>> {
    val available = this

    fun combine(used: List<T>): List<List<T>> {
        if (used.size == maxSize) {
            return listOf(used)
        }

        return available.flatMap { current: T -> combine( used + current) }
    }

    return combine(listOf())
}

inline fun <T, TOut : Comparable<TOut>> List<T>.getBounds(selector: (T) -> TOut): Pair<TOut, TOut> {
    return Pair(selector(this.minBy(selector)!!), selector(this.maxBy(selector)!!))
}

inline fun <T> List<T>.getBounds(minSelector: (T) -> Int, maxSelector: ((T) -> Int)): Pair<Int, Int> {
    return Pair(minSelector(this.minBy(minSelector)!!), maxSelector(this.maxBy(maxSelector)!!))
}

inline fun <T> List<T>.forEachCombinationPair(action: (T, T) -> Unit) {
    for (i1 in this.indices) {
        for (i2 in (i1 + 1) until this.size) {
            action(this[i1], this[i2])
        }
    }
}

inline fun <T, U : Comparable<U>> List<T>.sortMappedByDescending(selector: (T) -> U): List<T> {
    return this
        .map { Pair(it, selector(it)) }
        .sortedByDescending { it.second }
        .map { it.first }
}

fun Collection<String>.transpose(): Sequence<String> {
    val strings = this
    return sequence {
        strings.first().indices.forEach { index ->
            yield(strings.map { it[index] }.joinToString(""))
        }
    }
}

fun <T> Collection<T>.partitionIndexed(predicate: (IndexedValue<T>) -> Boolean): Pair<List<T>, List<T>> {
    val (first, second) = this.withIndex().partition(predicate)
    return first.map { it.value } to second.map { it.value }
}

fun <T> Collection<T>.multiplyBy(valueSelector: (T) -> Int): Int {
    return this.fold(1) { product, element -> product * valueSelector(element) }
}