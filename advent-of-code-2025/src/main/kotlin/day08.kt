import kotlin.Double
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3
import kotlin.collections.mapIndexed
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.text.split

private data class JunctionBox(
    val x: Double,
    val y: Double,
    val z: Double,
    var circuit: Int
) {
    fun distanceTo(other: JunctionBox) =
        sqrt(
            (x - other.x).pow(2)
            + (y - other.y).pow(2)
            + (z - other.z).pow(2)
        )
}

fun day08a(input: List<String>, nrOfConnections: Int): Int {
    val junctionBoxes = parseJunctionBoxes(input)

    val (_, circuits) = connectJunctionBoxes(junctionBoxes).take(nrOfConnections).last()

    return circuits.values.map { it.size }.sortedDescending().take(3).reduce(Int::times)
}

fun day08b(input: List<String>): Long {
    val junctionBoxes = parseJunctionBoxes(input)

    val (lastConnection, _) = connectJunctionBoxes(junctionBoxes).filter { it.first != null }.last()

    return lastConnection!!.first.x.toLong() * lastConnection.second.x.toLong()
}

private fun connectJunctionBoxes(junctionBoxes: List<JunctionBox>): Sequence<Pair<Pair<JunctionBox, JunctionBox>?, Map<Int, List<JunctionBox>>>> {
    val distances = mutableMapOf<Pair<JunctionBox, JunctionBox>, Double>()
    for (i in junctionBoxes.indices) {
        for (j in i + 1 until junctionBoxes.size) {
            val distance = junctionBoxes[i].distanceTo(junctionBoxes[j])
            distances[junctionBoxes[i] to junctionBoxes[j]] = distance
        }
    }

    val sortedDistances = distances.toList().sortedBy { (_, distance) -> distance }
    val circuits = junctionBoxes.associate { it.circuit to mutableListOf(it) }.toMutableMap()

    return sequence {
        sortedDistances.forEach { (junctionBoxPair, _) ->
            val (junctionBox1, junctionBox2) = junctionBoxPair
            val circuit1 = junctionBox1.circuit
            val circuit2 = junctionBox2.circuit

            if (circuit1 != circuit2) {
                circuits[circuit1] = circuits[circuit1]!!.plus(circuits[circuit2]!!).toMutableList()
                circuits[circuit1]!!.forEach { circuit -> circuit.circuit = circuit1 }
                circuits.remove(circuit2)

                yield(junctionBoxPair to circuits)
            } else {
                yield(null to circuits) // Bit ugly, but easiest way I could think of to support both parts of today's problem
            }
        }
    }
}

private fun parseJunctionBoxes(input: List<String>) =
    input.mapIndexed { index, line ->
        val (x, y, z) = line.split(",").map { it.toDouble() }
        JunctionBox(x, y, z, index + 1)
    }