package v2021

import util.max
import kotlin.math.sqrt

private class Target(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int) {
    fun isHit(x: Int, y:Int) =
        x in xMin .. xMax && y in yMin .. yMax

    fun hasMissed(x: Int, y: Int) =
        x > xMax || y < yMin
}

private fun String.parseTarget(): Target {
    val segments = split(" ")
    val (xMin, xMax) = segments[2].trimEnd(',').trimStart('x', '=').split("..").map { it.toInt() }
    val (yMin, yMax) = segments[3].trimStart('y', '=').split("..").map { it.toInt() }
    return Target(xMin, xMax, yMin, yMax)
}

private class ProbeLauncher(private val target: Target) {
    fun shoot(
        startVx: Int,
        startVy: Int,
        onHit: (Int) -> Unit,
        onMiss: (Int) -> Unit
    ) {
        var x = 0
        var y = 0
        var vx = startVx
        var vy = startVy

        var maxY = y
        while (!target.hasMissed(x, y)) {
            if (target.isHit(x, y)) {
                onHit(maxY)
                return
            }
            x += vx
            y += vy
            vx = max(0, vx - 1)
            vy--

            maxY = max(y, maxY)
        }

        onMiss(x)
    }
}

private fun findMaxY(input: List<String>): Int {
    val target = input.first().parseTarget()
    val probeLauncher = ProbeLauncher(target)

    var overallMaxY: Int? = null

    var startVx = target.xMin
    var startVy = target.yMax

    repeat(1000) { // TODO I'm pretty sure this can be done more efficiently
        probeLauncher.shoot(
            startVx,
            startVy,
            onHit = { maxY ->
                overallMaxY = maxY
                startVy++
            },
            onMiss = { x ->
                if (x < target.xMin) {
                    startVx++
                } else if (x > target.xMax) {
                    startVx--
                } else {
                    startVy++
                }
            }
        )
    }

    return overallMaxY ?: 0
}

fun day17a(input: List<String>) =
    findMaxY(input)

fun day17b(input: List<String>): Int {
    val maxY = findMaxY(input)

    val target = input.first().parseTarget()
    val probeLauncher = ProbeLauncher(target)

    var hitCount = 0

    val maxYSqrt = sqrt(maxY.toDouble()).toInt() * 2 // It's magic...

    (0 .. target.xMax).map { startVx ->
        (target.yMin .. maxYSqrt).map { startVy ->
            probeLauncher.shoot(
                startVx,
                startVy,
                onHit = { hitCount++ },
                onMiss = { }
            )
        }
    }

    return hitCount
}