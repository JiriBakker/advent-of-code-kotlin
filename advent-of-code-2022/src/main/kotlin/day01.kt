fun day01a(input: List<String>) =
    input.computeSums().maxOrNull()!!

fun day01b(input: List<String>) =
    input
        .computeSums()
        .sortedDescending()
        .take(3)
        .sum()

private fun List<String>.computeSums() =
    this
        .fold(0L to emptyList<Long>()) { (curSum, sums), line ->
            if (line == "") {
                0L to sums.plus(curSum)
            } else {
                (curSum + line.toLong()) to sums
            }
        }
        .second