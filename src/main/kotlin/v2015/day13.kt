package v2015

import util.permute

private fun parseSeatings(input: List<String>): Map<String, Map<String, Int>> {
    val output = mutableMapOf<String, MutableMap<String,Int>>()
    input.forEach { line ->
        val segments = line.trimEnd('.').split(" ")
        val name1 = segments[0]
        val name2 = segments[10]
        val happiness = segments[3].toInt() * (if (segments[2] == "gain") 1 else -1)
        output.getOrPut(name1, { mutableMapOf() })[name2] = happiness
    }
    return output
}

private fun findOptimalHappiness(seatings: Map<String, Map<String, Int>>): Int {
    fun getHappiness(name1: String, name2: String): Int = seatings[name1]!![name2]!! + seatings[name2]!![name1]!!

    return seatings.keys.permute().map { names ->
        names.zipWithNext().map { (name1, name2) ->
             getHappiness(name1, name2)
        }.sum() + getHappiness(names.first(), names.last())
    }.maxOrNull()!!
}

fun day13a(input: List<String>): Int {
    val seatings = parseSeatings(input)
    return findOptimalHappiness(seatings)
}

private const val YOU: String = "You"

fun day13b(input: List<String>): Int {
    val seatings = parseSeatings(input)

    val updatedSeatings: Map<String, Map<String, Int>> =
        seatings
            .mapValues { it.value.plus(YOU to 0) }
            .plus(YOU to seatings.keys.associateWith { 0 })

    return findOptimalHappiness(updatedSeatings)
}