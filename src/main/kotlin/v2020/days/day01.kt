package v2020.days.day01

import util.product

private fun findMatch(target: Int, length: Int, nrs: List<Int>): List<Int> {
    val seen = mutableMapOf(1 to nrs.associateWith { listOf(it) }.toMutableMap())

    fun memoize(combination: List<Int>) {
        seen
            .getOrPut(combination.size, { mutableMapOf() })
            .putIfAbsent(combination.sum(), combination)
    }

    fun getMemoized(sum: Int, length: Int): List<Int>? {
        return seen[length]?.get(sum)
    }

    fun findMatchRecursive(remaining: Int, length: Int, curNrIndex: Int, used: List<Int>): List<Int>? {
        val memoized = getMemoized(remaining, length)
        if (memoized != null) {
            return used.plus(memoized)
        }

        if (length > 1) {
            (curNrIndex until nrs.size).forEach { index ->
                val nr = nrs[index]
                val newUsed = used.plus(nr)

                val match = findMatchRecursive(
                    remaining - nr,
                    length - 1,
                    curNrIndex + 1,
                    newUsed
                )

                if (match != null) {
                    return match
                }

                memoize(newUsed)
            }
        }

        return null
    }

    return findMatchRecursive(target, length, 0, emptyList()) ?: error("No match found")
}

fun day01a(input: List<String>): Int {
    val nrs = input.map(String::toInt)

    val match = findMatch(2020, 2, nrs)

    return match.product()
}

fun day01b(input: List<String>): Int {
    val nrs = input.map(String::toInt)

    val match = findMatch(2020, 3, nrs)

    return match.product()
}

