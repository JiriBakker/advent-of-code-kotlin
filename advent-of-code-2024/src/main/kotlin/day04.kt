fun day04a(input: List<String>): Int {
    val masks =
        listOf(
            listOf(
                "XMAS"
            ),
            listOf(
                "SAMX"
            ),
            listOf(
                "X",
                "M",
                "A",
                "S"
            ),
            listOf(
                "S",
                "A",
                "M",
                "X"
            ),
            listOf(
                "S...",
                ".A..",
                "..M.",
                "...X"
            ),
            listOf(
                "...S",
                "..A.",
                ".M..",
                "X..."
            ),
            listOf(
                "X...",
                ".M..",
                "..A.",
                "...S"
            ),
            listOf(
                "...X",
                "..M.",
                ".A..",
                "S..."
            )
        )

    return masks.countMatches(input)
}

fun day04b(input: List<String>): Int {
    val masks =
        listOf(
            listOf(
                "M.M",
                ".A.",
                "S.S"
            ),
            listOf(
                "S.S",
                ".A.",
                "M.M"
            ),
            listOf(
                "M.S",
                ".A",
                "M.S"
            ),
            listOf(
                "S.M",
                ".A.",
                "S.M"
            )

        )

    return masks.countMatches(input)
}

private fun List<List<String>>.countMatches(input: List<String>) =
    input.indices.sumOf { y ->
        input[y].indices.sumOf { x ->
            this.count { mask -> mask.matches(x, y, input) }
        }
    }

private fun List<String>.matches(x: Int, y: Int, input: List<String>): Boolean {
    if (y + this.size > input.size) return false
    if (x + this[0].length > input[y].length) return false

    for (i in this.indices) {
        for (j in this[i].indices) {
            if (this[i][j] == '.') continue
            if (input[y + i][x + j] != this[i][j]) return false
        }
    }
    return true
}