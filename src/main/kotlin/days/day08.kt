package days.day08

import java.util.LinkedList
import java.util.Queue

private class Node(val parent: Node?, val id: Int) {
    val metadata: MutableList<Int> = mutableListOf()

    val children: MutableList<Node> = mutableListOf()
}

private class IdProvider {
    private var curId = 0

    fun next(): Int {
        return curId++
    }
}

private fun toIntQueue(treeData: String): Queue<Int> {
    return LinkedList(treeData.split(" ").map { it.toInt() })
}

private fun parse(input: Queue<Int>, parent: Node?, idProvider: IdProvider): Node {
    var nrOfChildren = input.poll()
    var nrOfMetadataItems = input.poll()

    var node = Node(parent, idProvider.next())

    for (i in 0 until nrOfChildren) {
        val child = parse(input, node, idProvider)
        node.children.add(child)
    }

    for (i in 0 until nrOfMetadataItems) {
        node.metadata.add(input.poll())
    }

    return node
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
    var rootNode = parse(toIntQueue(treeData), null, IdProvider())
    return collectSimple(rootNode).sum()
}

fun day08b(treeData: String): Int {
    var rootNode = parse(toIntQueue(treeData), null, IdProvider())
    return collectComplex(rootNode).sum()
}
