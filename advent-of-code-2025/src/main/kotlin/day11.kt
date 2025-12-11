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
    // Assumption: if there are no loops, we either hit dac first or fft first (so we can hardcode the order into our logic)
    // Strategy:
    // - Keep track of dependencies both ways
    // - Start at end and traverse dependencies
    // - If for a device all the 'parents' (downstream devices) are known, we can determine the number of paths to out (sum of 'parent' paths)
    // - For each device, keep track of whether dac or fft have been passed (so if dac/fft appear in the paths downstream)
    // - Globally keep track of if we 'completed' dac/fft
    // - When a 'parent' does not have dac/fft but globally we have seen it, it is not on a valid path, so we can ignore their path count
    // - Once we reach srv, we should have only counted paths that have dac and fft on its path, so we get the correct result
    val inverseLinks = mutableMapOf<String, MutableList<String>>()

    val links = input.associate { line ->
        val source = line.split(':').first()
        val destinations = line.split(": ").last().split(' ')

        for (destination in destinations) {
            inverseLinks.getOrPut(destination) { mutableListOf() }.add(source)
        }
        source to destinations
    }


    val states = mutableMapOf<String, DeviceState>()

    val toVisit = mutableListOf<String>()

    for (source in inverseLinks["out"]!!) {
        states[source] = DeviceState(totalPaths = 1)
        toVisit.add(source)
    }

    var globalVisitedDac = false
    var globalVisitedFft = false

    while (toVisit.isNotEmpty() && "svr" !in states) {
        val cur = toVisit.removeFirst()

        if (cur in states) {
            if (cur != "svr") {
                for (source in inverseLinks[cur]!!) {
                    if (source !in toVisit) {
                        toVisit.add(source)
                    }
                }
            }
            continue
        }

        if (links[cur]!!.all { it in states }) {
            var totalPaths = 0L
            var curVisitedDac = cur == "dac"
            var curVisitedFft = cur == "fft"

            for (destination in links[cur]!!) {
                val state = states[destination]!!
                if (globalVisitedDac && !globalVisitedFft) {
                    if (state.visitedDac && !state.visitedFft) {
                        totalPaths += state.totalPaths
                        curVisitedDac = true
                    }
                } else if (globalVisitedFft) {
                    if (state.visitedFft) {
                        totalPaths += state.totalPaths
                        curVisitedFft = true
                    }
                } else {
                    totalPaths += state.totalPaths
                }
            }
            states[cur] = DeviceState(totalPaths = totalPaths, visitedDac = curVisitedDac, visitedFft = curVisitedFft)
            if (cur != "svr") {
                for (source in inverseLinks[cur]!!) {
                    if (source !in toVisit) {
                        toVisit.add(source)
                    }
                }
            }

            if (cur == "dac") {
                globalVisitedDac = true
            }
            if (cur == "fft") {
                globalVisitedFft = true
            }
        }
        else {
            toVisit.add(cur)
        }
    }

    return states["svr"]!!.totalPaths
}