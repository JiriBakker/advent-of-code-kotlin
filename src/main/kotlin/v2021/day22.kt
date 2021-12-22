package v2021

import util.max
import util.min
import util.sumOfLong

private typealias CuboidInstruction = Pair<Cuboid, Boolean>

private fun IntRange.intersectsWith(other: IntRange) =
    first in other || last in other || other.first in this

private fun IntRange.getIntersection(other: IntRange): IntRange =
    max(first, other.first) .. min(last, other.last)

private data class Cuboid(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    private val subtracted = mutableListOf<Cuboid>()

    fun subtract(other: Cuboid) {
        if (this.intersects(other)) {
            val xIntersection = xRange.getIntersection(other.xRange)
            val yIntersection = yRange.getIntersection(other.yRange)
            val zIntersection = zRange.getIntersection(other.zRange)

            subtracted.forEach { cuboid ->
                cuboid.subtract(other)
            }

            subtracted.add(
                Cuboid(xIntersection, yIntersection, zIntersection)
            )
        }
    }

    fun intersects(other: Cuboid) =
        xRange.intersectsWith(other.xRange)
            && yRange.intersectsWith(other.yRange)
            && zRange.intersectsWith(other.zRange)

    val volume: Long get() {
        val totalVolume = xRange.count().toLong() * yRange.count() * zRange.count()
        val subtractedVolume = subtracted.sumOfLong { it.volume }
        return totalVolume - subtractedVolume
    }
}

private fun List<String>.parseInstructions() =
    map { line ->
        val (isOn, coordinates) = line.split(" ")
        val axis = coordinates.split(",")
        val (minX, maxX) = axis[0].trimStart('x', '=').split("..")
        val (minY, maxY) = axis[1].trimStart('y', '=').split("..")
        val (minZ, maxZ) = axis[2].trimStart('z', '=').split("..")
        CuboidInstruction(
            Cuboid(minX.toInt() .. maxX.toInt(), minY.toInt() .. maxY.toInt(), minZ.toInt() .. maxZ.toInt()),
            (isOn == "on")
        )
    }

private fun List<CuboidInstruction>.applyInstructions() =
    fold(emptyList<Cuboid>()) { cuboids, (cuboid, isOn) ->
        cuboids.forEach { it.subtract(cuboid) }

        when (isOn) {
            true -> cuboids.plus(cuboid)
            false -> cuboids
        }
    }

fun day22a(input: List<String>) =
    input.parseInstructions()
        .filter { (cube) ->
            cube.xRange.first >= -50 && cube.xRange.last <= 50 &&
                cube.yRange.first >= -50 && cube.yRange.last <= 50 &&
                cube.zRange.first >= -50 && cube.zRange.last <= 50
        }
        .applyInstructions()
        .sumOf { it.volume }

fun day22b(input: List<String>) =
    input.parseInstructions()
        .applyInstructions()
        .sumOf { it.volume }