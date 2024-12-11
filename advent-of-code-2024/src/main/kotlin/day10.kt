import java.util.ArrayDeque

fun day10a(input: List<String>): Int {
    val map = input.parseMap()
    return map
        .findTrailHeads()
        .sumOf { trailhead ->
            findTrails(trailhead, map).distinct().size
        }
}

fun day10b(input: List<String>): Int {
    val map = input.parseMap()
    return map
        .findTrailHeads()
        .sumOf { trailhead ->
            findTrails(trailhead, map).size
        }
}

private fun findTrails(trailhead: Pair<Int, Int>, map: List<List<Int>>): List<Pair<Int,Int>> {
    val trails = mutableListOf<Pair<Int, Int>>()

    val toVisit = ArrayDeque<Triple<Int, Int, MutableSet<Pair<Int, Int>>>>()
    toVisit.add(Triple(trailhead.first, trailhead.second, mutableSetOf()))

    fun attemptTravel(x: Int, y: Int, h: Int, visited: MutableSet<Pair<Int, Int>>) {
        if (
            y in map.indices
            && x in map[y].indices
            && x to y !in visited
            && map[y][x] == h + 1
        ) {
            toVisit.add(Triple(x, y, visited.toMutableSet()))
        }
    }

    while (toVisit.isNotEmpty()) {
        val (x, y, visited) = toVisit.pop()

        if (!visited.add(x to y)) continue

        if (map[y][x] == 9) {
            trails.add(x to y)
            continue
        }

        attemptTravel(x - 1, y, map[y][x], visited)
        attemptTravel(x + 1, y, map[y][x], visited)
        attemptTravel(x, y - 1, map[y][x], visited)
        attemptTravel(x, y + 1, map[y][x], visited)
    }

    return trails
}

private fun List<String>.parseMap(): List<List<Int>> =
    this.map { line -> line.map { if (it == '.') -1 else it.digitToInt() } }

private fun  List<List<Int>>.findTrailHeads(): List<Pair<Int, Int>> =
    this.withIndex().map { (y, line) ->
        line.withIndex().mapNotNull { (x, c) ->
            if (c == 0) x to y else null
        }
    }.flatten()