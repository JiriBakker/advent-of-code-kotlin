package v2015

import kotlin.math.min

private class Reindeer(val name: String, val speed: Int, val endurance: Int, val rest: Int) {
    fun distanceAfter(seconds: Int): Int {
        val cycles = seconds / (endurance + rest)
        val timeInLastCycle = seconds % (endurance + rest)
        return cycles * speed * endurance + speed * min(timeInLastCycle, endurance)
    }
}

private fun parseReindeers(input: List<String>): List<Reindeer> {
    return input.map {
        val segments = it.trimEnd('.').split(" ")
        val name = segments[0]
        val speed = segments[3].toInt()
        val endurance = segments[6].toInt()
        val rest = segments[13].toInt()
        Reindeer(name, speed, endurance, rest)
    }
}

fun day14a(input: List<String>, secondsUntilFinish: Int = 2503): Int {
    val reindeers = parseReindeers(input)
    return reindeers.map { it.distanceAfter(secondsUntilFinish) }.maxOrNull()!!
}

fun day14b(input: List<String>, secondsUntilFinish: Int = 2503): Int {
    val reindeers = parseReindeers(input)

    val scores = mutableMapOf<String, Int>()
    for (seconds in 1 .. secondsUntilFinish) {
        val reindeerDistances = reindeers.associate { it.name to it.distanceAfter(seconds) }
        reindeerDistances
            .filter { it.value == reindeerDistances.values.maxOrNull()!! }
            .forEach {
                scores[it.key] = scores.getOrDefault(it.key, 0) + 1
            }
    }

    return scores.values.maxOrNull()!!
}