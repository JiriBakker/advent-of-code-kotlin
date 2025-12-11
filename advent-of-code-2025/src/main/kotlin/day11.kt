fun day11a(input: List<String>): Long {
    val links = input.associate { line ->
        val source = line.split(':').first()
        val destinations = line.split(": ").last().split(' ')
        source to destinations
    }

    val toVisit = mutableListOf<String>()
    toVisit.add("you")

    var pathCount = 0L
    while (toVisit.isNotEmpty()) {
        val cur = toVisit.removeFirst()

        if (cur == "out") {
            pathCount++
            continue
        }

        toVisit.addAll(links[cur]!!)
    }

    return pathCount
}

private data class DeviceState(
    var totalPaths: Long = 0,
    var visitedDac: Boolean = false,
    var visitedFft: Boolean = false
)

fun day11b(input: List<String>): Long {
    val links = input.associate { line ->
        val source = line.split(':').first()
        val destinations = line.split(": ").last().split(' ')
        source to destinations
    }

    val cache = mutableMapOf<Pair<String, String>, Long>()

    fun countPaths(start: String, end: String): Long {
        if (start == end) return 1

        if ((start to end) in cache) {
            return cache[start to end]!!
        }

        val nrOfPaths = links[start]?.sumOf { device ->
            countPaths(device, end)
        } ?: 0

        cache[start to end] = nrOfPaths

        return nrOfPaths
    }

    return countPaths("svr", "fft") *
            countPaths("fft", "dac") *
            countPaths("dac", "out")
}