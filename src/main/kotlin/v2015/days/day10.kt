package v2015.days.day10

private fun generate(string: String): String {
    return sequence {
        var count = 0
        for (i in string.indices) {
            count++
            val cur = string[i]
            val next = string.getOrNull(i + 1)
            if (cur != next) {
                yield("$count$cur")
                count = 0
            }
        }
    }.joinToString("")
}

private fun repeatGenerate(initialString: String, repeats: Int): String {
    return (0 until repeats).fold(initialString) { string, _ -> generate(string) }
}

fun day10a(input: String, repeats: Int = 40): Int {
    return repeatGenerate(input, repeats).length
}

fun day10b(input: String, repeats: Int = 50): Int {
    return repeatGenerate(input, repeats).length
}