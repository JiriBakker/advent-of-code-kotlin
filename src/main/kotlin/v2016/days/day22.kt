package v2016.days.day22

import kotlin.math.abs

private class Node(name: String, val used: Int, val available: Int) {
    val x = name.split('-')[1].trimStart('x').toInt()
    val y = name.split('-')[2].trimStart('y').toInt()
}

private fun parseNodes(input: List<String>): List<Node> {
    return input
        .drop(2)
        .map {
            val segments = it.split(' ').filter(String::isNotEmpty)
            Node(segments[0], segments[2].trimEnd('T').toInt(), segments[3].trimEnd('T').toInt())
        }
}

private fun findViablePairs(nodes: List<Node>): Sequence<Pair<Node, Node>> {
    return sequence {
        val sourceNodes = nodes.sortedBy { it.used }
        val destNodes = nodes.sortedBy { it.available }

        var viablePairs = 0L
        var sourceIndex = 0
        var destIndex = 0

        while (sourceIndex < nodes.size && destIndex < nodes.size) {
            val source = sourceNodes[sourceIndex]
            val destination = destNodes[destIndex]

            when {
                sourceIndex == nodes.size - 1 -> destIndex++
                destIndex == nodes.size - 1 -> sourceIndex++
                source.used > destination.available -> destIndex++
                else -> sourceIndex++
            }
            if (source != destination && source.used > 0 && source.used <= destination.available) {
                yield(source to destination)
                viablePairs++
            }
        }
    }
}

private fun printNodeGrid(nodes: List<Node>) {
    val grid = nodes.map { it.y to it }.groupBy { it.first }
        .mapValues { row -> row.value.map { it.second.x to it.second }.toMap() }

    grid.forEach { (_, row) ->
        row.forEach { (_, node) ->
            print("${node.available.toString().padStart(3)}-${node.used.toString().padEnd(4)} ")
        }
        println()
    }
}

fun day22a(input: List<String>): Int {
    val nodes = parseNodes(input)
    return findViablePairs(nodes).count()
}

fun day22b(input: List<String>): Int {
    val nodes = parseNodes(input)

    // printNodeGrid(nodes)

    // Analysing the grid shows the following:
    // - There is an empty node in the bottomright section of the grid
    // - There is a row of nodes midright with huge amounts of data which block any moves
    //
    // Strategy:
    // - First we move the empty node around the row of blocking nodes next to the topright node
    // - Then we move the data in the topright node to the left by doing a 5 step cycle with the empty node
    //   (move around data node and then shift data to the left)
    // - Compute sum of steps as the final answer

    val emptyNode = nodes.first { it.used == 0 }
    val topRightNode = nodes.filter { it.y == 0 }.maxByOrNull { it.x }!!
    val leftMostBlockingNode = nodes.filter { it.used > 400 }.minByOrNull { it.x }!!

    val stepsFromEmptyToNextToBlockingNode =
        abs(emptyNode.x - (leftMostBlockingNode.x - 1)) + abs(emptyNode.y - leftMostBlockingNode.y)
    val stepsFromNextToBlockingNodeToTopRight =
        abs((topRightNode.x - 1) - (leftMostBlockingNode.x - 1)) + abs(topRightNode.y - leftMostBlockingNode.y)
    val stepsToMoveToRightIntoEmptyNode = 1
    val cyclesFromNextToTopRightToTopLeft = topRightNode.x - 1

    return stepsFromEmptyToNextToBlockingNode +
        stepsFromNextToBlockingNodeToTopRight +
        stepsToMoveToRightIntoEmptyNode +
        cyclesFromNextToTopRightToTopLeft * 5
}

