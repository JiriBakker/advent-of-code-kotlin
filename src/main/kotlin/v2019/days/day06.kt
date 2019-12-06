package v2019.days.day06

import java.util.PriorityQueue

private class SpaceObject(
    val name: String,
    var orbits: SpaceObject? = null,
    val orbiters: MutableList<SpaceObject> = mutableListOf()
)

private const val COM = "COM"
private const val YOU = "YOU"
private const val SAN = "SAN"

private fun parseSpaceObjects(input: List<String>): Map<String, SpaceObject> {
    val spaceObjects = mutableMapOf<String, SpaceObject>()

    fun getOrCreate(name: String): SpaceObject {
        return spaceObjects.getOrPut(name) { SpaceObject(name) }
    }

    input
        .map { it.split(")") }
        .forEach { (centerName, orbiterName) ->
            val center = getOrCreate(centerName)
            val orbiter = getOrCreate(orbiterName)
            center.orbiters.add(orbiter)
            orbiter.orbits = center
        }

    return spaceObjects
}

private fun countOrbitDescendancies(spaceObject: SpaceObject, nrOfAncestors: Int): Int {
    return spaceObject.orbiters.sumBy {
        countOrbitDescendancies(it, nrOfAncestors + 1)
    } + nrOfAncestors
}

private fun findShortestPath(origin: SpaceObject, destination: SpaceObject): Int {
    val visited = mutableSetOf<String>()

    val toVisit = PriorityQueue<Pair<SpaceObject, Int>> { a, b -> a.second - b.second }
    toVisit.add(Pair(origin, 0))

    while (toVisit.isNotEmpty()) {
        val (current, distance) = toVisit.poll()

        if (current == destination) { return distance }
        if (visited.contains(current.name)) { continue }

        visited.add(current.name)

        if (current.orbits != null) {
            toVisit.add(Pair(current.orbits!!, distance + 1))
        }
        toVisit.addAll(current.orbiters.map { Pair(it, distance + 1) })
    }

    error("No path found from ${origin.name} to ${destination.name}")
}

fun day06a(input: List<String>): Int {
    val spaceObjects = parseSpaceObjects(input)

    val com = spaceObjects[COM] ?: error("No COM found")

    return countOrbitDescendancies(com, 0)
}

fun day06b(input: List<String>): Int {
    val spaceObjects = parseSpaceObjects(input)

    val you = spaceObjects[YOU] ?: error("No YOU found")
    val santa = spaceObjects[SAN] ?: error("No SAN found")

    // '- 2' because we're only count orbital transfers, so we don't have to count the orbit 'steps' for YOU and SAN themselves
    return findShortestPath(you, santa) - 2
}