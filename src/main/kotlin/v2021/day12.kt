package v2021

private data class Node(val id: String) {
    val neighbours = mutableMapOf<String, Node>()

    val isSmallNode = id.all { it.isLowerCase() }
}

private fun Node.explore(pathSoFar: List<String>, doubleVisitsRemaining: Int = 0): List<List<String>> {
    return neighbours.flatMap { (id, node) ->
        if (id == "end") {
            listOf(pathSoFar.plus(id).toList())
        } else if (id == "start") {
            emptyList()
        } else if (node.isSmallNode) {
            val visitCount = pathSoFar.count { it == id }
            if (visitCount == 0) {
                node.explore(pathSoFar.plus(id), doubleVisitsRemaining)
            } else if (visitCount == 1 && doubleVisitsRemaining > 0) {
                node.explore(pathSoFar.plus(id), doubleVisitsRemaining - 1)
            } else {
                emptyList()
            }
        } else {
            node.explore(pathSoFar.plus(id), doubleVisitsRemaining)
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
        .explore(listOf("start"))
        .count()
}

fun day12b(input: List<String>): Int {
    val nodes = input.parseNodes()

    return nodes["start"]!!
        .explore(listOf("start"), 1)
        .count()
}