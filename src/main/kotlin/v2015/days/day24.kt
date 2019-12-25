package v2015.days.day24

private fun findCombinationsForWeight(available: List<Long>, used: List<Long>, weight: Long): List<List<Long>> {
    if (weight == 0L) {
        return listOf(used)
    }
    if (available.isEmpty() || weight < available.min()!! || weight > available.sum()) {
        return listOf()
    }

    val viable = available.takeWhile { it <= weight }
    return viable.flatMap { findCombinationsForWeight(viable.take(viable.indexOf(it)), used + it, weight - it) }
}

private fun computeQuantumEntanglement(group: List<Long>): Long {
    return group.reduce { product, weight -> product * weight }
}

private fun findQuantumEntanglementOfOptimalDistribution(packages: List<Long>, nrOfGroups: Int): Long {
    val totalWeight = packages.sum()
    val weightPerGroup = totalWeight / nrOfGroups

    val possibleGroups = findCombinationsForWeight(packages, listOf(), weightPerGroup)

    return possibleGroups
        .sortedWith(compareBy({ it.size }, { computeQuantumEntanglement(it) }))
        .first()
        .let(::computeQuantumEntanglement)
}

fun day24a(input: List<String>): Long {
    val packages = input.map { it.toLong() }
    return findQuantumEntanglementOfOptimalDistribution(packages, 3)
}

fun day24b(input: List<String>): Long {
    val packages = input.map { it.toLong() }
    return findQuantumEntanglementOfOptimalDistribution(packages, 4)
}
