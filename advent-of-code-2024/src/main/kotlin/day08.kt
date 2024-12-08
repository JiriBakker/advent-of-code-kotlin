fun day08a(input: List<String>) =
    input
        .collectCharPositions()
        .computeAntinodePositions(maxX = input[0].length - 1, maxY = input.size - 1)
        .size

fun day08b(input: List<String>) =
    input
        .collectCharPositions()
        .computeHarmonicAntinodePositions(maxX = input[0].length - 1, maxY = input.size - 1)
        .size

private fun List<String>.collectCharPositions(): Map<Char, List<Pair<Int, Int>>> {
    val letterPositions = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (y in this.indices) {
        for (x in this[y].indices) {
            val c = this[y][x]
            if (c != '.') {
                letterPositions.getOrPut(c) { mutableListOf() }.add(y to x)
            }
        }
    }
    return letterPositions
}

private fun Map<Char, List<Pair<Int, Int>>>.computeAntinodePositions(maxX: Int, maxY: Int): Set<Pair<Int,Int>> {
    val antinodePositions = mutableSetOf<Pair<Int, Int>>()

    for ((_, positions) in this.entries) {
        for (i in positions.indices) {
            for (j in i+1 until positions.size) {
                val (y1, x1) = positions[i]
                val (y2, x2) = positions[j]

                val dx1 = x2 - x1
                val dy1 = y2 - y1
                val ax1 = x2 + dx1
                val ay1 = y2 + dy1

                if (ax1 in 0..maxX && ay1 in 0..maxY) {
                    antinodePositions.add(ax1 to ay1)
                }

                val dx2 = x1 - x2
                val dy2 = y1 - y2
                val ax2 = x1 + dx2
                val ay2 = y1 + dy2

                if (ax2 in 0..maxX && ay2 in 0..maxY) {
                    antinodePositions.add(ax2 to ay2)
                }
            }
        }
    }
    return antinodePositions
}

private fun Map<Char, List<Pair<Int, Int>>>.computeHarmonicAntinodePositions(maxX: Int, maxY: Int): Set<Pair<Int,Int>> {
    val antinodePositions = mutableSetOf<Pair<Int, Int>>()

    for ((_, positions) in this.entries) {
        for (i in positions.indices) {
            for (j in i+1 until positions.size) {
                val (y1, x1) = positions[i]
                val (y2, x2) = positions[j]

                val dx1 = x2 - x1
                val dy1 = y2 - y1

                var curX = x2
                var curY = y2
                while (curX in 0..maxX && curY in 0..maxY) {
                    antinodePositions.add(curX to curY)
                    curX += dx1
                    curY += dy1
                }

                val dx2 = x1 - x2
                val dy2 = y1 - y2

                curX = x1
                curY = y1
                while (curX in 0..maxX && curY in 0..maxY) {
                    antinodePositions.add(curX to curY)
                    curX += dx2
                    curY += dy2
                }
            }
        }
    }
    return antinodePositions
}
