package days.day08

import java.util.LinkedList
import java.util.Queue

private data class Node(val children: List<Node>, val metadata: List<Int>)

private fun toIntQueue(treeData: String): Queue<Int> {
    return LinkedList(treeData.split(" ").map { it.toInt() })
}

private fun parse(input: Queue<Int>): Node {
    var nrOfChildren = input.poll()
    var nrOfMetadataItems = input.poll()

    val children = sequence { repeat(nrOfChildren) { yield(parse(input)) } }
    val metadata = sequence { repeat(nrOfMetadataItems) { yield(input.poll()) } }

    return Node(children.toList(), metadata.toList())
}

private fun collectSimple(node: Node): Sequence<Int> {
    return sequence {
        yieldAll(node.metadata)
        yieldAll(node.children.flatMap { collectSimple(it).toList() })
    }
}

private fun collectComplex(node: Node): Sequence<Int> {
    return sequence {
        if (node.metadata.isEmpty()) {
            return@sequence
        }

        val hasChildren = node.children.isNotEmpty()
        if (hasChildren) {
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
    var rootNode = parse(toIntQueue(treeData))
    return collectSimple(rootNode).sum()
}

fun day08b(treeData: String): Int {
    var rootNode = parse(toIntQueue(treeData))
    return collectComplex(rootNode).sum()
}
