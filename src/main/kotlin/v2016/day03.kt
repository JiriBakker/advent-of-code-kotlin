package v2016

private fun isValidTriangle(a: Int, b: Int, c: Int): Boolean {
    return a + b > c && b + c > a && a + c > b
}

private fun parseInput(input: List<String>): List<Int> {
    return input.flatMap { line ->
            line
                .split(" ")
                .filter { it != "" }
                .map(String::toInt)
        }
}

fun day03a(input: List<String>): Int {
    return parseInput(input)
        .chunked(3)
        .count { (a, b, c) -> isValidTriangle(a, b, c) }
}

fun day03b(input: List<String>): Int {
    return parseInput(input)
        .chunked(9)
        .flatMap { nrs ->
            listOf(
                listOf(nrs[0], nrs[3], nrs[6]),
                listOf(nrs[1], nrs[4], nrs[7]),
                listOf(nrs[2], nrs[5], nrs[8])
            )
        }
        .count { (a, b, c) -> isValidTriangle(a, b, c) }
}