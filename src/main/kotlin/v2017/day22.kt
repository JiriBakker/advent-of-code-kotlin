package v2017

import v2017.Day22.Direction
import v2017.Day22.Pos

private object Day22 {
    data class Pos(val x: Int, val y: Int) {
        fun up(): Pos = Pos(x, y - 1)
        fun right(): Pos = Pos(x + 1, y)
        fun down(): Pos = Pos(x, y + 1)
        fun left(): Pos = Pos(x - 1, y)
    }

    enum class Direction {
        UP {
            override fun turnLeft(): Direction = Direction.LEFT
            override fun turnRight(): Direction = Direction.RIGHT
        },
        RIGHT {
            override fun turnLeft(): Direction = Direction.UP
            override fun turnRight(): Direction = Direction.DOWN
        },
        DOWN {
            override fun turnLeft(): Direction = Direction.RIGHT
            override fun turnRight(): Direction = Direction.LEFT
        },
        LEFT {
            override fun turnLeft(): Direction = Direction.DOWN
            override fun turnRight(): Direction = Direction.UP
        };

        abstract fun turnLeft(): Direction
        abstract fun turnRight(): Direction
    }
}

private fun iterateVirus(
    inputGrid: List<String>,
    nrOfIterations: Int,
    applyRules: (Pos, Direction, MutableSet<Pos>, MutableSet<Pos>, MutableSet<Pos>) -> Pair<Direction, Boolean>
): Int {
    val originOffset = inputGrid.size / 2

    val infectedCells = inputGrid.withIndex().flatMap { (y, row) ->
        row.mapIndexedNotNull { x, cell ->
            if (cell == '#') Pos(x - originOffset, y - originOffset)
            else null
        }
    }.toMutableSet()
     val weakenedCells = mutableSetOf<Pos>()
    val flaggedCells = mutableSetOf<Pos>()

    var pos = Pos(0, 0)
    var direction = Direction.UP
    var infectionsCaused = 0

    repeat(nrOfIterations) {
        val (nextDirection, wasInfectionCaused) =
            applyRules(pos, direction, weakenedCells, infectedCells, flaggedCells)

        direction = nextDirection
        if (wasInfectionCaused) infectionsCaused++

        pos = when (direction) {
            Direction.UP -> pos.up()
            Direction.RIGHT -> pos.right()
            Direction.DOWN -> pos.down()
            Direction.LEFT -> pos.left()
        }
    }

    return infectionsCaused
}

fun day22a(input: List<String>, nrOfIterations: Int = 10000): Int {
    return iterateVirus(input, nrOfIterations) { pos, direction, _, infectedCells, _ ->
        if (infectedCells.remove(pos)) {
            direction.turnRight() to false
        } else {
            infectedCells.add(pos)
            direction.turnLeft() to true
        }
    }
}

fun day22b(input: List<String>, nrOfIterations: Int = 10000000): Int {
    return iterateVirus(input, nrOfIterations) { pos, direction, weakenedCells, infectedCells, flaggedCells ->
        when {
            weakenedCells.remove(pos) -> { // Was weakened, becomes infected
                infectedCells.add(pos)
                direction to true
            }
            infectedCells.remove(pos) -> { // Was infected, becomes flagged
                flaggedCells.add(pos)
                direction.turnRight() to false
            }
            flaggedCells.remove(pos) -> { // Was flagged, becomes clean
                direction.turnLeft().turnLeft()  to false
            }
            else -> { // Was clean, becomes weakened
                weakenedCells.add(pos)
                direction.turnLeft()  to false
            }
        }
    }
}
