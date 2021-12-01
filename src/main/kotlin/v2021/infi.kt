package v2021

import util.DoNotAutoExecute
import util.combine
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

private fun getComponentSums(itemCompositions: Map<String, Map<String, Long>>): Map<String, Long> {
    val componentSums = mutableMapOf<String, Long>()

    fun getComponentSum(name: String): Long {
        if (!itemCompositions.containsKey(name)) {
            return 1
        }
        if (!componentSums.containsKey(name)) {
            componentSums[name] =
                itemCompositions[name]!!
                    .sumOfLong { (name, count) ->
                        getComponentSum(name) * count
                    }
        }

        return componentSums[name]!!
    }

    itemCompositions.keys.forEach(::getComponentSum)

    return componentSums
}

fun infiA(input: List<String>): Long {
    val itemCompositions = input.parseItemCompositions()

    val componentSums = getComponentSums(itemCompositions)

    return componentSums.maxByOrNull { it.value }!!.value
}

@DoNotAutoExecute
fun infiB(input: List<String>, nrOfPresentsPacked: Int = 20): String {
    val itemCompositions = input.parseItemCompositions()
    val maxParts = input[0].split(" ")[0].toLong()

    fun isToy(name: String) =
        itemCompositions.none { it.value.keys.contains(name) }

    val componentSums = getComponentSums(itemCompositions)

    val sortedSums =
        componentSums
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
        .map { item -> componentSums.entries.first { it.value == item } }
        .map { it.key.first() }
        .sorted()
        .joinToString("")
}
