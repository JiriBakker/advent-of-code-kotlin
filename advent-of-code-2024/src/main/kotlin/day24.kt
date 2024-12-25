fun day24a(input: List<String>): Long {
    val inputs = input.parseInputs()
    val rules = input.parseRules()

    return applyOperations(inputs.toMutableMap(), rules)
}

fun day24b(input: List<String>): String {
    val rules = input.parseRules()

    val maxZ =
        rules
            .filter { it.second.isZ() }
            .maxBy { it.second.drop(1).toInt() }
            .second


    return sequence {
        rules.forEach { (source, dest) ->
            val (key1, operator, key2) = source

            when (operator) {
                "XOR" -> {
                    if (!dest.isInputOrOutput() && !key1.isInputOrOutput() && !key2.isInputOrOutput()) {
                        // We cannot have an XOR rule where none of the rule inputs or dest are inputs or outputs (x, y, or z)
                        yield(dest)
                    } else if (rules.hasOrRulesWithDestAsInput(dest)) {
                        // We cannot have an XOR rule if there are also OR rules with dest as input
                        yield(dest)
                    }
                }
                "AND" -> {
                    if (!key1.isFirstX() && !key2.isFirstX() && rules.hasNonOrRulesWithDestAsInput(dest)) {
                        // We cannot have an XOR or AND rule with dest as input, unless either input is x00
                        yield(dest)
                    } else if (dest.isZ() && dest != maxZ) {
                        // We cannot have an AND rule with a Z output, unless it is the last Z
                        yield(dest)
                    }
                }
                "OR" -> {
                    if (dest.isZ() && dest != maxZ) {
                        // We cannot have an OR rule with a Z output, unless it is the last Z
                        yield(dest)
                    }
                }
            }
        }
    }.sorted().joinToString(",")
}

private fun List<String>.parseInputs() =
    this.takeWhile { it.isNotBlank() }.associate { line ->
        val (key, value) = line.split ( ": ")
        key to value.toInt()
    }

private fun List<String>.parseRules() =
    this.dropWhile { it.isNotBlank() }.drop(1).map { line ->
        val (source, dest) = line.split(" -> ")
        val (key1, operator, key2) = source.split(" ")
        Triple(key1, operator, key2) to dest
    }

private typealias Rule = Pair<Triple<String, String, String>, String>

private fun applyOperations(values: MutableMap<String, Int>, rules: List<Rule>): Long {
    val ruleQueue = mutableListOf(*rules.toTypedArray())

    while (ruleQueue.isNotEmpty()) {
        val (source, dest) = ruleQueue.removeFirst()
        val (key1, operator, key2) = source

        if (key1 !in values || key2 !in values) {
            ruleQueue.add(source to dest)
            continue
        }
        val value1 = values[key1]!!
        val value2 = values[key2]!!

        val result = when (operator) {
            "AND" -> value1 and value2
            "OR" -> value1 or value2
            "XOR" -> value1 xor value2
            else -> error("Unknown operator: $operator")
        }
        values[dest] = result
    }

    return getValue('z', values)
}

private fun getValue(prefix: Char, values: Map<String, Int>): Long {
    return values
        .filter { it.key[0] == prefix }
        .entries
        .sortedByDescending { it.key }
        .map { it.value }
        .joinToString("")
        .toLong(2)
}

private fun String.isFirstX() = this == "x00"
private fun String.isX() = this[0] == 'x'
private fun String.isY() = this[0] == 'y'
private fun String.isZ() = this[0] == 'z'
private fun String.isInputOrOutput() = isX() or isY() or isZ()

private fun List<Rule>.hasNonOrRulesWithDestAsInput(dest: String) =
    any { (source, _) ->
        val (key1, operator, key2) = source
        operator != "OR" && (dest == key1 || dest == key2)
    }

private fun List<Rule>.hasOrRulesWithDestAsInput(dest: String) =
    any { (source, _) ->
        val (key1, operator, key2) = source
        operator == "OR" && (dest == key1 || dest == key2)
    }
