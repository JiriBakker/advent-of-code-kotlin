import kotlin.math.abs

fun day01a(input: List<String>): Int {
    val (list1, list2) = parseInput(input)

    return list1.sorted().zip(list2.sorted())
        .sumDistances()
}

fun day01b(input: List<String>): Int {
    val (list1, list2) = parseInput(input)

    val nrCounts = list2.groupBy { it }.mapValues { it.value.size }.toMap()

    return list1.sumOf { it * nrCounts.getOrDefault(it, 0) }
}

private fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> =
    input
        .map { str -> str.split("   ").map(String::toInt).let { it[0] to it[1] } }
        .unzip()

private fun List<Pair<Int,Int>>.sumDistances() =
    sumOf { (a, b) -> abs(a - b) }