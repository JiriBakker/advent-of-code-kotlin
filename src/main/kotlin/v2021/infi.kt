package v2021

import util.DoNotAutoExecute
import util.filteredValues
import util.sumOfLong

private fun List<String>.parseItemCompositions() =
    this
        .drop(1)
        .associate { line ->
            val (key, contents) = line.split(": ")
            val items = contents.split(", ").associate {
                val (count, name) = it.split(" ")
                name to count.toLong()
            }
            key to items
        }

private fun getItemSums(itemCompositions: Map<String, Map<String, Long>>): Map<String, Long> {
    val itemSums = mutableMapOf<String, Long>()

    fun getItemSum(name: String): Long {
        if (!itemCompositions.containsKey(name)) {
            return 1
        }
        if (!itemSums.containsKey(name)) {
            itemSums[name] =
                itemCompositions[name]!!
                    .sumOfLong { (name, count) ->
                        getItemSum(name) * count
                    }
        }

        return itemSums[name]!!
    }

    itemCompositions.keys.forEach(::getItemSum)

    return itemSums
}

fun infiA(input: List<String>): Long {
    val itemCompositions = input.parseItemCompositions()

    val itemSums = getItemSums(itemCompositions)

    return itemSums.maxByOrNull { it.value }!!.value
}

@DoNotAutoExecute
fun infiB(input: List<String>, nrOfPresentsPacked: Int = 20): String {
    val itemCompositions = input.parseItemCompositions()
    val maxParts = input[0].split(" ")[0].toLong()

    fun isToy(name: String) =
        itemCompositions.none { it.value.keys.contains(name) }

    val itemSums = getItemSums(itemCompositions)

    val sortedSums =
        itemSums
            .filteredValues { isToy(it.key) }
            .sortedDescending() // Taking high first will speed up finding match

    fun findMatchingCombo(maxLength: Int, remainingParts: Long): List<Long>? {
        if (maxLength > 0) {
            sortedSums.forEach { sum ->
                if (sum > remainingParts) return@forEach
                if (sum == remainingParts && maxLength == 1) return listOf(sum)

                val result = findMatchingCombo(maxLength - 1, remainingParts - sum)
                if (result != null) {
                    return listOf(sum) + result
                }
            }
        }

        return null
    }

    val match =
        findMatchingCombo(nrOfPresentsPacked, maxParts)
            ?: throw Error("No solution found")

    return match
        .map { item -> itemSums.entries.first { it.value == item } }
        .map { it.key.first() }
        .sorted()
        .joinToString("")
}
