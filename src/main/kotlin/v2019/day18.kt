package v2019

import util.combine
import util.sumOfLong
import java.util.PriorityQueue
import java.util.SortedMap
import kotlin.math.min

import v2019.Day18.PathState

private object Day18 {
    data class PathState(

        val x: Int,
        val y: Int,
        val steps: Long,
        val keys: SortedMap<Char, Long> = sortedMapOf(),
        val doors: Map<Char, Long> = emptyMap(),
        val dependencies: Map<Char, MutableList<Char>> = emptyMap()
    ) {
        val hash = "${x}_${y}_${keys.keys.joinToString("")}"
    }
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

    val toVisit = PriorityQueue<PathState> { a, b -> a.steps.compareTo(b.steps) }
    val visited = mutableSetOf<String>()

    toVisit.add(PathState(startX, startY, 0))

    fun canAccessFrom(from: PathState, x: Int, y: Int): Boolean {
        if (y < 0 || y >= grid.size || x < 0 || x >= grid[y].size) {
            return false
        }

        val cell = grid[y][x]
        return cell != '#' && (!cell.isUpperCase() || from.keys.contains(cell.lowercaseChar()))
    }

    fun addIfViable(x: Int, y: Int, from: PathState) {
        if (canAccessFrom(from, x, y)) {
            val cell = grid[y][x]
            val keys = from.keys.toSortedMap()
            if (cell.isLetter() && cell.isLowerCase() && !from.keys.contains(cell)) {
                keys[cell] = from.steps + 1
            }

            val state = PathState(x, y, from.steps + 1, keys)
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

private fun findPathsWithMaxKeys(x: Int, y: Int, grid: List<CharArray>): List<PathState> {
    val visited = mutableSetOf<String>()
    val toVisit = PriorityQueue<PathState> { a, b -> a.steps.compareTo(b.steps) }
    toVisit.add(PathState(x, y, 0))

    fun canAccessFrom(x: Int, y: Int): Boolean {
        return y >= 0 &&
            y < grid.size &&
            x >= 0 &&
            x < grid[y].size &&
            grid[y][x] != '#'
    }

    fun addIfViable(x: Int, y: Int, from: PathState) {
        if (canAccessFrom(x, y)) {
            val cell = grid[y][x]

            val doors = from.doors.toMutableMap()
            if (cell.isUpperCase()) {
                doors[cell] = from.steps + 1
            }

            val keys = from.keys.toSortedMap()
            val dependencies = from.dependencies.toMutableMap()

            if (cell.isLowerCase() && !from.keys.contains(cell)) {
                val depsForCell = dependencies.getOrPut(cell, { mutableListOf() })
                depsForCell.addAll(doors.keys.filter { !keys.keys.contains(it.lowercaseChar()) })
                if (depsForCell.contains(cell.uppercaseChar())) {
                    return
                }

                keys[cell] = from.steps + 1
            }

            val state = PathState(x, y, from.steps + 1, keys, doors, dependencies)
            if (!visited.contains(state.hash)) {
                toVisit.add(state)
            }
        }
    }

    val paths = mutableListOf<PathState>()
    while (toVisit.isNotEmpty()) {
        val curState = toVisit.poll()

        if (visited.contains(curState.hash)) {
            continue
        }
        visited.add(curState.hash)

        if (grid[curState.y][curState.x].isLowerCase()) {
            paths.add(curState)
        }

        addIfViable(curState.x + 1, curState.y, curState)
        addIfViable(curState.x - 1, curState.y, curState)
        addIfViable(curState.x, curState.y + 1, curState)
        addIfViable(curState.x, curState.y - 1, curState)
    }

    val maxKeys = paths.map { it.keys.size }.maxOrNull() ?: 0
    return paths.filter { it.keys.size == maxKeys }
}

private fun splitVaults(grid: List<CharArray>, x: Int, y: Int) {
    grid[y - 1][x - 1] = '@'
    grid[y - 1][x] = '#'
    grid[y - 1][x + 1] = '@'
    grid[y][x - 1] = '#'
    grid[y][x] = '#'
    grid[y][x + 1] = '#'
    grid[y + 1][x - 1] = '@'
    grid[y + 1][x] = '#'
    grid[y + 1][x + 1] = '@'
}

private fun hasDependencyCollisions(pathStates: List<PathState>): Boolean {
    val dependencies =
        pathStates.flatMap { state -> state.dependencies.entries.map { it.key to it.value } }.toMutableList()

    while (dependencies.isNotEmpty()) {
        val resolvedDependencies = dependencies.filter { it.second.isEmpty() }.map { it.first.uppercaseChar() }
        if (resolvedDependencies.isEmpty()) {
            return true
        }
        dependencies.removeIf { it.second.isEmpty() }
        dependencies.forEach { it.second.removeIf { dep -> resolvedDependencies.contains(dep) } }
    }
    return false
}

private fun findMinimalValidSteps(pathStates: List<List<PathState>>): Long {
    val offsetCombinations = (0..10).toList().combine(4)

    var optimal = Long.MAX_VALUE
    offsetCombinations.forEach { offsets ->
        val statesCombination = offsets.mapIndexed { index, offset -> pathStates[index].getOrNull(offset) }
        if (statesCombination.all { it != null } && !hasDependencyCollisions(statesCombination.requireNoNulls())) {
            optimal = min(optimal, statesCombination.requireNoNulls().sumOfLong { it.steps })
        }
    }

    return optimal
}

fun day18b(input: List<String>): Long {
    val grid = input.map(String::toCharArray)

    val (startX, startY) = findInGrid({ it == '@' }, grid).first()

    splitVaults(grid, startX, startY)

    val viablePaths =
        mutableListOf(
            findPathsWithMaxKeys(startX - 1, startY - 1, grid),
            findPathsWithMaxKeys(startX + 1, startY - 1, grid),
            findPathsWithMaxKeys(startX - 1, startY + 1, grid),
            findPathsWithMaxKeys(startX + 1, startY + 1, grid)
        )

    return findMinimalValidSteps(viablePaths)
}
