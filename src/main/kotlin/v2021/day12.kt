package v2021

private data class Node(val id: String) {
    val neighbours = mutableMapOf<String, Node>()
    val isSmallNode = id.all { it.isLowerCase() }
}

private fun Node.explore(nodeIdsVisited: Set<String>, doubleVisitsRemaining: Int = 0): List<Set<String>> {
    return neighbours.flatMap { (id, node) ->
        when {
            id == "end" -> setOf(nodeIdsVisited.plus(id))
            id == "start" -> emptyList()
            node.isSmallNode ->
                when {
                    !nodeIdsVisited.contains(id) -> node.explore(nodeIdsVisited.plus(id), doubleVisitsRemaining)
                    doubleVisitsRemaining > 0    -> node.explore(nodeIdsVisited, doubleVisitsRemaining - 1)
                    else                         -> emptyList()
                }
            else -> node.explore(nodeIdsVisited.plus(id), doubleVisitsRemaining)
        }
    }
}

private fun List<String>.parseNodes(): Map<String, Node> {
    val nodes = mutableMapOf<String, Node>()
    forEach { line ->
        val (a, b) = line.split("-")
        val nodeA = nodes.getOrPut(a) { Node(a) }
        val nodeB = nodes.getOrPut(b) { Node(b) }

        nodeA.neighbours.putIfAbsent(b, nodeB)
        nodeB.neighbours.putIfAbsent(a, nodeA)
    }
    return nodes
}

fun day12a(input: List<String>): Int {
    val nodes = input.parseNodes()

    return nodes["start"]!!
        .explore(setOf("start"))
        .count()
}

fun day12b(input: List<String>): Int {
    val nodes = input.parseNodes()

    return nodes["start"]!!
        .explore(setOf("start"), doubleVisitsRemaining = 1)
        .count()
}