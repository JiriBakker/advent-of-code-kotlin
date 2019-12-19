package v2015.days.day16

private class Aunt(val nr: Int, val properties: Map<String, Int>)

private fun parseAunts(input: List<String>): List<Aunt> {
    return input.map {
        val segments = it.split(" ")
        val nr = segments[1].trimEnd(':').toInt()

        val properties =
            segments
                .drop(2)
                .chunked(2)
                .associate { (name, value) ->
                    name.trimEnd(':') to value.trimEnd(',').toInt()
                }

        Aunt(nr, properties)
    }
}

private val propertiesToMatch = mapOf(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1
)

fun day16a(input: List<String>): Int {
    fun Aunt.hasCompatibleProperties(toCompare: Map<String, Int>): Boolean {
        return properties.keys.intersect(toCompare.keys).all { property ->
            properties[property] == toCompare[property]
        }
    }

    return parseAunts(input)
        .first { it.hasCompatibleProperties(propertiesToMatch) }
        .nr
}

fun day16b(input: List<String>): Int {
    fun Aunt.hasCompatibleProperties(toCompare: Map<String, Int>): Boolean {
        return properties.keys.intersect(toCompare.keys).all { property ->
            val thisVal = properties[property]!!
            val otherVal = toCompare[property]!!
            when (property) {
                "cats" ->  thisVal > otherVal
                "trees" -> thisVal > otherVal
                "pomeranians" -> thisVal < otherVal
                "goldfish" -> thisVal < otherVal
                else -> thisVal == otherVal
            }
        }
    }

    return parseAunts(input)
        .first { it.hasCompatibleProperties(propertiesToMatch) }
        .nr
}