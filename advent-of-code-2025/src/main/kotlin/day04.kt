import kotlin.collections.map

fun day04a(input: List<String>): Int {
    return input.countPaper() - input.removePaper().countPaper()
}

fun day04b(input: List<String>): Int {
    var grid = input
    var prevPaperCount = Int.MAX_VALUE
    var curPaperCount = prevPaperCount

    while (true) {
        grid = grid.removePaper()

        curPaperCount = grid.countPaper()
        if (prevPaperCount == curPaperCount) break

        prevPaperCount = curPaperCount
    }

    return input.countPaper() - curPaperCount
}

private fun List<String>.removePaper(): List<String> {
    return this.indices.map { y: Int ->
        this[y].indices.map { x: Int ->
            if (this[y][x] != '@') return@map '.'

            val neighbourPaperCount = listOf(
                (x - 1) to (y - 1),
                (x) to (y - 1),
                (x + 1) to (y - 1),
                (x - 1) to (y),
                (x + 1) to (y),
                (x - 1) to (y + 1),
                (x) to (y + 1),
                (x + 1) to (y + 1),
            )
                .map { (nx, ny) -> this.hasPaper(nx, ny) }
                .count { it }

            if (neighbourPaperCount < 4) '.' else '@'
        }.joinToString("")
    }
}
private fun List<String>.hasPaper(x: Int, y: Int) =
    y in this.indices && x in this[y].indices && this[y][x] == '@'

private fun List<String>.countPaper() =
    this.sumOf { row -> row.count { it == '@' } }