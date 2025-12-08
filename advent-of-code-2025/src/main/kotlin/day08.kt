import java.lang.Math.pow
import kotlin.Double
import kotlin.math.pow
import kotlin.math.sqrt

private data class JunctionBox(
    val x: Double,
    val y: Double,
    val z: Double,
    var circuit: Int
) {
    fun distanceTo(other: JunctionBox): Double {
        return sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))
    }
}

fun day08a(input: List<String>, nrOfConnections: Int): Long {
    val junctionBoxes = input.mapIndexed { index, line ->
        val (x, y, z) = line.split(",").map { it.toDouble() }
        JunctionBox(x, y, z, index + 1)
    }

    val distances = mutableMapOf<Pair<JunctionBox, JunctionBox>, Double>()
    for (i in junctionBoxes.indices) {
        for (j in i + 1 until junctionBoxes.size) {
            val distance = junctionBoxes[i].distanceTo(junctionBoxes[j])
            distances[junctionBoxes[i] to junctionBoxes[j]] = distance
        }
    }

    val sortedDistances = distances.toList().sortedBy { (_, j) -> j }
    val circuits = junctionBoxes.associate { it.circuit to mutableListOf(it) }.toMutableMap()

    fun mergeCircuits(id1: Int, id2: Int) {
        if (id1 == id2) return

        circuits[id1] = circuits[id1]!!.plus(circuits[id2]!!).toMutableList()
        circuits[id1]!!.forEach { circuit -> circuit.circuit = id1}
        circuits.remove(id2)
    }

    for ((junctionBoxPair, _) in sortedDistances.take(nrOfConnections)) {
        val (junctionBox1, junctionBox2) = junctionBoxPair

        mergeCircuits(junctionBox1.circuit, junctionBox2.circuit)
    }

    return circuits.values.map { it.size }.sortedDescending().take(3).reduce(Int::times).toLong()
}

fun day08b(input: List<String>): Long {
    val junctionBoxes = input.mapIndexed { index, line ->
        val (x, y, z) = line.split(",").map { it.toDouble() }
        JunctionBox(x, y, z, index + 1)
    }

    val distances = mutableMapOf<Pair<JunctionBox, JunctionBox>, Double>()
    for (i in junctionBoxes.indices) {
        for (j in i + 1 until junctionBoxes.size) {
            val distance = junctionBoxes[i].distanceTo(junctionBoxes[j])
            distances[junctionBoxes[i] to junctionBoxes[j]] = distance
        }
    }

    val sortedDistances = distances.toList().sortedBy { (_, j) -> j }
    val circuits = junctionBoxes.associate { it.circuit to mutableListOf(it) }.toMutableMap()

    var lastConnection: Pair<JunctionBox, JunctionBox>? = null

    fun mergeCircuits(id1: Int, id2: Int, junctionBoxPair: Pair<JunctionBox, JunctionBox>) {
        if (id1 == id2) return

        lastConnection = junctionBoxPair

        circuits[id1] = circuits[id1]!!.plus(circuits[id2]!!).toMutableList()
        circuits[id1]!!.forEach { circuit -> circuit.circuit = id1}
        circuits.remove(id2)
    }

    for ((junctionBoxPair, _) in sortedDistances) {
        val (junctionBox1, junctionBox2) = junctionBoxPair

        mergeCircuits(junctionBox1.circuit, junctionBox2.circuit, junctionBoxPair)
    }

    return lastConnection!!.first.x.toLong() * lastConnection.second.x.toLong()
}