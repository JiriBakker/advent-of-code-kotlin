import util.sumOfLong
import kotlin.text.indices

fun day12a(input: List<String>) =
    input.sumOfLong { line ->
        val record = line.split(" ").first()
        val groups = line.split(" ").last().split(",").map(String::toInt)

        countPossibilities(record, groups)
    }

fun day12b(input: List<String>) =
    input.sumOfLong { line ->
        val record = line.split(" ").first()
        val groups = line.split(" ").last()

        val unfoldedRecord = "$record?$record?$record?$record?$record"
        val unfoldedGroups = "$groups,$groups,$groups,$groups,$groups".split(",").map(String::toInt)

        countPossibilities(unfoldedRecord, unfoldedGroups)
    }

private fun countPossibilities(record: String, groups: List<Int>): Long {
    val cache = mutableMapOf<Pair<Int, Int>, Long>()

    fun recursePossibilities(recordIndex: Int, groupIndex: Int): Long {
        return cache.getOrPut(recordIndex to groupIndex) {
            if (recordIndex !in record.indices) {
                // Reached end, check if all groups have been placed
                if (groupIndex == groups.size) 1 else 0
            } else if (record[recordIndex] == '.') {
                // If current is . we can continue to the next character
                recursePossibilities(recordIndex + 1, groupIndex)
            } else {
                var sum = 0L
                if (record[recordIndex] == '?') {
                    // Count the possibilities if we replace ? with .
                    sum += recursePossibilities(recordIndex + 1, groupIndex)
                }
                if (groupIndex in groups.indices) {
                    val endIndex = recordIndex + groups[groupIndex]
                    if (
                        endIndex - 1 in record.indices // Check if we have sufficient space for current group
                        && record.substring(recordIndex, endIndex).none { it == '.' } // Check if there are no dots in the way that block the group
                        && (endIndex !in record.indices || record[endIndex] != '#') // Check if we can place a dot after the group, or if we reached the end of the record
                    ) {
                        // Mark group as completed and check if we can place remaining groups with the rest of the record
                        sum += recursePossibilities(endIndex + 1, groupIndex + 1)
                    }
                }
                sum
            }
        }
    }

    return recursePossibilities(0, 0)
}
