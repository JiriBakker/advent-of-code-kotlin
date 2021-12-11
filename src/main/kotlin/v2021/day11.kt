package v2021

private fun List<List<Int>>.getNeighbourPositions(x: Int, y: Int) =
    listOfNotNull(
        x - 1 to y - 1,
        x - 1 to y,
        x - 1 to y + 1,
        x     to y - 1,
        x     to y + 1,
        x + 1 to y - 1,
        x + 1 to y,
        x + 1 to y + 1
    ).filter { (x, y) ->
        x >= 0 && x < this[0].size && y >= 0 && y < this.size
    }

private fun List<MutableList<Int>>.resetFlashed() {
    forEach { row ->
        row.replaceAll {
            if (it == -1) 0 else it
        }
    }
}

private fun List<MutableList<Int>>.iterate(): Sequence<Int> {
    val octopi = this

    var flashDelta = 0

    fun increaseEnergy(x: Int, y: Int) {
        if (octopi[y][x] == -1) return // Already flashed

        octopi[y][x] = octopi[y][x] + 1

        if (octopi[y][x] == 10) {
            octopi[y][x] = -1 // Mark flashed

            flashDelta++

            getNeighbourPositions(x, y)
                .forEach { (neighbourX, neighbourY) ->
                    increaseEnergy(neighbourX, neighbourY)
                }
        }
    }

    return sequence {
        while (true) {
            (octopi.indices).forEach { y ->
                (octopi[y].indices).forEach { x ->
                    increaseEnergy(x, y)
                }
            }

            yield(flashDelta)
            flashDelta = 0

            resetFlashed()
        }
    }
}

fun day11a(input: List<String>): Int {
    val octopi = input.map { row -> row.map { it.toString().toInt() }.toMutableList() }

    return octopi.iterate().take(100).sum()
}

fun day11b(input: List<String>): Int {
    val octopi = input.map { row -> row.map { it.toString().toInt() }.toMutableList() }

    return octopi
        .iterate()
        .takeWhile { it < 100 }
        .count() + 1
}