import java.util.*
import kotlin.math.max



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
        visited.add(x to y)

        if (x == xTarget && y == yTarget) {
            maxDistance = maxOf(maxDistance, distance)
            continue
        }

        fun addToVisit(newX: Int, newY: Int) {
            if (isWithinBounds(newX, newY) && input[newY][newX] != '#' && newX to newY !in visited) {
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
    val targetX = input[0].length - 2
    val targetY = input.size - 1

    val (intersectionNodes, start) = buildIntersectionNodeGraph(input, targetX, targetY)

    return findLongestPath(intersectionNodes, start, targetX, targetY)
}

data class IntersectionNode(
    val x: Int,
    val y: Int,
    val neighbours: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
) {
    override fun hashCode(): Int = y * 1_000_000 + x
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IntersectionNode) return false
        return x == other.x && y == other.y
    }
}

private fun buildIntersectionNodeGraph(input: List<String>, targetX: Int, targetY: Int): Pair<Map<Pair<Int, Int>, IntersectionNode>, IntersectionNode> {
    fun isWithinBounds(x: Int, y: Int): Boolean =
        x >= 0 && x < input[0].length && y >= 0 && y < input.size && input[y][x] != '#'

    val visited = mutableSetOf<Pair<Int, Int>>()

    fun findNextIntersection(x: Int, y: Int, distance: Int): Triple<Int, Int, Int>? {
        if (x to y in visited) return null

        if (x == targetX && y == targetY) return Triple(x, y, distance)

        val openNeighbours =
            listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
            )
            .map { (dx, dy) -> x + dx to y + dy }
            .filter { (nx, ny) -> isWithinBounds(nx, ny) }
            .filter { (nx, ny) -> input[ny][nx] != '#' }
            .associate { (nx, ny) -> (nx to ny) to input[ny][nx] }

        if (openNeighbours.size == 1) return null

        if (openNeighbours.size == 2) {
            val nextNeighbour =
                openNeighbours.keys.firstOrNull { it !in visited }
                    ?: return null

            visited.add(x to y)

            val (nx, ny) = nextNeighbour
            return findNextIntersection(nx, ny, distance + 1)
        }

        return Triple(x, y, distance)
    }

    val start = IntersectionNode(1, 0)
    val intersectionNodes = mutableMapOf(1 to 0 to start)
    val toVisit = ArrayDeque<Triple<Int, Int, IntersectionNode>>()
    toVisit.add(Triple(1, 0, start))

    while (toVisit.isNotEmpty()) {
        val (x, y, prev) = toVisit.remove()
        if (x to y in visited) continue
        visited.add(x to y)

        if (x == targetX && y == targetY) continue

        val intersections =
            listOf(
                -1 to 0,
                1 to 0,
                0 to -1,
                0 to 1
            )
            .map { (dx, dy) -> x + dx to y + dy }
            .filter { (nx, ny) -> isWithinBounds(nx, ny) }
            .mapNotNull { (nx, ny) -> findNextIntersection(nx, ny, 1) }

        for (intersection in intersections) {
            val (xIntersect, yIntersect, dist) = intersection
            val intersectionNode = intersectionNodes.getOrPut(xIntersect to yIntersect) { IntersectionNode(xIntersect, yIntersect) }
            intersectionNode.neighbours[prev.x to prev.y] = dist
            prev.neighbours[intersectionNode.x to intersectionNode.y] = dist
            toVisit.add(Triple(xIntersect, yIntersect, intersectionNode))
        }
    }

    return intersectionNodes to start
}

private fun findLongestPath(nodes: Map<Pair<Int, Int>, IntersectionNode>, start: IntersectionNode, targetX: Int, targetY: Int): Int {
    val nodesToVisit = ArrayDeque<Triple<IntersectionNode, Int, Set<Pair<Int, Int>>>>()
    nodesToVisit.add(Triple(start, 0, mutableSetOf()))
    var maxDistance = 0

    while (nodesToVisit.isNotEmpty()) {
        val (node, distance, prevVisited) = nodesToVisit.remove()
        if (node.x to node.y in prevVisited) continue

        if (node.x == targetX && node.y == targetY) {
            maxDistance = max(maxDistance, distance)
            continue
        }

        for (neighbour in node.neighbours) {
            if (neighbour.key in prevVisited) continue

            nodesToVisit.add(
                Triple(
                    nodes[neighbour.key]!!,
                    distance + neighbour.value,
                    (prevVisited + (node.x to node.y)).toMutableSet()
                )
            )
        }
    }

    return maxDistance
}