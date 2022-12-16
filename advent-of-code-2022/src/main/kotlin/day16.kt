import Day16.ValvePath
import Day16.fillTravelTimes
import Day16.parseValves
import util.max
import util.min
import java.util.LinkedList

fun day16a(input: List<String>): Int {
    val maxMinutes = 30

    val valves = input.parseValves()
    val startValve = valves["AA"]!!

    val nonZeroFlowRateValves = valves.values.filter { it.flowRate > 0 }
    nonZeroFlowRateValves.fillTravelTimes(startValve, valves)

    val minimumMinutesToOpenValve = nonZeroFlowRateValves.minOf { it.minimumMinutesToOpenValve }
    val maxMinutesForLastMove = maxMinutes - minimumMinutesToOpenValve

    val toVisit = ArrayDeque<ValvePath>()
    toVisit.add(ValvePath(startValve, 0, 0, listOf(startValve)))

    val visited = mutableSetOf<String>()
    var maxPressureReleased = 0

    while (toVisit.isNotEmpty()) {
        val path = toVisit.removeFirst()

        maxPressureReleased = max(maxPressureReleased, path.pressureReleased)

        val hasTimeToMove = path.minutesElapsed <= maxMinutesForLastMove
        if (!hasTimeToMove) {
            continue
        }

        val hasAlreadyVisited = !visited.add(path.hash())
        if (hasAlreadyVisited) {
            continue
        }

        nonZeroFlowRateValves
            .filter { !path.visitedValves.contains(it)}
            .forEach { other ->
                val minutesElapsedWhenValveOpened = path.minutesElapsed + path.currentValve.minutesToOpenValve(other)
                val minutesRemaining = maxMinutes - minutesElapsedWhenValveOpened
                val additionalPressureReleased = other.flowRate * minutesRemaining

                toVisit.add(
                    ValvePath(
                        other,
                        minutesElapsedWhenValveOpened,
                        path.pressureReleased + additionalPressureReleased,
                        path.visitedValves.plus(other)
                    )
                )
            }
    }

    return maxPressureReleased
}

fun day16b(input: List<String>): Int {
    val maxMinutes = 26
    val maxMinutesForLastMove = maxMinutes - 2

    val valves = input.parseValves()
    val startValve = valves["AA"]!!

    val nonZeroFlowRateValves = valves.values.filter { it.flowRate > 0 }
    nonZeroFlowRateValves.fillTravelTimes(startValve, valves)

    val toVisit = ArrayDeque<Pair<ValvePath, ValvePath>>()
    toVisit.add(Pair(
        ValvePath(startValve, 0, 0, listOf(startValve)),
        ValvePath(startValve, 0, 0, listOf(startValve))
    ))

    val minimumMinutesToOpenValve = nonZeroFlowRateValves.minOf { it.minimumMinutesToOpenValve }

    val visited = mutableSetOf<String>()
    var maxPressureReleased = 0

    while (toVisit.isNotEmpty()) {
        val (yourPath, elephantPath) = toVisit.removeFirst()

        maxPressureReleased = max(maxPressureReleased, yourPath.pressureReleased + elephantPath.pressureReleased)

        val hasTimeToMove = yourPath.minutesElapsed <= maxMinutesForLastMove || elephantPath.minutesElapsed <= maxMinutesForLastMove
        if (!hasTimeToMove) {
            continue
        }

        val hasAlreadyVisited = !visited.add("${yourPath.hash()} -- ${elephantPath.hash()}")
        if (hasAlreadyVisited) {
            continue
        }

        val hasVisitedAllValves = yourPath.visitedValves.size + elephantPath.visitedValves.size >= nonZeroFlowRateValves.size + 2
        if (hasVisitedAllValves) {
            continue
        }

        val remainingPotential =
            nonZeroFlowRateValves
                .minus(yourPath.visitedValves)
                .minus(elephantPath.visitedValves)
                .sortedBy { it.flowRate }
                .mapIndexed { index, it -> it.flowRate * (26 - minimumMinutesToOpenValve - (index * 2) - yourPath.minutesElapsed) }
                .sum()

        val canReachHigherPressureReleased = yourPath.pressureReleased + elephantPath.pressureReleased + remainingPotential > maxPressureReleased
        if (!canReachHigherPressureReleased) {
            continue
        }

        nonZeroFlowRateValves
            .filter { !yourPath.visitedValves.contains(it) && !elephantPath.visitedValves.contains(it) }
            .forEach { other ->
                val minutesElapsedAfterYouOpenValve = yourPath.minutesElapsed + yourPath.currentValve.minutesToOpenValve(other)

                if (minutesElapsedAfterYouOpenValve <= maxMinutesForLastMove) {
                    val minutesRemaining = maxMinutes - minutesElapsedAfterYouOpenValve
                    val additionalPressureReleased = other.flowRate * minutesRemaining

                    toVisit.add(
                        ValvePath(
                            other,
                            minutesElapsedAfterYouOpenValve,
                            yourPath.pressureReleased + additionalPressureReleased,
                            yourPath.visitedValves.plus(other)
                        ) to elephantPath
                    )
                }

                val minutesElapsedAfterElephantOpensValve = elephantPath.minutesElapsed + elephantPath.currentValve.minutesToOpenValve(other)

                if (minutesElapsedAfterElephantOpensValve <= maxMinutesForLastMove) {
                    val minutesRemaining = maxMinutes - minutesElapsedAfterElephantOpensValve
                    val additionalPressureReleased = other.flowRate * minutesRemaining

                    toVisit.add(
                        yourPath to ValvePath(
                            other,
                            minutesElapsedAfterElephantOpensValve,
                            elephantPath.pressureReleased + additionalPressureReleased,
                            elephantPath.visitedValves.plus(other)
                        )
                    )
                }
            }
    }

    return maxPressureReleased
}

