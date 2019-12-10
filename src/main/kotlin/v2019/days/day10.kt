package v2019.days.day10

import java.text.DecimalFormat
import java.util.ArrayDeque
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

private class LineOfSight(val other: Asteroid, val distance: Double)

private class Asteroid(val x: Int, val y: Int) {
    private val linesOfSight: MutableMap<String, MutableList<LineOfSight>> = mutableMapOf()

    // Floating point safe hash (TODO is this needed?)
    private fun generateAngleHash(x: Double, y: Double): String {
        return DecimalFormat("#.####").format((atan2(x, y) + (kotlin.math.PI * 2) + 0.00001) % (kotlin.math.PI * 2))
    }

    fun addLineOfSight(other: Asteroid) {
        val distance: Double = sqrt((x.toDouble() - other.x).pow(2.0) + (y.toDouble() - other.y).pow(2.0))
        val vectorX = (other.x - x) / distance
        val vectorY = (y - other.y) / distance

        val hash = generateAngleHash(vectorX, vectorY)
        val linesOfSightForAngle = this.linesOfSight.getOrPut(hash, { mutableListOf() })
        linesOfSightForAngle.add(LineOfSight(other, distance))
    }

    fun getLinesOfSight(): Map<String, List<LineOfSight>> {
        return linesOfSight
    }
}

private fun parseInput(input: List<String>): List<Asteroid> {
    val asteroids = mutableListOf<Asteroid>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] == '#') {
                asteroids.add(Asteroid(x, y))
            }
        }
    }
    return asteroids
}

private fun collectLinesOfSight(asteroids: List<Asteroid>) {
     for (i1 in asteroids.indices) {
        val asteroid1 = asteroids[i1]
        for (i2 in i1 + 1 until asteroids.size) {
            val asteroid2 = asteroids[i2]
            asteroid1.addLineOfSight(asteroid2)
            asteroid2.addLineOfSight(asteroid1)
        }
    }
}

fun day10a(input: List<String>): Int {
    val asteroids = parseInput(input)

    collectLinesOfSight(asteroids)

    return asteroids.map { it.getLinesOfSight().size }.max()!!
}

fun day10b(input: List<String>): Int {
    val asteroids = parseInput(input)

    collectLinesOfSight(asteroids)

    val optimalAsteroid = asteroids.maxBy { it.getLinesOfSight().size }!!

    val linesOfSight = optimalAsteroid.getLinesOfSight()
        .map { it.key to ArrayDeque<LineOfSight>(it.value.sortedBy { los -> los.distance }) }
        .toMap()
        .toSortedMap()

    val laserHits = sequence {
        while (true) {
            linesOfSight.forEach { (_, linesOfSight) ->
                if (linesOfSight.isNotEmpty()) {
                    yield(linesOfSight.poll().other)
                }
            }
        }
    }

    return laserHits
        .take(200)
        .last()
        .let { it.x * 100 + it.y }
}
