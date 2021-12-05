package v2021

import util.max
import util.min
import util.sumOf

private class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    val isVertical   = x1 == x2
    val isHorizontal = y1 == y2

    fun getPoints(): Sequence<Pair<Int, Int>> {
        return sequence {
            if (isVertical) {
                for (y in min(y1, y2) .. max(y1, y2)) {
                    yield(x1 to y)
                }
            } else if (isHorizontal) {
                for (x in min(x1, x2) .. max(x1, x2)) {
                    yield(x to y1)
                }
            } else {
                val xDelta = if (x1 > x2) -1 else 1
                if (y1 < y2) {
                    var x = x1
                    for (y in y1 .. y2) {
                        yield(x to y)
                        x += xDelta
                    }
                } else {
                    var x = x1
                    for (y in y1 downTo y2) {
                        yield(x to y)
                        x += xDelta
                    }
                }
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
