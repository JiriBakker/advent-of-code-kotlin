package v2019.days.day12

import v2019.util.forEachCombinationPair
import v2019.util.leastCommonMultiple
import v2019.util.sumByLong
import kotlin.math.abs

private enum class Axis { X, Y, Z }

private class State(var pos: Long, var velocity: Long)

private class Moon(val axisStates: Map<Axis, State>) {
    fun applyGravityPull(other: Moon) {
        Axis.values().forEach { axis ->
            addToAxisVelocity(axis, other.getAxisPos(axis).compareTo(getAxisPos(axis)).toLong())
        }
    }

    fun stepPos() {
        Axis.values().forEach { axis ->
            addToAxisPos(axis, getAxisVelocity(axis))
        }
    }

    fun getAxisPos(axis: Axis): Long = axisStates[axis]!!.pos
    fun getAxisVelocity(axis: Axis): Long = axisStates[axis]!!.velocity

    fun addToAxisPos(axis: Axis, pos: Long) {
        axisStates[axis]!!.pos += pos
    }

    fun addToAxisVelocity(axis: Axis, velocity: Long) {
        axisStates[axis]!!.velocity += velocity
    }

    fun getKineticEnergy(): Long = axisStates.values.sumByLong { abs(it.velocity) }
    fun getPotentialEnergy(): Long = axisStates.values.sumByLong { abs(it.pos) }

    fun equalsOnAxis(other: Moon, axis: Axis): Boolean {
        return getAxisPos(axis) == other.getAxisPos(axis) && getAxisVelocity(axis) == other.getAxisVelocity(axis)
    }
}

private fun parseMoons(input: List<String>): List<Moon> {
    fun parsePosition(str: String): Triple<Long, Long, Long> {
        return str
            .trimStart('<')
            .trimEnd('>')
            .replace("x=", "")
            .replace("y=", "")
            .replace("z=", "")
            .split(',')
            .map { it.trimStart().toLong() }
            .let { Triple(it[0], it[1], it[2]) }
    }

    return input.map {
        val (x, y, z) = parsePosition(it)
        Moon(
            mapOf(
                Axis.X to State(x, 0),
                Axis.Y to State(y, 0),
                Axis.Z to State(z, 0)
            )
        )
    }
}

fun day12a(input: List<String>, nrOfSteps: Long = 1000): Long {
    val moons = parseMoons(input)

    for (step in 0 until nrOfSteps) {
        moons.forEachCombinationPair { moon1, moon2 ->
            moon1.applyGravityPull(moon2)
            moon2.applyGravityPull(moon1)
        }
        moons.forEach { it.stepPos() }
    }

    return moons.sumByLong { it.getKineticEnergy() * it.getPotentialEnergy() }
}

fun day12b(input: List<String>): Long {
    val initialMoons = parseMoons(input)
    val moons = parseMoons(input)

    val periodPerAxis = mutableMapOf(Axis.X to 0L, Axis.Y to 0L, Axis.Z to 0L)
    var step = 0L

    while (periodPerAxis.any { it.value == 0L }) {
        step++

        moons.forEachCombinationPair { moon1, moon2 ->
            moon1.applyGravityPull(moon2)
            moon2.applyGravityPull(moon1)
        }

        moons.forEach { it.stepPos() }

        periodPerAxis
            .filter { it.value == 0L }
            .forEach { (axis, steps) ->
                val isEqualOnAxis = initialMoons.zip(moons).all { (moon1, moon2) -> moon1.equalsOnAxis(moon2, axis) }
                periodPerAxis[axis] = if (isEqualOnAxis) step else 0L
            }
    }

    return leastCommonMultiple(periodPerAxis.values.toList())
}