import kotlin.math.abs

fun day01a(input: List<String>): Int {
    val (list1, list2) = parseInput(input)

    return list1.sorted().zip(list2.sorted())
        .sumOf { (a, b) -> abs(a - b) }
}

fun day01b(input: List<String>): Int {
    val (list1, list2) = parseInput(input)

    val letterCounts = list2.groupBy { it }.mapValues { it.value.size }.toMap()

    return list1.sumOf { it * letterCounts.getOrDefault(it, 0) }
}

private fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> =
    input
        .map { str -> str.split("   ").map(String::toInt).let { it[0] to it[1] } }
        .unzip()