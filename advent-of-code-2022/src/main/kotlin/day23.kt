import Day23.countEmptyTiles
import Day23.parseElves
import Day23.runRoundsWhile
import util.max
import util.min

fun day23a(input: List<String>): Int {
    val initialGrid = input.parseElves()

    val (finalGrid, _) =
        initialGrid.runRoundsWhile { _, roundNr ->
            roundNr < 10
        }

    return finalGrid.countEmptyTiles()
}

fun day23b(input: List<String>): Int {
    val initialElves = input.parseElves()

    var prevElves = initialElves

    val (_, finalRoundNr) =
        initialElves.runRoundsWhile { elves, _ ->
            (elves != prevElves)
                .also { prevElves = elves }
        }


    return finalRoundNr
}

object Day23 {

    fun Map<Int, Map<Int, Int>>.runRoundsWhile(
        runWhilePredicate: (Map<Int, Map<Int, Int>>, Int) -> Boolean
    ): Pair<Map<Int, Map<Int, Int>>, Int> {
        var elves = this
        var moveRules = initialMoveRules

        var roundNr = 0
        do {
            val collisionTiles =
                elves.move(moveRules)
                    .filterCollisionPositions()

            elves = elves.move(moveRules, collisionTiles)

            moveRules = moveRules.drop(1).plus(moveRules.first())

            roundNr++
        } while (runWhilePredicate.invoke(elves, roundNr))

        return elves to roundNr
    }

    private fun Map<Int, Map<Int, Int>>.filterCollisionPositions() =
        this.flatMap { (y, row) ->
                row.filter { it.value > 1 }
                    .map { (x, _) -> x to y }
            }
            .toSet()

    private fun Map<Int, Map<Int, Int>>.move(
        moveRules: List<(Map<Int, Map<Int, Int>>, Int, Int) -> Pair<Int, Int>?>,
        collisionTiles: Set<Pair<Int, Int>> = emptySet()
    ): Map<Int, Map<Int, Int>> {
        val nextElves = mutableMapOf<Int, MutableMap<Int, Int>>()

        fun setElf(x: Int, y: Int) {
            val nextRow = nextElves.getOrPut(y) { mutableMapOf() }
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
            row.forEach { (x, _) ->
                if (hasNeighbours(x, y)) {
                    val potentialNextPos =
                        moveRules.firstNotNullOfOrNull { rule ->
                            rule.invoke(this, x, y)
                        }

                    if (potentialNextPos != null) {
                        val isCollisionTile = collisionTiles.contains(potentialNextPos)
                        if (!isCollisionTile) {
                            setElf(potentialNextPos.first, potentialNextPos.second)
                            return@forEach
                        }
                    }
                }

                setElf(x, y)
            }
        }

        return nextElves
    }

    fun Map<Int, Map<Int, Int>>.countEmptyTiles(): Int {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE

        this.forEach { (y, row) ->
            minY = min(minY, y)
            maxY = max(maxY, y)
            row.forEach { (x, _) ->
                minX = min(minX, x)
                maxX = max(maxX, x)
            }
        }

        return (minY .. maxY).sumOf { y ->
            (minX..maxX).count { x ->
                this[y]?.get(x) == null
            }
        }
    }

    fun List<String>.parseElves(): Map<Int, Map<Int, Int>> {
        val elves = mutableMapOf<Int, MutableMap<Int, Int>>()
        this.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') {
                    val row = elves.getOrPut(y) { mutableMapOf() }
                    row[x] = 1
                }
            }
        }
        return elves
    }

    private val moveNorthRule =
        { elves: Map<Int, Map<Int, Int>>, x: Int, y: Int ->
            val ne = elves[y - 1]?.get(x - 1)
            val n = elves[y - 1]?.get(x)
            val nw = elves[y - 1]?.get(x + 1)
            if (ne == null && n == null && nw == null) {
                x to (y - 1)
            } else {
                null
            }
        }

    private val moveSouthRule =
        { elves: Map<Int, Map<Int, Int>>, x: Int, y: Int ->
            val sw = elves[y + 1]?.get(x - 1)
            val s = elves[y + 1]?.get(x)
            val se = elves[y + 1]?.get(x + 1)
            if (sw == null && s == null && se == null) {
                x to (y + 1)
            } else {
                null
            }
        }

    private val moveWestRule =
        { elves: Map<Int, Map<Int, Int>>, x: Int, y: Int ->
            val nw = elves[y - 1]?.get(x - 1)
            val w = elves[y]?.get(x - 1)
            val sw = elves[y + 1]?.get(x - 1)
            if (nw == null && w == null && sw == null) {
                (x - 1) to y
            } else {
                null
            }
        }


    private val moveEastRule =
        { elves: Map<Int, Map<Int, Int>>, x: Int, y: Int ->
            val ne = elves[y - 1]?.get(x + 1)
            val e = elves[y]?.get(x + 1)
            val se = elves[y + 1]?.get(x + 1)
            if (ne == null && e == null && se == null) {
                (x + 1) to y
            } else {
                null
            }
        }

    private val initialMoveRules =
        listOf(
            moveNorthRule,
            moveSouthRule,
            moveWestRule,
            moveEastRule
        )
}