package v2020.days.day01

private tailrec fun findMatchingPair(
    target: Int,
    nrs: List<Int>,
    curIndex: Int,
    seen: MutableSet<Int>
): Pair<Int, Int>? {
    if (curIndex >= nrs.size) {
        return null
    }

    val toFind = target - nrs[curIndex]
    if (seen.contains(toFind)) {
        return toFind to nrs[curIndex]
    }

    seen.add(nrs[curIndex])

    return findMatchingPair(target, nrs, curIndex + 1, seen)
}

fun day01a(input: List<String>): Int {
    val match = findMatchingPair(2020, input.map(String::toInt), 0, mutableSetOf()) ?: error("No match found")
    return match.first * match.second
}

fun day01b(input: List<String>): Int {
    val nrs = input.map(String::toInt)

    nrs.forEachIndexed { index, nr ->
        val match = findMatchingPair(2020 - nr, input.map(String::toInt), index + 1, mutableSetOf())
        if (match != null) {
            return match.first * match.second * nr
        }
    }

    error("No match found")
}
