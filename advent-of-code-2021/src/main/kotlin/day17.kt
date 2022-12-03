import util.max

private class ProbeTarget(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
    fun isHit(x: Int, y:Int) =
        x in minX .. maxX && y in minY .. maxY

    fun isMiss(x: Int, y: Int) =
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
        startVy: Int
    ): ShotResult {
        var x = 0
        var y = 0
        var vx = startVx
        var vy = startVy

        while (true) {
            if (probeTarget.isHit(x, y)) {
                return Hit(startVy)
            }
            if (probeTarget.isMiss(x, y)) {
                return Miss(finalX = x)
            }

            x += vx
            y += vy
            vx = max(0, vx - 1)
            vy--
        }
    }
}

private interface ShotResult
private class Hit(val startVy: Int): ShotResult
private class Miss(val finalX: Int): ShotResult

private fun findMaxVY(input: List<String>): Int {
    val target = input.first().parseTarget()
    val probeLauncher = ProbeLauncher(target)

    var overallMaxVy = 0

    var startVx = target.minX
    var startVy = target.maxY

    var missCount = 0

    while (missCount < 75) { // TODO Fix magic number
        val shotResult = probeLauncher.shoot(startVx, startVy)
        when (shotResult) {
            is Hit -> {
                overallMaxVy = max(overallMaxVy, shotResult.startVy)
                startVy++
                missCount = 0
            }
            is Miss -> {
                if (shotResult.finalX < target.minX) {
                    startVx++
                } else if (shotResult.finalX > target.maxX) {
                    startVx--
                } else {
                    startVy++
                }
                missCount++
            }
        }
    }

    return overallMaxVy
}

private fun findMaxVYWithSmartMath(input: List<String>): Int {
    val target = input.first().parseTarget()
    return target.minY * -1 - 1
}

fun day17a(input: List<String>): Int {
    val maxVy = findMaxVY(input)
    return maxVy * (maxVy + 1) / 2
}

fun day17b(input: List<String>): Int {
    val maxVy = findMaxVY(input)

    val target = input.first().parseTarget()
    val probeLauncher = ProbeLauncher(target)

    val hitCount =
        (0 .. target.maxX).sumOf { startVx -> // TODO reduce search space further?
            (target.minY .. maxVy).count { startVy ->
                val shotResult = probeLauncher.shoot(startVx, startVy)
                shotResult is Hit
            }
        }

    return hitCount
}