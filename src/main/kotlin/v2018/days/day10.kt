package v2018.days.day10

import v2018.getBounds

private class Light(private val startX: Int, private val startY: Int, val speedX: Int, val speedY: Int) {
    var posX = startX
        private set

    var posY = startY
        private set

    fun toSecond(second: Int) {
        posX = startX + (speedX * second)
        posY = startY + (speedY * second)
    }
}

private fun parse(inputLines: List<String>): List<Light> {
    return inputLines.map {
        val parts = it.split("<")
        val posParts = parts[1].split(",")
        val posX = posParts[0].trim().toInt()
        val posY = posParts[1].substring(0, posParts[1].indexOf('>')).trim().toInt()
        val speedParts = parts[2].split(",")
        val speedX = speedParts[0].trim().toInt()
        val speedY = speedParts[1].substring(0, speedParts[1].indexOf('>')).trim().toInt()
        Light(posX, posY, speedX, speedY)
    }
}

private fun waitForAlignedLights(lights: List<Light>): Int {
    var second = 0
    var minWidth = Int.MAX_VALUE
    do {
        lights.forEach { it.toSecond(second) }
        second++

        val (minX, maxX) = lights.getBounds { it.posX }
        val width = maxX - minX
        if (width < minWidth) {
            minWidth = width
        }
    } while (width == minWidth) // Have the bounds starting expanding again?

    second -= 2 // One 'second++' too many, and one iteration too many

    lights.forEach { it.toSecond(second) }
    return second
}

private fun alignedLightsToString(lights: List<Light>): String {
    val (minX, maxX) = lights.getBounds { it.posX }
    val (minY, maxY) = lights.getBounds { it.posY }
    val width = maxX - minX + 1
    val height = maxY - minY + 1

    val grid = Array(width) { Array(height) { '.' } }
    lights.forEach { grid[it.posX - minX][it.posY - minY] = '#' }

    val buffer = StringBuilder()
    for (y in 0 until height) {
        for (x in 0 until width) {
            buffer.append(grid[x][y])
        }
        buffer.append('\n')
    }
    return buffer.toString()
}

fun day10a(inputLines: List<String>): String {
    val lights = parse(inputLines)
    waitForAlignedLights(lights)
    return alignedLightsToString(lights)
}

fun day10b(inputLines: List<String>): Int {
    val lights = parse(inputLines)
    return waitForAlignedLights(lights)
}
