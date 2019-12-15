package v2019.days.day10

import v2019.util.pythDistance
import java.util.ArrayDeque
import kotlin.math.PI
import kotlin.math.atan2

private class LineOfSight(val other: Asteroid, val distance: Double)

private class Asteroid(val x: Int, val y: Int) {
    private val linesOfSight: MutableMap<String, MutableList<LineOfSight>> = mutableMapOf()

    private fun generateAngleHash(x: Double, y: Double): String {
        val angleWithNegativeYAxis = (atan2(x, -y) + (PI * 2)) % (PI * 2)
        return angleWithNegativeYAxis.toString()
    }

    fun addLineOfSight(other: Asteroid) {
        val distance = pythDistance(x, y, other.x, other.y).toDouble()
        val vectorX = (other.x - x) / distance
        val vectorY = (other.y - y) / distance

        val hash = generateAngleHash(vectorX, vectorY)
        this.linesOfSight
            .getOrPut(hash, { mutableListOf() })
            .add(LineOfSight(other, distance))
    }

    fun getLinesOfSight(): Map<String, List<LineOfSight>> {
        return linesOfSight
    }
}

private fun parseInput(input: List<String>): List<Asteroid> {
    return input.withIndex().flatMap {
        val y = it.index
        it.value.mapIndexed { x, char ->
            if (char == '#') Asteroid(x, y) else null
        }.filterNotNull()
    }
}

private fun collectLinesOfSight(asteroids: List<Asteroid>) {
    asteroids.forEachIndexed { i1, asteroid1 ->
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
        .mapValues { ArrayDeque<LineOfSight>(it.value.sortedBy { los -> los.distance }) }
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
