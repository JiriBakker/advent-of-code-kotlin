package v2021

import util.max

private class ProbeTarget(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
    fun isHit(x: Int, y:Int) =
        x in minX .. maxX && y in minY .. maxY

    fun isMissed(x: Int, y: Int) =
        x > maxX || y < minY
}

private fun String.parseTarget(): ProbeTarget {
    val segments = split(" ")
    val (xMin, xMax) = segments[2].trimEnd(',').trimStart('x', '=').split("..").map { it.toInt() }
    val (yMin, yMax) = segments[3].trimStart('y', '=').split("..").map { it.toInt() }
    return ProbeTarget(xMin, xMax, yMin, yMax)
}

private class ProbeLauncher(private val probeTarget: ProbeTarget) {
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

        while (!probeTarget.isMissed(x, y)) {
            if (probeTarget.isHit(x, y)) {
                onHit(startVy)
                return
            }
            x += vx
            y += vy
            vx = max(0, vx - 1)
            vy--
        }

        onMiss(x)
    }
}

private fun findMaxVY(input: List<String>): Int {
    val target = input.first().parseTarget()
    val probeLauncher = ProbeLauncher(target)

    var overallMaxVy = 0

    var startVx = target.minX
    var startVy = target.maxY

    var missCount = 0

    while (missCount < 75) { // TODO Fix magic number
        probeLauncher.shoot(
            startVx,
            startVy,
            onHit = { maxVy ->
                overallMaxVy = max(overallMaxVy, maxVy)
                startVy++
                missCount = 0
            },
            onMiss = { x ->
                if (x < target.minX) {
                    startVx++
                } else if (x > target.maxX) {
                    startVx--
                } else {
                    startVy++
                }
                missCount++
            }
        )
    }

    return overallMaxVy
}

fun day17a(input: List<String>): Int {
    val maxVy = findMaxVY(input)
    return maxVy * (maxVy + 1) / 2
}

fun day17b(input: List<String>): Int {
    val maxVy = findMaxVY(input)

    val target = input.first().parseTarget()
    val probeLauncher = ProbeLauncher(target)

    var hitCount = 0

    (0 .. target.maxX).map { startVx ->
        (target.minY .. maxVy).map { startVy ->
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