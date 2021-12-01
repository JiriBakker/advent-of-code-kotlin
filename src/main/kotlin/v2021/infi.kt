package v2021

import util.DoNotAutoExecute
import v2021.Infi.parse

private object Infi {
    fun List<String>.parse() =
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
}

private fun getComponentSums(itemCompositions: Map<String, Map<String, Long>>): Map<String, Long> {
    val componentSums = mutableMapOf<String, Long>()

    fun getComponentSum(name: String): Long {
        if (!itemCompositions.containsKey(name)) return 1
        if (componentSums.containsKey(name)) return componentSums[name]!!

        componentSums[name] =
            itemCompositions[name]!!
                .entries
                .sumOf { (name, count) ->
                    getComponentSum(name) * count
                }

        return componentSums[name]!!
    }

    itemCompositions.keys.forEach(::getComponentSum)

    return componentSums
}

fun infiA(input: List<String>): Long {
    val itemCompositions = input.parse()

    val componentSums = getComponentSums(itemCompositions)

    return componentSums.maxByOrNull { it.value }!!.value
}

@DoNotAutoExecute
fun infiB(input: List<String>, nrOfPresentsPacked: Int = 20): String {
    val itemCompositions = input.parse()
    val maxParts = input[0].split(" ")[0].toLong()

    val toys = itemCompositions.filter { item ->
        itemCompositions.none { it.value.keys.contains(item.key) }
    }

    val componentSums = getComponentSums(itemCompositions)



    val sortedSums =
        componentSums
            .entries
            .filter { toys.containsKey(it.key) }
            .associate { it.value to it.key }
            .toSortedMap(compareByDescending { it })

    fun findMatchingCombo(maxLength: Int, remainingParts: Long): List<Long>? {
        if (maxLength > 0) {
            sortedSums.keys.forEach { count ->
                if (count > remainingParts) return@forEach
                if (count == remainingParts && maxLength == 1) return listOf(count)

                val result = findMatchingCombo(maxLength - 1, remainingParts - count)
                if (result != null) {
                    return listOf(count) + result
                }
            }
        }

        return null
    }

    val match =
        findMatchingCombo(nrOfPresentsPacked, maxParts)
            ?: throw Error("No solution found")

    return match
        .map { sortedSums[it]!!.first() }
        .sorted()
        .joinToString("")
}
