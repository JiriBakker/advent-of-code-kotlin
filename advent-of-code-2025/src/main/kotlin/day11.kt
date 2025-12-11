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
    // Assumption: no loops in dependency graph
    // Assumption: if there are no loops, we either hit dac first or fft first (so we can hardcode an order into our logic, and swap if it doesn't give the correct answer)
    // Strategy:
    // - Keep track of dependencies both ways
    // - Start at end and traverse dependencies
    // - If for a device all the 'parents' (downstream devices) are known, we can determine the number of paths to out (sum of 'parent' paths)
    // - For each device, keep track of whether dac or fft have been passed (so if dac/fft appear in the paths downstream)
    // - When a 'parent' does not have dac/fft but globally we have seen it, it is not on a valid path, so we can ignore their path count
    // - Once we reach srv, we should have only counted paths that have dac and fft on its path, so we get the correct result
    val upstreamLinks = mutableMapOf<String, MutableList<String>>()

    val downstreamLinks = input.associate { line ->
        val source = line.split(':').first()
        val destinations = line.split(": ").last().split(' ')

        for (destination in destinations) {
            upstreamLinks.getOrPut(destination) { mutableListOf() }.add(source)
        }
        source to destinations
    }

    val states = mutableMapOf<String, DeviceState>()
    states["out"] = DeviceState(totalPaths = 1)

    val toVisit = mutableListOf<String>()
    toVisit.addAll(upstreamLinks["out"]!!)

    while (toVisit.isNotEmpty() && "svr" !in states) {
        val cur = toVisit.removeFirst()

        if (downstreamLinks[cur]!!.any { it !in states }) {
            toVisit.add(cur)
        }
        else if (cur !in states) {
            var totalPaths = 0L
            var curVisitedDac = cur == "dac"
            var curVisitedFft = cur == "fft"

            for (destination in downstreamLinks[cur]!!) {
                val state = states[destination]!!

                if (cur == "fft") {
                    // If we reach fft, we only accept paths that have dac in them
                    if (state.visitedDac) {
                        totalPaths += state.totalPaths
                    }
                } else if (cur == "svr") {
                    // If we reach svr, we only accept paths that have both dac and fft in them
                    if (state.visitedDac && state.visitedFft) {
                        totalPaths += state.totalPaths
                    }
                } else if (("dac" !in states || state.visitedDac) && ("fft" !in states || state.visitedFft)) {
                    // If dac and/or fft have been reached, only include paths that contain them
                    totalPaths += state.totalPaths
                }

                curVisitedDac = curVisitedDac || state.visitedDac
                curVisitedFft = curVisitedFft || state.visitedFft
            }

            if (cur == "svr") {
                return totalPaths
            }

            states[cur] = DeviceState(totalPaths = totalPaths, visitedDac = curVisitedDac, visitedFft = curVisitedFft)
        }

        if (cur != "svr") {
            toVisit.addAll(upstreamLinks[cur]!!.filter { it !in toVisit })
        }
    }

    throw Exception("svr not reached")
}