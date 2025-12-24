import util.sumOfLong
import kotlin.text.indices

fun day12a(input: List<String>): Long {
    val values = input.map { line ->
        val record = line.split(" ").first()
        val groups = line.split(" ").last().split(",").map(String::toInt)

        countPossibilities(record, 0, groups, mutableMapOf())
    }
    return values.sum()
}

fun day12b(input: List<String>): Long {
    return input.sumOfLong { line ->
        val chars = line.split(" ").first()
        val lengths = line.split(" ").last()

        val unfoldedChars = "$chars?$chars?$chars?$chars?$chars"
        val unfoldedGroups = "$lengths,$lengths,$lengths,$lengths,$lengths".split(",").map(String::toInt)

        countPossibilities(unfoldedChars, 0, unfoldedGroups, mutableMapOf())
    }
}

private fun countPossibilities(
    record: String,
    curIndex: Int,
    remainingGroups: List<Int>,
    cache: MutableMap<String, Long>
): Long {
    val hash = "${curIndex}_${remainingGroups.joinToString(",")}"

    return cache.getOrPut(hash) {
        if (curIndex !in record.indices) {
            // Reached end, check if all groups have been placed
            if (remainingGroups.isEmpty()) 1 else 0
        } else if (record[curIndex] == '.') {
            // If current is . we can skip it
            countPossibilities(record, curIndex + 1, remainingGroups, cache)
        } else {
            var sum = 0L
            if (record[curIndex] == '?') {
                // Option where we replace ? with .
                sum += countPossibilities(record, curIndex + 1, remainingGroups, cache)
            }
            if (remainingGroups.isNotEmpty()) {
                val endIndex = curIndex + remainingGroups.first()
                if (
                    endIndex - 1 in record.indices // Check if we have sufficient space for this group
                    && record.substring(curIndex, endIndex).none { it == '.' } // Check if there are no dots that disallow the group
                    && (endIndex !in record.indices || record[endIndex] != '#') // Check if we can place a dot after the group, or if we reached the end of the record
                ) {
                    // Mark group as completed and check if we can fulfill remaining groups with the rest of the record
                    sum += countPossibilities(record, endIndex + 1, remainingGroups.drop(1), cache)
                }
            }
            sum
        }

    }
}
