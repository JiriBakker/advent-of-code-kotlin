package util

import java.util.ArrayDeque
import java.util.PriorityQueue
import java.util.Stack

inline fun <T> Iterable<T>.sumOfLong(selector: (T) -> Long): Long {
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
    return selector(this.minByOrNull(selector)!!) to selector(this.maxByOrNull(selector)!!)
}

inline fun <T> List<T>.getBounds(minSelector: (T) -> Int, maxSelector: ((T) -> Int)): Pair<Int, Int> {
    return Pair(minSelector(this.minByOrNull(minSelector)!!), maxSelector(this.maxByOrNull(maxSelector)!!))
}

inline fun <T> List<T>.forEachCombinationPair(action: (T, T) -> Unit) {
    for (i1 in this.indices) {
        for (i2 in (i1 + 1) until this.size) {
            action(this[i1], this[i2])
        }
    }
}

inline fun <T, U> List<T>.mapCombinationPairs(map: (T, T) -> U) =
    (this.indices).flatMap { i1 ->
        ((i1 + 1) until this.size).map { i2 ->
            map(this[i1], this[i2])
        }
    }

inline fun <T, U> List<T>.mapCombinationPairsWithIndex(map: (Pair<Int,T>, Pair<Int,T>) -> U) =
    (this.indices).flatMap { i1 ->
        ((i1 + 1) until this.size).map { i2 ->
            map(i1 to this[i1], i2 to this[i2])
        }
    }

fun <T> List<T>.getCombinationPairs(): Sequence<Pair<T, T>> {
    val list = this
    return sequence {
        list.indices.flatMap { i1 ->
            ((i1 + 1) until list.size).map { i2 ->
                yield(list[i1] to list[i2])
            }
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

fun <T> priorityQueueBy(valueSelector: (T) -> Comparable<*>): PriorityQueue<T> {
    return PriorityQueue { a, b ->
        @Suppress("UNCHECKED_CAST")
        (valueSelector(a) as? Comparable<Any>)
            ?.compareTo(valueSelector(b))
            ?: throw Exception("Unable to cast to Comparable<Any>")
    }
}

fun <T> Collection<T>.allEqual(): Boolean {
    return this.zipWithNext().all { it.first == it.second }
}

fun <K,V> Map<K,V>.sumOf(valueSelector: (Map.Entry<K,V>) -> Int): Int {
    return this.map(valueSelector).sum()
}

fun <K,V> Map<K,V>.sumOfLong(valueSelector: (Map.Entry<K,V>) -> Long): Long {
    return this.map(valueSelector).sum()
}

fun <K,V> Map<K,V>.filteredValues(predicate: (Map.Entry<K,V>) -> Boolean) =
    this.filter(predicate).map { it.value }

fun <T> Iterator<T>.nextOrNull(): T? {
    return if (this.hasNext()) this.next() else null
}

fun <T> ArrayDeque<T>.popOrNull(): T? {
    return if (this.isNotEmpty()) this.pop() else null
}

operator fun <T> List<T>.component6(): T {
    return get(5)
}

operator fun <T> List<T>.component7(): T {
    return get(6)
}

fun Collection<Int>.product(): Int {
    return this.fold(1, { total, it -> total * it })
}

fun Collection<Long>.product(): Long {
    return this.fold(1L, { total, it -> total * it })
}

fun <T> Collection<T>.productOfLong(selector: (T) -> Long): Long {
    return this.fold(1L) { total, it -> total * selector(it) }
}

fun <T> Collection<T>.productOf(selector: (T) -> Int): Int {
    return this.fold(1) { total, it -> total * selector(it) }
}


fun <T> List<List<T>>.rotatedRight(): List<List<T>> {
    val height = this.size
    val width = this.first().size
    return this.rotateApply(height, width) { x, y ->
        y to (height - 1 - x)
    }
}

fun <T> List<List<T>>.rotatedLeft(): List<List<T>> {
    val height = this.size
    val width = this.first().size
    return this.rotateApply(height, width) { x, y ->
        (width - 1 - y) to x
    }
}

fun <T> List<List<T>>.rotatedTwice(): List<List<T>> {
    val height = this.size
    val width = this.first().size
    return this.rotateApply(width, height) { x, y ->
        (width - 1 - x) to (height - 1 - y)
    }
}

private fun <T> List<List<T>>.rotateApply(
    width: Int,
    height: Int,
    indexConverter: (Int, Int) -> Pair<Int, Int>
): List<List<T>> {
    val grid = mutableListOf<MutableList<T>>()
    for (y in 0 until height) {
        grid.add(mutableListOf())
        for (x in 0 until width) {
            val (sourceX, sourceY) = indexConverter(x, y)
            grid[y].add(this[sourceY][sourceX])
        }
    }
    return grid
}

fun <T> List<List<T>>.flippedHorizontal(): List<List<T>> {
    val grid = this.map { it.toMutableList() }
    val height = this.size
    val width = this.first().size
    for (y in 0 until height) {
        for (x in 0 until width) {
            grid[y][x] = this[y][width - 1 - x]
        }
    }
    return grid
}

fun <T> List<List<T>>.flipVertical(): List<List<T>> {
    val grid = this.map { it.toMutableList() }
    val height = this.size
    val width = this.first().size
    for (y in 0 until height) {
        for (x in 0 until width) {
            grid[y][x] = this[height - 1 - y][x]
        }
    }
    return grid
}

fun <T> MutableMap<T, Int>.inc(key: T) {
    this[key] = this.getOrDefault(key, 0) + 1
}

fun <T> Map<T, Int>.findKeyForMaxValue(): T? {
    var maxValue = Int.MIN_VALUE
    var maxKeys = mutableListOf<T>()
    this.entries.forEach { (key, value) ->
        if (value > maxValue) {
            maxValue = value
            maxKeys = mutableListOf(key)
        } else if (value == maxValue) {
            maxKeys.add(key)
        }
    }
    return maxKeys.singleOrNull()
}

fun <T> MutableList<T>.insertAt(index: Int, element: T) {
    add(this[size - 1])
    for (i in size - 2 downTo index + 1) {
        this[i] = this[i - 1]
    }
    this[index] = element
}

fun <T,U> Pair<T,U>.flip() = second to first

fun <T> Collection<T>.countDistinct() = distinct().count()

fun <T> Stack<T>.popMultiple(amount: Int) =
    (0 until amount).map { this.pop() }

fun <T> List<T>.toPair() =
    this[0] to this[1]

