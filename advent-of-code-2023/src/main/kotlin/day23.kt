import java.util.*
import kotlin.math.max

data class Node(val x: Int, val y: Int, val neighbours: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()) {
    override fun hashCode(): Int = y * 1_000_000 + x
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false
        return x == other.x && y == other.y
    }
}

fun day23a(input: List<String>): Int {
    val xTarget = input[0].length - 2
    val yTarget = input.size - 1

    fun isWithinBounds(x: Int, y: Int): Boolean =
        x >= 0 && x < input[0].length && y >= 0 && y < input.size

    val toVisit: Queue<Triple<Pair<Int, Int>, Int, MutableList<Pair<Int, Int>>>> = ArrayDeque()
    toVisit.add(Triple(1 to 0, 0, mutableListOf<Pair<Int, Int>>()))

    var maxDistance = 0
    while (toVisit.isNotEmpty()) {
        val (pos, distance, prevVisited) = toVisit.remove()
        val (x, y) = pos
        val visited = prevVisited.toMutableList()
        visited.add(Pair(x, y))

        if (x == xTarget && y == yTarget) {
            maxDistance = maxOf(maxDistance, distance)
            continue
        }

        fun addToVisit(newX: Int, newY: Int) {
            if (isWithinBounds(newX, newY) && input[newY][newX] != '#' && Pair(newX, newY) !in visited) {
                toVisit.add(Triple(newX to newY, distance + 1, visited.toMutableList()))
            }
        }

        when (input[y][x]) {
            '>' -> toVisit.add(Triple(x + 1 to y, distance + 1, visited))
            '<' -> toVisit.add(Triple(x - 1 to y, distance + 1, visited))
            '^' -> toVisit.add(Triple(x to y - 1, distance + 1, visited))
            'v' -> toVisit.add(Triple(x to y + 1, distance + 1, visited))
            else -> {
                addToVisit(x + 1, y)
                addToVisit(x - 1, y)
                addToVisit(x, y + 1)
                addToVisit(x, y - 1)
            }
        }
    }
    return maxDistance
}

fun day23b(input: List<String>): Int {
    val xTarget = input[0].length - 2
    val yTarget = input.size - 1

    fun isWithinBounds(x: Int, y: Int): Boolean =
        x >= 0 && x < input[0].length && y >= 0 && y < input.size && input[y][x] != '#'

    val visited = mutableSetOf<Pair<Int, Int>>()

    fun findNextIntersection(x: Int, y: Int, distance: Int): Triple<Int, Int, Int>? {
        if (Pair(x, y) in visited) return null

        if (x == xTarget && y == yTarget) return Triple(x, y, distance)

        val neighbours = mutableMapOf<Pair<Int, Int>, Char>()
        if (isWithinBounds(x + 1, y)) neighbours[Pair(x + 1, y)] = input[y][x + 1]
        if (isWithinBounds(x - 1, y)) neighbours[Pair(x - 1, y)] = input[y][x - 1]
        if (isWithinBounds(x, y + 1)) neighbours[Pair(x, y + 1)] = input[y + 1][x]
        if (isWithinBounds(x, y - 1)) neighbours[Pair(x, y - 1)] = input[y - 1][x]

        val openNeighbours = neighbours.filter { it.value != '#' }

        if (openNeighbours.size == 1) return null

        if (openNeighbours.size == 2) {
            val nextNeighbour = openNeighbours.entries.firstOrNull { it.key !in visited }
            if (nextNeighbour == null) return null
            visited.add(Pair(x, y))
            return findNextIntersection(nextNeighbour.key.first, nextNeighbour.key.second, distance + 1)
        }

        return Triple(x, y, distance)
    }

    val start = Node(1, 0)
    val nodes = mutableMapOf(Pair(1, 0) to start)
    val toVisit = ArrayDeque<Triple<Int, Int, Node>>()
    toVisit.add(Triple(1, 0, start))

    while (toVisit.isNotEmpty()) {
        val (x, y, prev) = toVisit.remove()
        if (Pair(x, y) in visited) continue
        visited.add(Pair(x, y))

        if (x == xTarget && y == yTarget) continue

        val intersections = mutableListOf<Triple<Int, Int, Int>>()
        if (isWithinBounds(x + 1, y)) {
            findNextIntersection(x + 1, y, 1)?.let { intersections.add(it) }
        }
        if (isWithinBounds(x - 1, y)) {
            findNextIntersection(x - 1, y, 1)?.let { intersections.add(it) }
        }
        if (isWithinBounds(x, y + 1)) {
            findNextIntersection(x, y + 1, 1)?.let { intersections.add(it) }
        }
        if (isWithinBounds(x, y - 1)) {
            findNextIntersection(x, y - 1, 1)?.let { intersections.add(it) }
        }

        for (intersection in intersections) {
            val (xIntersect, yIntersect, dist) = intersection
            val node = nodes.getOrPut(Pair(xIntersect, yIntersect)) { Node(xIntersect, yIntersect) }
            node.neighbours[Pair(prev.x, prev.y)] = dist
            prev.neighbours[Pair(node.x, node.y)] = dist
            toVisit.add(Triple(xIntersect, yIntersect, node))
        }
    }

    val nodesToVisit = ArrayDeque<Triple<Node, Int, Set<Pair<Int, Int>>>>()
    nodesToVisit.add(Triple(start, 0, mutableSetOf<Pair<Int, Int>>()))
    var maxDistance = 0

    while (nodesToVisit.isNotEmpty()) {
        val (node, distance, prevVisited) = nodesToVisit.remove()
        if (Pair(node.x, node.y) in prevVisited) continue

        if (node.x == xTarget && node.y == yTarget) {
            maxDistance = max(maxDistance, distance)
            continue
        }

        for (neighbour in node.neighbours) {
            if (neighbour.key in prevVisited) continue
            nodesToVisit.add(Triple(nodes[neighbour.key]!!, distance + neighbour.value, (prevVisited + Pair(node.x, node.y)).toMutableSet()))
        }
    }
    return maxDistance
}
