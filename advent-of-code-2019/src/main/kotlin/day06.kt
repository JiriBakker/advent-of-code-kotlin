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
    return spaceObject.orbiters.sumOf {
        countOrbitDescendancies(it, nrOfAncestors + 1)
    } + nrOfAncestors
}

private fun findCombinedDistanceToFirstCommonAncestor(a: SpaceObject, b: SpaceObject): Int {
    fun distancesTo(spaceObject: SpaceObject): List<Pair<String, Int>> {
        return generateSequence(spaceObject) { it.orbits }
            .mapIndexed { distance, obj -> obj.name to distance }
            .toList()
            .reversed()
    }

    val firstDiff =
        distancesTo(a)
            .zip(distancesTo(b))
            .first { it.first.first != it.second.first }

    return firstDiff.first.second + firstDiff.second.second + 2
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
    return findCombinedDistanceToFirstCommonAncestor(you, santa) - 2
}