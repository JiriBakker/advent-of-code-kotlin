import kotlin.random.Random

fun day25a(input: List<String>): Int {
    val (components, connections) = parseInput(input)

    while (true) {
        val groups =
            components
                .map { component -> mutableSetOf(component) }
                .toMutableSet()

        fun findGroupForComponent(component: String) =
            groups.first { group -> component in group }

        for (connection in connections.shuffled(Random)) {
            if (groups.size <= 2) break

            val (group1, group2) = connection.toList().map(::findGroupForComponent)
            if (group1 === group2) continue

            group1.addAll(group2)
            groups.removeIf { group -> group == group2 }
        }

        if (connections.count { (a, b) -> findGroupForComponent(a) != findGroupForComponent(b) } == 3) {
            return groups.map { group -> group.size }.reduce(Int::times)
        }
    }
}

private fun parseInput(input: List<String>): Pair<Set<String>, Set<Pair<String, String>>> =
    input
        .map { line ->
            val component = line.split(": ").first()
            val destinations = line.split(": ").last().split(" ")
            destinations.plus(component) to destinations.map { destination -> component to destination }.toSet()
        }
        .unzip()
        .let { (components, edges) -> components.flatten().toSet() to edges.flatten().toSet() }
