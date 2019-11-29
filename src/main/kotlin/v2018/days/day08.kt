package v2018.days.day08

import java.util.LinkedList
import java.util.Queue

private class Node(val children: List<Node>, val metadata: List<Int>)

private fun toIntQueue(treeData: String): Queue<Int> {
    return LinkedList(treeData.split(" ").map { it.toInt() })
}

private fun parse(input: Queue<Int>): Node {
    val nrOfChildren = input.poll()
    val nrOfMetadataItems = input.poll()

    val children = Array(nrOfChildren) { parse(input) }.asList()
    val metadata = Array(nrOfMetadataItems) { input.poll() }.asList()

    return Node(children, metadata)
}

private fun collectSimple(node: Node): List<Int> {
    return node.metadata + node.children.flatMap { collectSimple(it) }
}

private fun collectComplex(node: Node): Sequence<Int> {
    return sequence {
        if (node.metadata.isEmpty()) {
            return@sequence
        }

        if (node.children.isNotEmpty()) {
            yieldAll(node.metadata.flatMap {
                if (it > 0 && it <= node.children.size) {
                    collectComplex(node.children[it - 1]).toList()
                } else {
                    listOf(0)
                }
            })
        } else {
            yieldAll(node.metadata)
        }
    }
}

fun day08a(treeData: String): Int {
    val rootNode = parse(toIntQueue(treeData))
    return collectSimple(rootNode).sum()
}

fun day08b(treeData: String): Int {
    val rootNode = parse(toIntQueue(treeData))
    return collectComplex(rootNode).sum()
}
