package v2019.days.day14

import util.BinarySearchRange
import util.doBinarySearch
import java.util.ArrayDeque
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

private const val ORE = "ORE"
private const val FUEL = "FUEL"

private class Chemical(val name: String, val amount: Long)
private class Reaction(val produces: Chemical, val requirements: List<Chemical>)

private fun parseReactions(input: List<String>): Map<String, Reaction> {
    fun parseChemical(str: String): Chemical {
        return str.split(" ").let { Chemical(it[1], it[0].toLong()) }
    }

    return input
        .associate {
            val segments = it.split(" => ")
            val requirements = segments[0].split(", ").map(::parseChemical)
            val produces = parseChemical(segments[1])
            produces.name to Reaction(produces, requirements)
        }
}

private fun findOreRequired(reactions: Map<String, Reaction>, fuelRequired: Long = 1L): Long {
    val toProduce = ArrayDeque<Pair<Chemical, Long>>(
        listOf(reactions[FUEL]!!.produces to fuelRequired)
    )

    var oreRequired = 0L
    val leftovers = mutableMapOf<String, Long>()

    while (toProduce.isNotEmpty()) {
        val (chemical, grossRequired) = toProduce.poll()

        if (chemical.name == ORE) {
            oreRequired += grossRequired
            continue
        }

        val reaction = reactions[chemical.name] ?: error("No reaction found for ${chemical.name}")

        val leftover = leftovers[chemical.name] ?: 0
        val leftoverUsed = min(leftover, grossRequired)
        leftovers[chemical.name] = max(0, leftover - leftoverUsed)

        val netRequired = grossRequired - leftoverUsed
        if (netRequired <= 0L) {
            continue
        }

        val reactionCount = ceil(netRequired.toDouble() / reaction.produces.amount).toLong()

        leftovers[chemical.name] = reaction.produces.amount * reactionCount - netRequired

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
