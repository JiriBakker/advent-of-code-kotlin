package v2021

private fun <T> List<T>.zipWithOffset(offset: Int): Sequence<Pair<T,T>> {
    val list = this
    return sequence {
        for (i in offset until size) {
            yield(list[i - offset] to list[i])
        }
    }
}

fun day01a(input: List<String>): Int {
    return input
        .map(String::toLong)
        .zipWithOffset(1)
        .count { it.second > it.first }
}

fun day01b(input: List<String>): Int {
    return input
        .map(String::toLong)
        .zipWithOffset(3)
        .count { it.second > it.first }
}