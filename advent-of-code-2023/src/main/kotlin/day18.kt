import util.sumOfLong
import java.math.BigInteger
import kotlin.collections.component1
import kotlin.collections.map

fun day18a(input: List<String>): Long {
    val instructions = input.map { line ->
        val (direction, distance, _) = line.split(" ")
        direction to distance.toInt()
    }

    val outerEdgePositions = findHorizontalOuterEdges(instructions)

    return sumOuterEdgeRanges(outerEdgePositions)
}

fun day18b(input: List<String>): Long {
    val instructions = input.map { line ->
        val hex = line.split("(#")[1].take(6)
        val distance = BigInteger(hex.take(5), 16).toInt()
        val direction = when (hex.takeLast(1)) {
            "0" -> "R"
            "1" -> "D"
            "2" -> "L"
            else -> "U"
        }
        direction to distance
    }

    val outerEdgePositions = findHorizontalOuterEdges(instructions)

    return sumOuterEdgeRanges(outerEdgePositions)
}

private fun findHorizontalOuterEdges(instructions: List<Pair<String, Int>>): Map<Int, List<Int>> {
    // Collect all (horizontal) outer edge positions so we can use them later to determine what is inside the shape and what is outside (per horizontal row)
    var curX = 0
    var curY = 0

    val holePositions = mutableMapOf<Int, MutableSet<Int>>()
    holePositions.getOrPut(curY) { mutableSetOf() }.add(curX)

    val outerEdgePositions = mutableMapOf<Int, MutableSet<Int>>()

    instructions
        .forEach { (direction, distance) ->
            val (dx, dy) = when (direction) {
                "R"  ->  1 to  0
                "L"  -> -1 to  0
                "U"  ->  0 to -1
                else ->  0 to  1 // down
            }

            if (dy != 0) {
                // If we are moving vertical we need to record current outer edge position (will be cleaned up later by filtering if needed)
                outerEdgePositions.getOrPut(curY) { mutableSetOf() }.add(curX + dy)
            }

            repeat(distance) {
                curX += dx
                curY += dy

                if (dy != 0) {
                    // If we are moving vertical we can record outer edge positions
                    outerEdgePositions.getOrPut(curY) { mutableSetOf() }.add(curX + dy)
                } else {
                    // If we are moving horizontal we can record hole positions (so we can use them later for filtering)
                    holePositions.getOrPut(curY) { mutableSetOf() }.add(curX)
                }
            }
        }

    // At corners it's possible that the hole positions overlap with the outer edge, so remove those from the outer edge
    val filteredOuterEdgePositions = outerEdgePositions.mapValues { (y, xs) -> xs.filter { x -> y !in holePositions || x !in holePositions[y]!! } }

    holePositions.clear() // Free some memory

    return filteredOuterEdgePositions
}

private fun sumOuterEdgeRanges(outerEdgePositions: Map<Int, List<Int>>) =
    outerEdgePositions.sumOfLong { (_, positions) ->
        var prevX: Int? = null
        positions
            .sorted()
            .sumOfLong { x ->
                if (prevX == null) {
                    prevX = x
                    0L
                }
                else {
                    val count = x - prevX - 1
                    prevX = null
                    count.toLong()
                }
            }
    }
