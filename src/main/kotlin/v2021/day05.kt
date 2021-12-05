package v2021

import util.max
import util.normalize
import util.sumOf
import kotlin.math.abs

private class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    val isVertical   = x1 == x2
    val isHorizontal = y1 == y2

    fun getPoints(): Sequence<Pair<Int, Int>> {
        val xDelta = (x2 - x1).normalize()
        val yDelta = (y2 - y1).normalize()
        val length = max(abs(x1 - x2), abs(y1 - y2)) + 1

        return sequence {
            var x = x1
            var y = y1

            repeat(length) {
                yield(x to y)
                x += xDelta
                y += yDelta
            }
        }
    }
}

private fun List<String>.parseLines() =
    map { line ->
        val (start, end) = line.split(" -> ")
        val (x1, y1) = start.split(",").map { it.toInt() }
        val (x2, y2) = end.split(",").map { it.toInt() }
        Line(x1, y1, x2, y2)
    }

private fun List<Line>.markPoints(): Map<Int, Map<Int, Int>> {
    val markedPoints = mutableMapOf<Int, MutableMap<Int, Int>>()

    fun markPoint(x: Int, y: Int) {
        val row = markedPoints.getOrPut(y) { mutableMapOf() }
        row[x] = row.getOrDefault(x, 0) + 1
    }

    forEach { line ->
        line.getPoints()
            .forEach { (x, y) ->
                markPoint(x, y)
            }
    }

    return markedPoints
}

private fun Map<Int, Map<Int, Int>>.countOverlapping() =
    sumOf { row -> row.value.count { it.value >= 2 } }

fun day05a(input: List<String>) =
    input
        .parseLines()
        .filter { it.isHorizontal || it.isVertical }
        .markPoints()
        .countOverlapping()

fun day05b(input: List<String>) =
    input
        .parseLines()
        .markPoints()
        .countOverlapping()
