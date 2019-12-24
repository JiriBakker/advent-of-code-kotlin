package v2019.days.day24

import v2019.util.pow
import v2019.util.sumByLong

fun day24a(input: List<String>): Long {
    fun computeBiodiversity(x: Int, y: Int): Long = 2L.pow(y * 5L + x)

    var grid = input.mapIndexed { y, line -> line.mapIndexed { x, cell -> if (cell == '#') computeBiodiversity(x, y) else 0L }.toLongArray() }

    fun countAdjacentBugs(x: Int, y: Int): Int {
        fun hasBug(x: Int, y: Int): Boolean = (grid.getOrNull(y)?.getOrNull(x) ?: 0) > 0
        return (if (hasBug(x, y + 1)) 1 else 0) +
            (if (hasBug(x, y - 1)) 1 else 0) +
            (if (hasBug(x + 1, y)) 1 else 0) +
            (if (hasBug(x - 1, y)) 1 else 0)
    }

    val prevBiodiversities = mutableSetOf<Long>()

    while (true) {
        grid = grid.mapIndexed { y, row ->
            row.mapIndexed { x, biodiversity ->
                val nrOfAdjacentBugs = countAdjacentBugs(x, y)
                if (nrOfAdjacentBugs == 1 || (biodiversity == 0L && nrOfAdjacentBugs == 2)) computeBiodiversity(x, y)
                else 0L
            }.toLongArray()
        }
        val biodiversity = grid.sumByLong(LongArray::sum)
        if (prevBiodiversities.contains(biodiversity)) {
            return biodiversity
        }
        prevBiodiversities.add(biodiversity)
    }
}

fun day24b(input: List<String>): Long {
    return 0
}
