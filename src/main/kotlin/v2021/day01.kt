package v2021

private fun <T> List<T>.zipWithOffset(offset: Int): Sequence<Pair<T,T>> {
    val list = this
    return sequence {
        for (i in offset until size) {
            yield(list[i - offset] to list[i])
        }
    }
}

private fun List<Long>.countIncreases(offset: Int) =
    zipWithOffset(offset).count { it.second > it.first }

fun day01a(input: List<String>) =
    input
        .map(String::toLong)
        .countIncreases(1)

fun day01b(input: List<String>) =
    input
        .map(String::toLong)
        .countIncreases(3)
