fun day23a(input: List<String>) =
    input.parseLinks()
        .findGroups()
        .count { it.first[0] == 't' || it.second[0] == 't' ||  it.third[0] == 't' }

fun day23b(input: List<String>) =
    input.parseLinks()
        .expandSets()
        .findOverlappingSets()
        .sorted()
        .joinToString(",")

private fun List<String>.parseLinks(): Map<String, Set<String>> {
    val links = mutableMapOf<String, Set<String>>()
    for (line in this) {
        val (a, b) = line.split("-")
        links[a] = (links[a] ?: emptySet()) + b
        links[b] = (links[b] ?: emptySet()) + a
    }
    return links
}

private fun Map<String, Set<String>>.findGroups(): Set<Triple<String, String, String>> {
    val groups = mutableSetOf<Triple<String, String, String>>()
    this.entries.forEach { (computer, linkedComputers) ->
        for (i in linkedComputers.indices) {
            for (j in i + 1 until linkedComputers.size) {
                val a = linkedComputers.elementAt(i)
                val b = linkedComputers.elementAt(j)

                if (a != b && this[a]!!.contains(b)) {
                    groups.add(listOf(computer, a, b).sorted().let { Triple(it[0], it[1], it[2]) })
                }
            }
        }
    }
    return groups
}

private fun Map<String, Set<String>>.expandSets(): Map<String, Map<String, Int>> {
    // Expand set to include the sets of linked computers
    val sets = mutableMapOf<String, MutableMap<String, Int>>()

    fun addToSet(computer: String, linkedComputer: String) {
        if (computer == linkedComputer) {
            return
        }
        sets[computer]!![linkedComputer] = sets[computer]!!.getOrDefault(linkedComputer, 0) + 1
    }

    for ((computer, linkedComputers) in this) {
        sets[computer] = mutableMapOf()
        for (linkedComputer in linkedComputers) {
            addToSet(computer, linkedComputer)
            val otherLinks = this[linkedComputer]!!
            for (otherLink in otherLinks) {
                addToSet(computer, otherLink)
            }
        }
    }

    return sets
}

private fun Map<String, Map<String, Int>>.findOverlappingSets(): List<String> {
    // If we count how many times a certain computer is in an expanded set, and that count value is found the same
    // number of times as the count value itself ('occurrences' == 'count value'), then we consider it a match
    val matches = mutableListOf<String>()
    for (set in this) {
        val counts = set.value.values.groupBy { it }.mapValues { it.value.size }.filter { it.key == it.value }
        if (counts.isNotEmpty()) {
            matches.add(set.key)
        }

    }
    return matches
}