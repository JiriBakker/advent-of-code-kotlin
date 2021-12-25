package v2021

private typealias CucumberGrid = Map<Int, Map<Int, Char>>

private fun List<String>.parseCucumberGrid() =
    withIndex().associate { (y, line) ->
        y to line.withIndex().mapNotNull { (x, char) ->
            if (char != '.') x to char
            else null
        }.toMap()
    }

fun day25a(input: List<String>): Int {
    val height = input.size
    val width  = input[0].length

    var grid = input.parseCucumberGrid()

    var steps = 0
    do {
        val nextGrid = mutableMapOf<Int, MutableMap<Int, Char>>()

        var hasMoved = false

        fun CucumberGrid.isLocationEmpty(x: Int, y: Int) =
            get(y)?.containsKey(x) != true

        fun canEastMoveInto(x: Int, y: Int) =
            grid.isLocationEmpty(x, y)

        fun canSouthMoveInto(x: Int, y: Int) =
            grid[y]?.get(x) != 'v' && nextGrid.isLocationEmpty(x, y)

        fun applyMoves(
            cucumber: Char,
            isMovePossible: (Int, Int) -> Boolean,
            getNextX: (Int) -> Int = { it },
            getNextY: (Int) -> Int = { it },
        ) {
            grid.forEach { (y, row) ->
                row.forEach { (x, char) ->
                    if (char == cucumber) {
                        val xNext = getNextX(x)
                        val yNext = getNextY(y)

                        if (isMovePossible(xNext, yNext)) {
                            nextGrid.getOrPut(yNext) { mutableMapOf() }[xNext] = cucumber
                            hasMoved = true
                        } else {
                            nextGrid.getOrPut(y) { mutableMapOf() }[x] = cucumber
                        }
                    }
                }
            }
        }

        applyMoves('>', ::canEastMoveInto, getNextX = { x -> (x + 1) % width })
        applyMoves('v', ::canSouthMoveInto, getNextY = { y -> (y + 1) % height })

        grid = nextGrid

        steps++
    } while (hasMoved)

    return steps
}

