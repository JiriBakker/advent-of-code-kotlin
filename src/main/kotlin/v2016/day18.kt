package v2016

private fun generateTileRows(startRow: List<Boolean>): Sequence<List<Boolean>> {
    return generateSequence(startRow) { row ->
        row.indices.map { index ->
            (index > 0 && row[index - 1]) !=
                (index < row.size - 1 && row[index + 1])
        }
    }
}

private fun countSafeTiles(startRow: List<Boolean>, nrOfRows: Int): Int {
    return generateTileRows(startRow)
        .map { row -> row.count { !it } }
        .take(nrOfRows)
        .sum()
}

fun day18a(input: String, nrOfRows: Int = 40): Int {
    return countSafeTiles(input.map { it == '^' }, nrOfRows)
}

fun day18b(input: String, nrOfRows: Int = 400000): Int {
    return countSafeTiles(input.map { it == '^' }, nrOfRows)
}