object Day16 {

    fun List<String>.parseValves(): Map<String, Valve> {
        return this.associate { line ->
            val parts = line.split(" ")
            val id = parts[1]
            val flowRate = parts[4].split("=")[1].trimEnd(';').toInt()
            val connectedValveIds = parts.drop(9).map { it.trimEnd(',') }
            id to Valve(id, flowRate, connectedValveIds.toSet())
        }
    }

    fun List<Valve>.fillTravelTimes(startValve: Valve, valves: Map<String, Valve>) {
        fun computeTravelTime(source: Valve, destination: Valve): Int {
            val toVisit = LinkedList<Pair<Valve, Int>>()
            toVisit.add(source to 0)

            val visited = mutableSetOf<Valve>()

            while (toVisit.isNotEmpty()) {
                val (valve, time) = toVisit.removeFirst()

                if (!visited.add(valve)) {
                    continue
                }

                if (valve == destination) {
                    return time + 1
                }

                toVisit.addAll(
                    valve.connectedValveIds.map { valves[it]!! to (time + 1) }
                )
            }

            throw Exception("No path found from $source to $destination")
        }

        this.forEachIndexed { index, sourceValve ->
            this.drop(index + 1).forEach { destValve ->
                if (sourceValve != destValve) {
                    val time = computeTravelTime(sourceValve, destValve)
                    sourceValve.registerMinutesToOpenValve(destValve, time)
                    destValve.registerMinutesToOpenValve(sourceValve, time)
                }
            }
            startValve.registerMinutesToOpenValve(sourceValve, computeTravelTime(startValve, sourceValve))
        }
    }

    data class Valve(
        val id: String,
        val flowRate: Int,
        val connectedValveIds: Set<String>
    ) {
        private val minutesToOpenValve = mutableMapOf<Valve, Int>()

        fun minutesToOpenValve(other: Valve) =
            minutesToOpenValve[other] ?: throw Exception("Not possible to reach ${other.id} from $id")

        fun registerMinutesToOpenValve(other: Valve, time: Int) {
            minutesToOpenValve[other] = time
        }

        val minimumMinutesToOpenValve get() =
            minutesToOpenValve.values.min()
    }

    data class ValvePath(
        val currentValve: Valve,
        val minutesElapsed: Int,
        val pressureReleased: Int,
        val visitedValves: List<Valve>
    ) {
        fun hash() = visitedValves.joinToString(",") { it.id }
    }

}