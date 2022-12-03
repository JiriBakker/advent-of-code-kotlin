private fun parseContainers(input: List<String>): List<Int> {
    return input.map { it.toInt() }.sorted()
}

private fun findCombinations(availableContainers: List<Int>, remainingEggnog: Int, usedContainers: List<Int> = emptyList()): List<List<Int>> {
    if (remainingEggnog == 0) {
        return listOf(usedContainers)
    }

    return availableContainers
        .withIndex()
        .filter { (_, it) -> it <= remainingEggnog }
        .flatMap { (index, containerVolume) ->
            findCombinations(
                availableContainers.drop(index + 1),
                remainingEggnog - containerVolume,
                usedContainers.plus(containerVolume)
            )
        }
}

fun day17a(input: List<String>, eggnog: Int = 150): Int {
    val containers = parseContainers(input)

    return findCombinations(containers, eggnog).size
}

fun day17b(input: List<String>, eggnog: Int = 150): Int {
    val containers = parseContainers(input)

    val combinations = findCombinations(containers, eggnog)

    val minNrOfContainers = combinations.map { it.size }.minOrNull()!!

    return combinations.filter { it.size == minNrOfContainers }.size
}