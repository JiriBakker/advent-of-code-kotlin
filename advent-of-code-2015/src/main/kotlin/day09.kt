import util.permute

private fun parseRoutes(input: List<String>): List<Triple<String, String, Int>> {
    return input.map {
        val (start, _, destination, _, distance) = it.split(" ")
        Triple(start, destination, distance.toInt())
    }
}

private fun computeRouteSums(routes: List<Triple<String, String, Int>>): List<Int> {
    val destinations = routes.flatMap { listOf(it.first, it.second) }.distinct()
    val theoreticalRoutes = destinations.permute().filter { it.size == destinations.size }

    return theoreticalRoutes
        .map { route ->
            route
                .zipWithNext()
                .mapNotNull { (start, destination) ->
                    routes.firstOrNull {
                        (it.first == start && it.second == destination) ||
                        (it.first == destination && it.second == start)
                    }
                }
        }
        .filter { it.size == destinations.size - 1 }
        .map { route -> route.sumOf { it.third }}
}

fun day09a(input: List<String>): Int {
    val routes = parseRoutes(input)
    return computeRouteSums(routes).minOrNull()!!
}

fun day09b(input: List<String>): Int {
    val routes = parseRoutes(input)
    return computeRouteSums(routes).maxOrNull()!!
}