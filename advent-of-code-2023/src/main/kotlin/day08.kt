import util.leastCommonMultiple

fun day08a(input: List<String>): Long {
    val directions = input.first()
    val mappings = parseMappings(input.drop(2))

    return countSteps("AAA", directions, mappings)
}

fun day08b(input: List<String>): Long {
    val directions = input.first()
    val mappings = parseMappings(input.drop(2))

    val starts = mappings.filter { it.key.endsWith('A') }

    val stepsRequired = starts.map { (start, _) ->
        countSteps(start, directions, mappings)
    }

    return leastCommonMultiple(stepsRequired)
}

private fun countSteps(start: String, directions: String, mappings: Map<String, Pair<String, String>>): Long {
    var cur = start
    var steps = 0L
    while (true) {
        directions.forEach { direction ->
            steps++

            cur = if (direction == 'L') {
                mappings[cur]!!.first
            } else {
                mappings[cur]!!.second
            }

            if (cur.endsWith('Z')) {
                return steps
            }
        }
    }
}

private fun parseMappings(input: List<String>) =
    input.associate { line ->
        val source = line.split(" = (")[0]
        val left = line.split(" = (")[1].split(", ")[0]
        val right = line.split(" = (")[1].split(", ")[1].dropLast(1)

        source to (left to right)
    }
