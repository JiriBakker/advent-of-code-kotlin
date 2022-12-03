private fun findInvalidNumber(nrs: List<Long>, preambleLength: Int): Long {
    val prevNrs = ArrayDeque(nrs.take(preambleLength))

    val nrToSumsLookup = mutableMapOf<Long, List<Long>>()
    val sumsToNrsLookup = mutableMapOf<Long, List<Pair<Long, Long>>>()

    fun updateLookups(nr1: Long, nr2: Long) {
        val sum = nr1 + nr2
            sumsToNrsLookup[sum] = sumsToNrsLookup.getOrDefault(sum, listOf()).plus(nr1 to nr2)
            nrToSumsLookup[nr1] = nrToSumsLookup.getOrDefault(nr1, listOf()).plus(sum)
            nrToSumsLookup[nr2] = nrToSumsLookup.getOrDefault(nr2, listOf()).plus(sum)
    }

    prevNrs.forEachIndexed { index, nr1 ->
        prevNrs.subList(index + 1, preambleLength).forEach { nr2 ->
            updateLookups(nr1, nr2)
        }
    }

    nrs.drop(preambleLength).forEach { nr ->
        if (nr !in sumsToNrsLookup.keys) {
            return nr
        }

        val nrToRemove = prevNrs.removeFirst()
        val sumsToRemove = nrToSumsLookup[nrToRemove]!!
        sumsToRemove.forEach { sumToRemove ->
            val newSums = sumsToNrsLookup[sumToRemove]?.filter { it.first != nrToRemove && it.second != nrToRemove }
            if (newSums != null && newSums.isNotEmpty()) {
                sumsToNrsLookup[sumToRemove] = newSums
            } else {
                sumsToNrsLookup.remove(sumToRemove)
            }
        }

        prevNrs.forEach { prevNr -> updateLookups(prevNr, nr) }

        prevNrs.addLast(nr)
    }

    error("No invalid number found")
}

fun day09a(input: List<String>, preambleLength: Int = 25): Long {
    return findInvalidNumber(input.map(String::toLong), preambleLength)
}

fun day09b(input: List<String>, preambleLength: Int = 25): Long {
    val nrs = input.map(String::toLong)
    val invalidNumber = findInvalidNumber(nrs, preambleLength)

    var startIndex = 0
    var endIndex = 1

    while (endIndex in nrs.indices) {
        val continguousSet = nrs.subList(startIndex, endIndex)
        val curSum = continguousSet.sum()
        when {
            curSum > invalidNumber -> startIndex++
            curSum < invalidNumber -> endIndex++
            else -> {
                return continguousSet.minOrNull()!! + continguousSet.maxOrNull()!!
            }
        }
    }

    error("No continguous set found")
}

