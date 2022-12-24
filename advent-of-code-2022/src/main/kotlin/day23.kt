import Day23.computePotential
import Day23.moveElves
import Day23.parseGrid
import util.max
import util.min

fun day23a(input: List<String>): Int {
    val initialGrid = input.parseGrid()
    var grid = initialGrid

    var directionRules =
        listOf(
            { x: Int, y: Int ->
                val ne = grid[y - 1]?.get(x - 1)
                val n = grid[y - 1]?.get(x)
                val nw = grid[y - 1]?.get(x + 1)
                if (ne == null && n == null && nw == null) {
                    x to (y - 1)
                } else {
                    null
                }
            },
            { x: Int, y: Int ->
                val sw = grid[y + 1]?.get(x - 1)
                val s = grid[y + 1]?.get(x)
                val se = grid[y + 1]?.get(x + 1)
                if (sw == null && s == null && se == null) {
                    x to (y + 1)
                } else {
                    null
                }
            },
            { x: Int, y: Int ->
                val nw = grid[y - 1]?.get(x - 1)
                val w = grid[y]?.get(x - 1)
                val sw = grid[y + 1]?.get(x - 1)
                if (nw == null && w == null && sw == null) {
                    (x - 1) to y
                } else {
                    null
                }
            },
            { x: Int, y: Int ->
                val ne = grid[y - 1]?.get(x + 1)
                val e = grid[y]?.get(x + 1)
                val se = grid[y + 1]?.get(x + 1)
                if (ne == null && e == null && se == null) {
                    (x + 1) to y
                } else {
                    null
                }
            }
        )

    repeat(10) {
        //println("Round $it")
        val potentialGrid = grid.computePotential(directionRules)

        grid = grid.moveElves(directionRules, potentialGrid)

        directionRules = directionRules.drop(1).plus(directionRules.first())
    }

    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var minY = Int.MAX_VALUE
    var maxY = Int.MIN_VALUE

    grid.forEach { (y, row) ->
        minY = min(minY, y)
        maxY = max(maxY, y)
        row.forEach { (x, _) ->
            minX = min(minX, x)
            maxX = max(maxX, x)
        }
    }


    var emptyTiles = 0
    (minY .. maxY).forEach { y ->
        (minX..maxX).forEach { x ->
            if (grid[y]?.get(x) == null) {
                emptyTiles++
            }
        }
    }

    return emptyTiles
}

fun day23b(input: List<String>): Int {
    val initialGrid = input.parseGrid()
    var grid = initialGrid

    var directionRules =
        listOf(
            { x: Int, y: Int ->
                val ne = grid[y - 1]?.get(x - 1)
                val n = grid[y - 1]?.get(x)
                val nw = grid[y - 1]?.get(x + 1)
                if (ne == null && n == null && nw == null) {
                    x to (y - 1)
                } else {
                    null
                }
            },
            { x: Int, y: Int ->
                val sw = grid[y + 1]?.get(x - 1)
                val s = grid[y + 1]?.get(x)
                val se = grid[y + 1]?.get(x + 1)
                if (sw == null && s == null && se == null) {
                    x to (y + 1)
                } else {
                    null
                }
            },
            { x: Int, y: Int ->
                val nw = grid[y - 1]?.get(x - 1)
                val w = grid[y]?.get(x - 1)
                val sw = grid[y + 1]?.get(x - 1)
                if (nw == null && w == null && sw == null) {
                    (x - 1) to y
                } else {
                    null
                }
            },
            { x: Int, y: Int ->
                val ne = grid[y - 1]?.get(x + 1)
                val e = grid[y]?.get(x + 1)
                val se = grid[y + 1]?.get(x + 1)
                if (ne == null && e == null && se == null) {
                    (x + 1) to y
                } else {
                    null
                }
            }
        )

    var round = 1
    while (true) {
        //println("Round $it")
        val prevGrid = grid
        val potentialGrid = grid.computePotential(directionRules)

        grid = grid.moveElves(directionRules, potentialGrid)

        directionRules = directionRules.drop(1).plus(directionRules.first())

        val hasChanged =
            prevGrid.any { (y, prevRow) ->
                val row = grid[y]
                row == null || prevRow.any { (x, elf) ->
                    row[x] == null
                }
            }

        if (!hasChanged) {
            return round
        }
        round++
    }
}

