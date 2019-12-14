package v2019.days.day14

import v2019.util.BinarySearchRange
import v2019.util.doBinarySearch
import java.util.ArrayDeque
import kotlin.math.ceil
import kotlin.math.min

private const val ORE = "ORE"
private const val FUEL = "FUEL"

private class Material(val name: String, val amount: Long)
private class Reaction(val produces: Material, val requirements: List<Material>)

private fun parseReactions(input: List<String>): Map<String, Reaction> {
    fun parseMaterial(str: String): Material {
        return str.split(" ").let { Material(it[1], it[0].toLong()) }
    }

    return input
        .associate {
            val segments = it.split(" => ")
            val requirements = segments[0].split(", ").map(::parseMaterial)
            val produces = parseMaterial(segments[1])
            produces.name to Reaction(produces, requirements)
        }
}

private fun findOreRequired(reactions: Map<String, Reaction>, fuelRequired: Long = 1L): Long {
    val toProduce = ArrayDeque<Pair<Material, Long>>(
        listOf(reactions[FUEL]!!.produces to fuelRequired)
    )

    var oreRequired = 0L
    val leftovers = mutableMapOf<String, Long>()

    fun useLeftoversIfAvailable(materialName: String, amountRequired: Long): Long {
        val leftoversAvailable = leftovers.getOrDefault(materialName, 0L)
        val leftoversUsed = min(leftoversAvailable, amountRequired)

        leftovers[materialName] = leftoversAvailable - leftoversUsed

        return leftoversUsed
    }

    fun storeLeftovers(materialName: String, amountProduced: Long, amountUsed: Long) {
        leftovers[materialName] =
            leftovers.getOrDefault(materialName, 0L) +
                (amountProduced - amountUsed)
    }

    while (toProduce.isNotEmpty()) {
        val (material, amountRequired) = toProduce.poll()

        if (material.name == ORE) {
            oreRequired += amountRequired
            continue
        }

        val reaction = reactions[material.name] ?: error("No reaction found for ${material.name}")

        val leftoversUsed = useLeftoversIfAvailable(material.name, amountRequired)
        val amountRequiredAfterLeftovers = amountRequired - leftoversUsed
        val reactionCount = ceil(amountRequiredAfterLeftovers.toDouble() / reaction.produces.amount).toLong()

        if (amountRequiredAfterLeftovers <= 0L) {
            continue
        }

        storeLeftovers(material.name, reaction.produces.amount * reactionCount, amountRequiredAfterLeftovers)

        toProduce.addAll(reaction.requirements.map { it to it.amount * reactionCount })
    }

    return oreRequired
}

fun day14a(input: List<String>): Long {
    val reactions = parseReactions(input)

    return findOreRequired(reactions)
}

fun day14b(input: List<String>): Long {
    val reactions = parseReactions(input)

    val target = 1000000000000L
    val range = BinarySearchRange(0, 1000000000L)

    return doBinarySearch(range, target) { findOreRequired(reactions, it) } - 1
}
