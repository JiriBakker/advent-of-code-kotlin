package v2015.days.day19

private fun parseInput(input: List<String>): Pair<String, Map<String, List<String>>> {
    val startMolecule = input.last()
    val replacements =
        input
            .dropLast(2)
            .map {
                val (atom, output) = it.split(" => ")
                atom to output
            }
            .groupBy { it.first }
            .mapValues { it.value.map { output -> output.second } }

    return startMolecule to replacements
}

fun day19a(input: List<String>): Int {
    val (startMolecule, replacements) = parseInput(input)

    return sequence {
        var i = 0
        while (i < startMolecule.length) {
            val prefix = startMolecule.take(i)
            val curChar = startMolecule[i]

            if (replacements.containsKey("$curChar")) {
                val suffix = startMolecule.drop(i + 1)
                replacements["$curChar"]!!.forEach {
                    yield(prefix + it + suffix)
                }
            } else {
                val nextChar = startMolecule.getOrElse(i + 1) { ' ' }
                if (replacements.containsKey("$curChar$nextChar")) {
                    val suffix = startMolecule.drop(i + 2)
                    replacements["$curChar$nextChar"]!!.forEach {
                        yield(prefix + it + suffix)
                    }
                    i++
                }
            }
            i++
        }
    }.distinct().count()
}

fun day19b(input: List<String>): Int {
    var (startMolecule, replacements) = parseInput(input)

    return 0
}

// 501 low
// 603 high