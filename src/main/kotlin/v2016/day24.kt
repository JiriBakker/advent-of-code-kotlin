package v2016

import util.priorityQueueBy

import v2016.Day24.State

private object Day24 {
    class State(val x: Int, val y: Int, val steps: Int, val visitedLocations: List<Int> = listOf()) {
        val hash = "${x}_${y}_${visitedLocations.sorted().joinToString("-")}"
    }
}

private fun findLocation(grid: List<String>, nr: Int): Pair<Int, Int> {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == nr.toString().first()) {
                return x to y
            }
        }
    }
    error("Unable to find $nr in grid")
}

private fun countLocations(grid: List<String>): Int {
    var count = 0
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            val locationNr = grid[y][x].toString().toIntOrNull()

            if (locationNr != null && locationNr != 0) {
                count++
            }
        }
    }
    return count
}

private fun findPath(grid: List<String>, isAllowedFinish: (Int, Int) -> Boolean = { _, _ -> true }): Int {
    val (startX, startY) = findLocation(grid, 0)
    val nrOfLocations = countLocations(grid)

    val visited = mutableSetOf<String>()
    val toVisit = priorityQueueBy<State> { it.steps }
    toVisit.add(State(startX, startY, 0))

    while (toVisit.isNotEmpty()) {
        val state = toVisit.poll()

        if (state.visitedLocations.size == nrOfLocations && isAllowedFinish(state.x, state.y)) {
            return state.steps
        }

        if (!visited.add(state.hash)) {
            continue
        }

        listOf(
            state.x - 1 to state.y,
            state.x + 1 to state.y,
            state.x to state.y - 1,
            state.x to state.y + 1
        )
            .filter { (x, y) -> y >= 0 && y < grid.size && x >= 0 && x < grid[y].length && grid[y][x] != '#' }
            .forEach { (x, y) ->
                toVisit.add(
                    if (grid[y][x] != '.' && grid[y][x] != '0')
                        State(x, y, state.steps + 1, state.visitedLocations.plus(grid[y][x].toString().toInt()).distinct())
                    else
                        State(x, y, state.steps + 1, state.visitedLocations)
                )
            }
    }

    error("No path found that reaches all locations")
}

fun day24a(input: List<String>): Int {
    return findPath(input)
}

fun day24b(input: List<String>): Int {
    return findPath(input) { x, y -> input[y][x] == '0' }
}