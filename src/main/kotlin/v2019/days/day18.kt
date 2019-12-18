package v2019.days.day18

import java.util.PriorityQueue

private data class State(val x: Int, val y: Int, val steps: Long, val keys: Set<Char> = emptySet()) {
    val hash = "${x}_${y}_${keys.sorted().joinToString("")}"
}

private data class State2(val x: Int, val y: Int, val steps: Long, val keys: Map<Char, Long> = emptyMap(), val doors: Map<Char, Long> = emptyMap(), val dependencies: Map<Char, MutableList<Char>> = emptyMap()) {
    val hash =
        "${x}_" +
            "${y}_" +
            "${keys.keys.joinToString("")}_"// +
            // "${doors.keys.sorted().joinToString("")}_"// +
            // "${dependencies.map { (key, doors) -> "$key:[${doors.joinToString(",")}"}}"
}

private fun findInGrid(condition: (Char) -> Boolean, grid: List<CharArray>): Sequence<Pair<Int, Int>> {
    return sequence {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (condition(grid[y][x])) {
                    yield(x to y)
                }
            }
        }
    }
}



fun day18a(input: List<String>): Long {
    val grid = input.map(String::toCharArray)

    val (startX, startY) = findInGrid({ it == '@' }, grid).first()
    val nrOfKeys = findInGrid(Char::isLowerCase, grid).count()

    val toVisit = PriorityQueue<State> { a, b -> a.steps.compareTo(b.steps) }
    val visited = mutableSetOf<String>()

    toVisit.add(State(startX, startY, 0, emptySet()))

    fun canAccessFrom(from: State, x: Int, y: Int): Boolean {
        if (y < 0 || y >= grid.size || x < 0 || x >= grid[y].size) {
            return false
        }

        val cell = grid[y][x]
        if (cell == '#') {
            return false
        }

        if (cell == '.' || cell == '@' || cell.isLowerCase()) {
            return true
        }

        return from.keys.contains(cell.toLowerCase())
    }

    fun addIfViable(x: Int, y: Int, from: State) {
        if (canAccessFrom(from, x, y)) {
            val cell = grid[y][x]
            val state =
                if (cell.isLetter() && cell.isLowerCase() && !from.keys.contains(cell)) {
                    State(x, y, from.steps + 1, from.keys.plus(cell))
                } else {
                    State(x, y, from.steps + 1, from.keys)
                }

            if (!visited.contains(state.hash)) {
                toVisit.add(state)
            }
        }
    }

    while (toVisit.isNotEmpty()) {
        val curCell = toVisit.poll()

        if (curCell.keys.size == nrOfKeys) {
            return curCell.steps
        }

        if (visited.contains(curCell.hash)) {
            continue
        }

        visited.add(curCell.hash)

        addIfViable(curCell.x + 1, curCell.y, curCell)
        addIfViable(curCell.x - 1, curCell.y, curCell)
        addIfViable(curCell.x, curCell.y + 1, curCell)
        addIfViable(curCell.x, curCell.y - 1, curCell)
    }

    error("No possible path to all keys")
}

private fun findPath(x: Int, y: Int, grid: List<CharArray>): List<State2> {
    val visited = mutableSetOf<String>()
    val toVisit = PriorityQueue<State2> { a, b -> a.steps.compareTo(b.steps) }
    toVisit.add(State2(x, y, 0))

    fun canAccessFrom(x: Int, y: Int): Boolean {
        return y >= 0 &&
            y < grid.size &&
            x >= 0 &&
            x < grid[y].size &&
            grid[y][x] != '#'
    }

    fun addIfViable(x: Int, y: Int, from: State2) {
        if (canAccessFrom(x, y)) {
            val cell = grid[y][x]

            val doors = from.doors.toMutableMap()
            if (cell.isUpperCase()) {
                doors[cell] = from.steps + 1
            }

            val keys = from.keys.toMutableMap()
            val dependencies = from.dependencies.toMutableMap()

            if (cell.isLowerCase() && !from.keys.contains(cell)) {
                val depsForCell = dependencies.getOrPut(cell, { mutableListOf() })
                depsForCell.addAll(doors.keys.filter { !keys.keys.contains(it.toLowerCase())})
                if (depsForCell.contains(cell.toUpperCase())) {
                    return
                }

                keys[cell] = from.steps + 1
            }

            val state = State2(x, y, from.steps + 1, keys, doors, dependencies)
            if (!visited.contains(state.hash)) {
                toVisit.add(state)
            }
        }
    }

    val keyPaths = mutableListOf<State2>()
    while (toVisit.isNotEmpty()) {
        val curState = toVisit.poll()

        if (visited.contains(curState.hash)) {
            continue
        }
        visited.add(curState.hash)

        if (grid[curState.y][curState.x].isLowerCase()) {
            keyPaths.add(curState)
        }

        addIfViable(curState.x + 1, curState.y, curState)
        addIfViable(curState.x - 1, curState.y, curState)
        addIfViable(curState.x, curState.y + 1, curState)
        addIfViable(curState.x, curState.y - 1, curState)
    }
    return keyPaths
}

fun day18b(input: List<String>): Long {
    val grid = input.map(String::toCharArray)

    val (startX, startY) = findInGrid({ it == '@' }, grid).first()

    grid[startY - 1][startX - 1] = '@'
    grid[startY - 1][startX] = '#'
    grid[startY - 1][startX + 1] = '@'
    grid[startY][startX - 1] = '#'
    grid[startY][startX] = '#'
    grid[startY][startX + 1] = '#'
    grid[startY + 1][startX - 1] = '@'
    grid[startY + 1][startX] = '#'
    grid[startY + 1][startX + 1] = '@'

    val keyPaths = mutableListOf(
        findPath(startX - 1, startY - 1, grid),
        findPath(startX + 1, startY - 1, grid),
        findPath(startX - 1, startY + 1, grid),
        findPath(startX + 1, startY + 1, grid)
    )

    val bestStates = keyPaths.map { states ->
        val maxKeys = states.map { it.keys.size }.max() ?: 0
        states.filter { it.keys.size == maxKeys && it.dependencies.none { (key, doors) -> doors.contains(key.toUpperCase()) } }
    }

    // val collectedKeys = mutableSetOf<Char>()
    // val stepCounts = mutableListOf<Long>(0, 0, 0, 0)
    // while (collectedKeys.size < nrOfKeys) {
    //     for (i in 0 until 4) {
    //         val reachablePaths = bestStates[i].filter { states ->
    //
    //             !collectedKeys.contains(key) && path.second.keys.all { collectedKeys.contains(it.toLowerCase()) } }
    //
    //         collectedKeys.addAll(reachablePaths.keys)
    //         reachablePaths.map { it.value.first }.max().let { if (it != null) stepCounts[i] = it }
    //     }
    // }
    //
    // val nrOfSteps = stepCounts.sum()



    error("No possible path to all keys")
}
