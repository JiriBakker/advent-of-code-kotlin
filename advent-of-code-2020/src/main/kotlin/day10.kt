import kotlin.math.max

private fun getJoltages(input: List<String>): List<Int> {
    val adapters = input
        .map(String::toInt)
        .sorted()

    return listOf(0).plus(adapters).plus(adapters.last() + 3)
}

private fun getJoltDiffs(joltages: List<Int>): List<Int> {
    return joltages
        .zipWithNext { prev, next ->
            next - prev
        }
}

fun day10a(input: List<String>): Int {
    val joltDiffs = getJoltDiffs(getJoltages(input))

    return joltDiffs.count { it == 1 } * joltDiffs.count { it == 3 }
}

// Works if there are no diffs of 2 (only 1 and 3) and the longest sequence of diff 1's is 4
fun day10b_fast(input: List<String>): Long {
    val joltDiffs = getJoltDiffs(getJoltages(input))

    return joltDiffs
        .fold(Pair(1L, 0))
            { (comboCount, seqOfOnesLength), joltDiff ->
                if (joltDiff == 1) {
                    comboCount to seqOfOnesLength + 1
                } else {
                    when (seqOfOnesLength) {
                        4 -> comboCount * 7 to 0
                        3 -> comboCount * 4 to 0
                        2 -> comboCount * 2 to 0
                        else -> comboCount to 0
                    }
                }
            }
        .first
}

// Works on all inputs, but potentially slow if a 'group' is big
fun day10b(input: List<String>): Long {
    val joltages = getJoltages(input)
    val joltDiffs = getJoltDiffs(joltages)

    var group = mutableListOf<Int>()
    var comboCount = 1L

    joltDiffs
        .forEachIndexed { index, joltDiff ->
                group.add(joltages[index])
                if (joltDiff == 3) {
                    comboCount *= max(1, countPaths(group))
                    group = mutableListOf()
                }
            }

    return comboCount
}

private fun countPaths(group: List<Int>, curIndex: Int = 0): Int {
    if (curIndex == group.size - 1) {
        return 1
    }

    fun canReach(index1: Int, index2: Int): Boolean {
        return group[index1] + 3 >= group.getOrElse(index2) { Int.MAX_VALUE }
    }

    var count = 0
    if (canReach(curIndex,curIndex + 1)) {
        count += countPaths(group, curIndex + 1)
    }
    if (canReach(curIndex, curIndex + 2)) {
        count += countPaths(group, curIndex + 2)
    }
    if (canReach(curIndex,  curIndex + 3)) {
        count += countPaths(group, curIndex + 3)
    }
    return count
}
