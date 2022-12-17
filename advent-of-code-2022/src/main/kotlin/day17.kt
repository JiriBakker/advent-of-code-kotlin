import Day17.dropRocks
import Day17.height

fun day17a(input: List<String>): Int {
    val tower = mutableMapOf<Int, MutableSet<Int>>()

    tower.dropRocks(2022, input[0])

    return tower.height()
}

fun day17b(input: List<String>): Long {
    val tower = mutableMapOf<Int, MutableSet<Int>>()

    val heightsReached = tower.dropRocks(6000, input[0])

    // Here be dragons...

    fun Map<Int, Set<Int>>.rowToString(y: Int) =
        (0 until 7).joinToString { if (this[y]?.contains(it) == true) "#" else "." }

    val maxRockNr = 1000000000000L
    var y = 1
    while (true) {
        val curRow = tower.rowToString(y)
        var cycleSize = 1
        do {
            val firstMatch = tower.rowToString(y + cycleSize)
            if (curRow == firstMatch) {
                var cycleNr = 2
                while (y + (cycleSize * cycleNr) < tower.height()) {
                    val match = tower.rowToString(y + (cycleSize * cycleNr))
                    if (curRow != match) {
                        break
                    }
                    cycleNr++
                }

                if (y + (cycleSize * cycleNr) >= tower.height()) {
                    var yOffset = 1
                    while (tower.rowToString(y + yOffset) == tower.rowToString(y + cycleSize + yOffset) && yOffset < cycleSize) {
                        val y2 = y + yOffset
                        val rockNr1 = heightsReached.indexOfFirst { it >= y2 }
                        val rockNr2 = heightsReached.indexOfFirst { it >= y2 + cycleSize }
                        if (heightsReached[rockNr1] == y2 && heightsReached[rockNr2] == y2 + cycleSize) {
                            val rockCycleSize = rockNr2 - rockNr1
                            if ((maxRockNr - rockNr1) % rockCycleSize == 0L) {
                                return ((maxRockNr - rockNr1) / rockCycleSize) * cycleSize + y2 - 1
                            }
                        }
                        yOffset++
                    }
                }
            }
            cycleSize++
        } while (cycleSize < 3000)

        y++
    }
}

object Day17 {
    private val rocks = listOf(
        listOf(
            "####"
        ),

        listOf(
            ".#.",
            "###",
            ".#."
        ),

        listOf(
            "..#",
            "..#",
            "###"
        ),

        listOf(
            "#",
            "#",
            "#",
            "#"
        ),

        listOf(
            "##",
            "##"
        )
    )

    fun MutableMap<Int, MutableSet<Int>>.height() =
        this.keys.maxOrNull() ?: 0

    fun MutableMap<Int, MutableSet<Int>>.dropRocks(amount: Int, jetPatternString: String): List<Int> {
        val jetPattern = sequence {
            while (true) {
                jetPatternString.forEach { yield(it) }
            }
        }.iterator()

        val tower = this
        val heightsReached = mutableListOf<Int>()

        repeat(amount) {
            val rock = rocks[it % rocks.size]
            var rockX = 2
            var rockY = tower.height() + 3 + rock.size

            fun canMoveDown(): Boolean {
                rock.forEachIndexed { yOffset, rockLine ->
                    val y = rockY - yOffset - 1
                    if (y == 0) {
                        return false
                    }
                    rockLine.forEachIndexed { xOffset, char ->
                        if (char == '#') {
                            val x = rockX + xOffset
                            if (tower[y]?.contains(x) == true) {
                                return false
                            }
                        }
                    }
                }
                return true
            }

            fun applyJet() {
                val jetDirection = jetPattern.next()

                val xDelta =
                    if (jetDirection == '<') -1
                    else 1

                val rockWidth = rock[0].length - 1
                if (rockX + xDelta < 0 || rockX + rockWidth + xDelta > 6) {
                    return
                }

                rock.forEachIndexed { yOffset, rockLine ->
                    rockLine.forEachIndexed { xOffset, char ->
                        if (char == '#' && tower[rockY - yOffset]?.contains(rockX + xOffset + xDelta) == true) {
                            return
                        }
                    }
                }

                rockX += xDelta
            }

            while (true) {
                applyJet()
                if (!canMoveDown()) {
                    break
                }
                rockY--
            }

            rock.forEachIndexed { yOffset, rockLine ->
                val y = rockY - yOffset
                rockLine.forEachIndexed { xOffset, char ->
                    if (char == '#') {
                        val x = rockX + xOffset
                        tower.getOrPut(y) { mutableSetOf() }.add(x)
                    }
                }
            }

            heightsReached.add(tower.height())
        }

        return heightsReached
    }
}