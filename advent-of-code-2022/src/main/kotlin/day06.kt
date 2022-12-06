import Day06.findUniqueSequenceOffset

fun day06a(input: String) =
    input.findUniqueSequenceOffset(4)

fun day06b(input: String) =
    input.findUniqueSequenceOffset(14)

object Day06 {

    // Iterate over string and keep track of counts (each character accessed once)
    fun String.findUniqueSequenceOffset(sequenceLength: Int): Int {
        var uniqueCount = 0
        val letterCounts = mutableMapOf<Char, Int>()

        fun countCharAtIndex(index: Int) {
            val char = this[index]
            val letterCount = letterCounts.getOrDefault(char, 0)
            if (letterCount == 0) {
                uniqueCount++
            }
            letterCounts[char] = letterCount + 1
        }

        fun uncountCharAtIndex(index: Int) {
            val charToRemove = this[index]
            val letterCountToLower = letterCounts[charToRemove]!!
            if (letterCountToLower == 1) {
                uniqueCount--
            }
            letterCounts[charToRemove] = letterCountToLower - 1
        }

        repeat(this.length) { index ->
            if (index >= sequenceLength) {
                uncountCharAtIndex(index - sequenceLength)
            }
            countCharAtIndex(index)

            if (uniqueCount == sequenceLength) {
                return index + 1
            }
        }

        throw Exception("Unique sequence not found")
    }

    // Compute letter counts per substring and determine if unique (each character accessed `sequenceLength` times)
    fun String.findUniqueSequenceOffsetSuboptimal(sequenceLength: Int): Int {
        repeat(this.length - sequenceLength) { offset ->
            val letterCounts = this.substring(offset, offset + sequenceLength).countLetters()
            if (letterCounts.values.none { it > 1 }) {
                return offset + sequenceLength
            }
        }

        throw Exception("Unique sequence not found")
    }

    private fun String.countLetters() =
        this.groupBy { it }.mapValues { it.value.size }
}

