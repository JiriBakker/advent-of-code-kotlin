package v2018.days.day25

import util.forEachCombinationPair

private class Point(val x: Int, val y: Int, val z: Int, val t: Int) {
    val neighbours = mutableListOf<Point>()
    var constellationNr: Int? = null

    fun distanceTo(other: Point): Int {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z) + Math.abs(t - other.t)
    }
}

private fun parse(inputLines: List<String>): List<Point> {
    return inputLines.map {
        val (x, y, z, t) = it.trim().split(",").map(String::toInt)
        Point(x, y, z, t)
    }
}

private fun addToConstellation(point: Point, constellationNr: Int) {
    point.constellationNr = constellationNr
    point.neighbours.forEach {
        if (it.constellationNr == null) {
            addToConstellation(it, constellationNr)
        }
    }
}

fun day25a(inputLines: List<String>): Int {
    val points = parse(inputLines)

    points.forEachCombinationPair { point1, point2 ->
        if (point1.distanceTo(point2) <= 3) {
            point1.neighbours.add(point2)
            point2.neighbours.add(point1)
        }
    }

    var curConstellationNr = 0
    points.forEach {
        if (it.constellationNr == null) {
            addToConstellation(it, curConstellationNr++)
        }
    }
    return curConstellationNr
}