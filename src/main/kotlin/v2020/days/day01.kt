package v2020.days.day01

private fun findMatchingPair(target: Int, nrs: List<Int>, startIndex: Int = 0): Pair<Int, Int>? {
    val seen = mutableSetOf<Int>()

    tailrec fun findRecursive(curIndex: Int): Pair<Int, Int>? {
        if (curIndex >= nrs.size) {
            return null
        }

        val toFind = target - nrs[curIndex]
        if (seen.contains(toFind)) {
            return toFind to nrs[curIndex]
        }

        seen.add(nrs[curIndex])

        return findRecursive(curIndex + 1)
    }

    return findRecursive(startIndex)
}

fun day01a(input: List<String>): Int {
    val nrs = input.map(String::toInt)
    val match = findMatchingPair(2020, nrs, 0) ?: error("No match found")
    return match.first * match.second
}

fun day01b(input: List<String>): Int {
    val nrs = input.map(String::toInt)

    nrs.forEachIndexed { index, nr ->
        val match = findMatchingPair(2020 - nr, nrs, index + 1)
        if (match != null) {
            return match.first * match.second * nr
        }
    }

    error("No match found")
}