object Day23 {

    fun Map<Int, Map<Int, Int>>.computePotential(
        directionRules: List<(Int, Int) -> Pair<Int, Int>?>
    ): Map<Int, Map<Int, Int>> {
        val potentialGrid = mutableMapOf<Int, MutableMap<Int, Int>>()

        fun setPotential(x: Int, y: Int) {
            val potentialRow = potentialGrid.getOrPut(y) { mutableMapOf() }
            potentialRow[x] = potentialRow.getOrDefault(x, 0) + 1
        }

        fun hasNeighbours(x: Int, y: Int) =
            this[y - 1]?.get(x) != null || // N
                this[y - 1]?.get(x + 1) != null || // NE
                this[y]?.get(x + 1) != null || // E
                this[y + 1]?.get(x + 1) != null || // SE
                this[y + 1]?.get(x) != null || // S
                this[y + 1]?.get(x - 1) != null || // SW
                this[y]?.get(x - 1) != null || // W
                this[y - 1]?.get(x - 1) != null // NW


        this.forEach { (y, row) ->
            row.forEach { (x, elf) ->
                if (hasNeighbours(x, y)) {
                    val potentialNextPos =
                        directionRules.firstNotNullOfOrNull { rule ->
                            rule.invoke(x, y)
                        }

                    if (potentialNextPos != null) {
                        setPotential(potentialNextPos.first, potentialNextPos.second)
                        return@forEach
                    }
                }

//                setPotential(x, y)
            }
        }

        return potentialGrid
    }

    fun Map<Int, Map<Int, Int>>.moveElves(
        directionRules: List<(Int, Int) -> Pair<Int, Int>?>,
        potentialGrid: Map<Int, Map<Int, Int>>
    ): Map<Int, Map<Int, Int>> {
        val nextGrid = mutableMapOf<Int, MutableMap<Int, Int>>()

        fun set(x: Int, y: Int) {
            //println("Setting $x $y")
            val nextRow = nextGrid.getOrPut(y) { mutableMapOf() }
            nextRow[x] = nextRow.getOrDefault(x, 0) + 1
        }

        fun hasNeighbours(x: Int, y: Int) =
            this[y - 1]?.get(x) != null || // N
                this[y - 1]?.get(x + 1) != null || // NE
                this[y]?.get(x + 1) != null || // E
                this[y + 1]?.get(x + 1) != null || // SE
                this[y + 1]?.get(x) != null || // S
                this[y + 1]?.get(x - 1) != null || // SW
                this[y]?.get(x - 1) != null || // W
                this[y - 1]?.get(x - 1) != null // NW


        this.forEach { (y, row) ->
            row.forEach { (x, elf) ->
                //print("Checking $x $y")
                if (hasNeighbours(x, y)) {
                    val potentialNextPos =
                        directionRules.firstNotNullOfOrNull { rule ->
                            rule.invoke(x, y)
                        }

                    if (potentialNextPos != null) {
                        val collidingElf = potentialGrid[potentialNextPos.second]?.get(potentialNextPos.first)
                        if (collidingElf == null || collidingElf <= 1) {
                            set(potentialNextPos.first, potentialNextPos.second)
                            return@forEach
                        }
                    }
                }

                set(x, y)
            }
        }

        return nextGrid
    }

    fun List<String>.parseGrid(): Map<Int, Map<Int, Int>> {
        val grid = mutableMapOf<Int, MutableMap<Int, Int>>()
        this.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    val row = grid.getOrPut(y) { mutableMapOf() }
                    row[x] = 1
                }
            }
        }
        return grid
    }
}