import java.util.PriorityQueue

import Day20.PathState
import Day20.Pos

private class PortalPair(val id: Int, val innerPos: Pos, val outerPos: Pos)

private object Day20 {
    data class Pos(val x: Int, val y: Int)

    class PathState(val pos: Pos, val steps: Int, val depth: Int = 0)
}

private const val START = -1
private const val FINISH = -2

private fun parseGrid(input: List<String>): Pair<List<IntArray>, Map<String, PortalPair>> {
    val width = input.map { it.length }.maxOrNull()!! - 4
    val height = input.size - 4

    val portals = mutableMapOf<String, Pair<Int, Pos>>()
    val portalPairs = mutableMapOf<String, PortalPair>()
    var curId = 10
    val output = List(height) { IntArray(width) { 0 } }

    fun addPortal(x: Int, y: Int) {
        val letter = input[y][x]
        val (name, pos) = when {
            x > 0 && x < input[y].length - 1 && input[y][x - 1].isLetter() && input[y][x + 1] == '.' -> "${input[y][x - 1]}$letter" to Pos(x + 1 - 2, y - 2)
            x > 1 && input[y][x - 1].isLetter() && input[y][x - 2] == '.' -> "${input[y][x - 1]}$letter" to Pos(x - 2 - 2, y - 2)
            x > 0 && x < input[y].length - 1 && input[y][x + 1].isLetter() && input[y][x - 1] == '.' -> "$letter${input[y][x + 1]}" to Pos(x - 1 - 2, y - 2)
            x < input[y].length - 2 && input[y][x + 1].isLetter() && input[y][x + 2] == '.' -> "$letter${input[y][x + 1]}" to Pos(x + 2 - 2, y - 2)

            y > 0 && y < input.size - 1 && input[y - 1][x].isLetter() && input[y + 1][x] == '.' -> "${input[y - 1][x]}$letter" to Pos(x - 2, y + 1 - 2)
            y > 1 && input[y - 1][x].isLetter() && input[y - 2][x] == '.' -> "${input[y - 1][x]}$letter" to Pos(x - 2, y - 2 - 2)
            y > 0 && y < input.size - 1 && input[y + 1][x].isLetter() && input[y - 1][x] == '.' -> "$letter${input[y + 1][x]}" to Pos(x - 2, y - 1 - 2)
            y < input.size - 2 && input[y + 1][x].isLetter() && input[y + 2][x] == '.' -> "$letter${input[y + 1][x]}" to Pos(x - 2, y + 2 - 2)

            else -> error("Unable to parse portal")
        }

        when {
            portals.containsKey(name) -> {
                if (name != "AA" && name != "ZZ") {
                    val (id, pos2) = portals[name]!!
                    if (pos != pos2) {
                        if (pos.x == 0 || pos.y == 0 || pos.x == width - 1 || pos.y == height - 1) {
                            portalPairs[name] = PortalPair(id, pos2, pos)
                        } else {
                            portalPairs[name] = PortalPair(id, pos, pos2)
                        }
                    }
                }
            }
            else -> {
                portals[name] = curId to pos
                curId++
            }
        }
    }

    for (y in input.indices) {
        for (x in input[y].indices) {
            when (input[y][x]) {
                ' ' -> Unit
                '#' -> output[y - 2][x - 2] = 0
                '.' -> output[y - 2][x - 2] = 1
                else -> addPortal(x, y)
            }
        }
    }

    portals.forEach { portal ->
        val portalPair = portalPairs[portal.key]
        if (portalPair != null) {
            output[portalPair.innerPos.y][portalPair.innerPos.x] = portal.value.first
            output[portalPair.outerPos.y][portalPair.outerPos.x] = portal.value.first
        } else {
            when (portal.key) {
                "AA" -> output[portal.value.second.y][portal.value.second.x] = START
                "ZZ" -> output[portal.value.second.y][portal.value.second.x] = FINISH
            }
        }
    }

    return output to portalPairs
}

private fun findOptimalPath(grid: List<IntArray>, portalPairs: Map<String, PortalPair>, depthTransitionDelta: Int = 0): Int {
    val (startX, startY) = grid.withIndex().flatMap { (y, row) -> row.withIndex().firstOrNull { it.value == -1 }.let { if (it != null) listOf(it.index to y) else emptyList() } }.first()

    val visited = mutableSetOf<Pair<Pos, Int>>()
    val toVisit = PriorityQueue<PathState> { a, b -> a.steps.compareTo(b.steps) }
    toVisit.add(PathState(Pos(startX, startY), 0, 0))

    fun addIfNotVisited(pos: Pos, steps: Int, depth: Int) {
        if (!visited.contains(pos to depth)) {
            toVisit.add(PathState(pos, steps, depth))
        }
    }

    fun addIfViable(x: Int, y: Int, steps: Int, depth: Int) {
        if (y < 0 || y >= grid.size || x < 0 || x >= grid[y].size || grid[y][x] == 0) return
        addIfNotVisited(Pos(x, y), steps, depth)
    }

    while (toVisit.isNotEmpty()) {
        val cur = toVisit.poll()
        val (x, y) = cur.pos

        if (cur.depth == 0 && grid[y][x] == FINISH) {
            return cur.steps
        }

        if (visited.contains(cur.pos to cur.depth)) {
            continue
        }
        visited.add(cur.pos to cur.depth)

        addIfViable(x - 1, y, cur.steps + 1, cur.depth)
        addIfViable(x + 1, y, cur.steps + 1, cur.depth)
        addIfViable(x, y - 1, cur.steps + 1, cur.depth)
        addIfViable(x, y + 1, cur.steps + 1, cur.depth)

        if (grid[y][x] > 1) {
            val portalPair = portalPairs.values.first { it.id == grid[y][x] }
            if (portalPair.innerPos == Pos(x, y)) {
                addIfNotVisited(portalPair.outerPos, cur.steps + 1, cur.depth + depthTransitionDelta)
            } else if (cur.depth >= depthTransitionDelta) {
                addIfNotVisited(portalPair.innerPos, cur.steps + 1, cur.depth - depthTransitionDelta)
            }
        }
    }

    error("No path from AA to ZZ found")
}

fun day20a(input: List<String>): Int {
    val (grid, portalPairs) = parseGrid(input)
    return findOptimalPath(grid, portalPairs)
}

fun day20b(input: List<String>): Int {
    val (grid, portalPairs) = parseGrid(input)
    return findOptimalPath(grid, portalPairs, 1)
}
