fun day03a(input: List<String>) =
    input.joinToString("")
        .sumMultiplications()

fun day03b(input: List<String>) =
    input.joinToString("")
        .extractEnabledSections()
        .sumOf { section -> section.sumMultiplications() }

private fun String.extractEnabledSections() =
    this.split("do()")
        .mapNotNull { it.split("don't()").firstOrNull() }

private fun String.sumMultiplications() =
    this.split("mul(")
        .sumOf { section ->
            try {
                if (!section.contains(",")) 0L
                else if (!section.contains(")")) 0L
                else {
                    val (a, b) = section
                        .substringBefore(")")
                        .split(",")
                        .map(String::toLong)

                    a * b
                }
            } catch (e: Exception) {
                0L
            }
        }