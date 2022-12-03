import util.forEachCombinationPair
import util.leastCommonMultiple
import util.sumOfLong
import kotlin.math.abs

private enum class Axis { X, Y, Z }

private data class State(var pos: Long, var velocity: Long)

private class Moon(private val axisStates: Map<Axis, State>) {
    fun applyGravityPull(other: Moon) {
        Axis.values().forEach { axis ->
            getState(axis).velocity += other.getState(axis).pos.compareTo(getState(axis).pos)
        }
    }

    fun applyVelocity() {
        Axis.values().forEach { axis ->
            getState(axis).pos += getState(axis).velocity
        }
    }

    fun computeKineticEnergy(): Long = axisStates.values.sumOfLong { abs(it.velocity) }
    fun computePotentialEnergy(): Long = axisStates.values.sumOfLong { abs(it.pos) }

    fun equalsOnAxis(other: Moon, axis: Axis): Boolean = getState(axis) == other.getState(axis)

    private fun getState(axis: Axis): State = axisStates[axis] ?: error("No state for axis $axis found")
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

private fun simulateStep(moons: List<Moon>) {
    moons.forEachCombinationPair { moon1, moon2 ->
        moon1.applyGravityPull(moon2)
        moon2.applyGravityPull(moon1)
    }

    moons.forEach { it.applyVelocity() }
}

fun day12a(input: List<String>, nrOfSteps: Int = 1000): Long {
    val moons = parseMoons(input)

    repeat(nrOfSteps) { simulateStep(moons) }

    return moons.sumOfLong { it.computeKineticEnergy() * it.computePotentialEnergy() }
}

fun day12b(input: List<String>): Long {
    val initialMoons = parseMoons(input)
    val moons = parseMoons(input)

    val cyclePeriodPerAxis = mutableMapOf(Axis.X to 0L, Axis.Y to 0L, Axis.Z to 0L)
    var step = 0L

    while (cyclePeriodPerAxis.any { it.value == 0L }) {
        step++

        simulateStep(moons)

        val moonPairs = initialMoons.zip(moons)
        Axis.values().forEach { axis ->
            if (cyclePeriodPerAxis[axis] == 0L) {
                val isEqualOnAxis = moonPairs.all { (moon1, moon2) -> moon1.equalsOnAxis(moon2, axis) }
                cyclePeriodPerAxis[axis] = if (isEqualOnAxis) step else 0L
            }
        }
    }

    return leastCommonMultiple(cyclePeriodPerAxis.values)
}