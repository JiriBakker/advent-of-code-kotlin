package v2020.days.day07

private typealias BagHierarchy = Map<String, Map<String, Int>>

private fun parseHierarchy(input: List<String>): Pair<BagHierarchy, BagHierarchy> {
    val topDownHierarchy = mutableMapOf<String, Map<String, Int>>()
    val bottomUpHierarchy = mutableMapOf<String, Map<String, Int>>()

    input.forEach { line ->
        val words = line.split(" ")
        val outerBag = "${words[0]} ${words[1]}"

        words.drop(4).chunked(4)
            .forEach { bag ->
                if (bag[0] != "no") {
                    val (count, tint, color) = bag
                    val innerBag = "$tint $color"

                    topDownHierarchy[innerBag] = topDownHierarchy.getOrDefault(innerBag, mutableMapOf()).plus(outerBag to count.toInt())
                    bottomUpHierarchy[outerBag] = bottomUpHierarchy.getOrDefault(outerBag, mutableMapOf()).plus(innerBag to count.toInt())
                }
            }
    }

    return topDownHierarchy to bottomUpHierarchy
}

fun day07a(input: List<String>): Int {
    val (hierarchy, _) = parseHierarchy(input)
    val seen = mutableSetOf<String>()

    fun findNested(bag: String) {
        if (!seen.add(bag)) {
            return
        }

        hierarchy[bag]
            ?.keys
            ?.forEach { innerBag -> findNested(innerBag) }
    }

    findNested("shiny gold")
    return seen.size - 1
}

fun day07b(input: List<String>): Int {
    val (_, hierarchy) = parseHierarchy(input)

    fun countNested(bag: String): Int {
        return hierarchy[bag]
            ?.entries
            ?.sumOf { (innerBag, count) -> countNested(innerBag) * count + count }
            ?: 0
    }

    return countNested("shiny gold")
}
