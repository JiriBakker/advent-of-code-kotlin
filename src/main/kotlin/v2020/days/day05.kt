package v2020.days.day05

private fun getBoardingPasses(input: List<String>): List<Pair<Int, Int>> {
    return input.map { line ->
        var rowRange = 0 .. 127
        line.take(7).forEach { char ->
            val mid = rowRange.first + (rowRange.count() / 2)
            if (char == 'B') {
                rowRange = mid .. rowRange.last
            } else {
                rowRange = rowRange.first until mid
            }
        }

        var colRange = 0 .. 7
        line.takeLast(3).forEach { char ->
            val mid = colRange.first + (colRange.count() / 2)
            if (char == 'R') {
                colRange = mid .. colRange.last
            } else {
                colRange = colRange.first until mid
            }
        }

        rowRange.first() to colRange.first()
    }
}

fun day05a(input: List<String>): Int {
    return getBoardingPasses(input).map { it.first * 8 + it.second }.max()!!
}

fun day05b(input: List<String>): Int {
    val boardingPasses = getBoardingPasses(input)

    val expectedCols = 0 .. 7

    val actualRow =
        boardingPasses
            .groupBy({ it.first }) { it.second }
            .entries

            // Ignore full rows
            .filter { it.value.size < 8 }

            // Ignore front row
            .sortedBy { it.key }.drop(1)

            .first()

    val missingCol = expectedCols.minus(actualRow.value).first()

    return actualRow.key * 8 + missingCol
}

