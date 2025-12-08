import kotlin.Double
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3
import kotlin.collections.mapIndexed
import kotlin.math.min
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
        // NOT Manhattan distance, but Euclidian distance
        sqrt(
            (x - other.x).pow(2)
            + (y - other.y).pow(2)
            + (z - other.z).pow(2)
        )
}

fun day08a(input: List<String>, nrOfConnections: Int): Int {
    val junctionBoxes = parseJunctionBoxes(input)

    val (_, circuits) = connectJunctionBoxes(junctionBoxes, nrOfConnections)

    return circuits.values.map { it.size }.sortedDescending().take(3).reduce(Int::times)
}

fun day08b(input: List<String>): Long {
    val junctionBoxes = parseJunctionBoxes(input)

    val (lastConnection, _) = connectJunctionBoxes(junctionBoxes)

    return lastConnection.first.x.toLong() * lastConnection.second.x.toLong()
}

private fun connectJunctionBoxes(junctionBoxes: List<JunctionBox>, nrOfConnections: Int = Int.MAX_VALUE): Pair<Pair<JunctionBox, JunctionBox>, Map<Int, List<JunctionBox>>> {
    val distances = mutableMapOf<Pair<JunctionBox, JunctionBox>, Double>()
    for (i in junctionBoxes.indices) {
        for (j in i + 1 until junctionBoxes.size) {
            val distance = junctionBoxes[i].distanceTo(junctionBoxes[j])
            distances[junctionBoxes[i] to junctionBoxes[j]] = distance
        }
    }

    val sortedDistances = distances.toList().sortedBy { (_, distance) -> distance }
    val circuits = junctionBoxes.associate { it.circuit to listOf(it) }.toMutableMap()
    var lastConnection: Pair<JunctionBox, JunctionBox>? = null

    for (i in 0 until min(nrOfConnections, sortedDistances.size)) {
        val (connection, _) = sortedDistances[i]
        val circuit1 = connection.first.circuit
        val circuit2 = connection.second.circuit

        if (circuit1 == circuit2) continue

        // If not part of same circuit, merge circuits (and update circuit property of junctionboxes)
        circuits[circuit2]!!.forEach { circuit -> circuit.circuit = circuit1 }
        circuits[circuit1] = circuits[circuit1]!!.plus(circuits[circuit2]!!)
        circuits.remove(circuit2)

        lastConnection = connection
    }

    return lastConnection!! to circuits
}

private fun parseJunctionBoxes(input: List<String>) =
    input.mapIndexed { index, line ->
        val (x, y, z) = line.split(",").map { it.toDouble() }
        JunctionBox(x, y, z, index + 1)
    }