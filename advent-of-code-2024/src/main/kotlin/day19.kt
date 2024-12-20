import util.sumOfLong

fun day19a(input: List<String>) =
    input.parse()
        .let { (towels, designs) ->
            designs.count { design ->
                towels.countDesignPossibilities(design, mutableMapOf()) > 0
            }
        }

fun day19b(input: List<String>) =
    input.parse()
        .let { (towels, designs) ->
            designs.sumOfLong { design ->
                towels.countDesignPossibilities(design, mutableMapOf())
            }
        }

private fun List<String>.countDesignPossibilities(design: String, cache: MutableMap<String, Long>): Long {
    if (design in cache) {
        return cache[design]!!
    }

    return this
        .sumOfLong { towel ->
            when {
                design == towel -> 1
                design.startsWith(towel) -> this.countDesignPossibilities(design.substring(towel.length), cache)
                else -> 0
            }
        }
        .also { result ->
            cache[design] = result
        }
}

private fun List<String>.parse(): Pair<List<String>, List<String>> {
    val towels = this[0].split(", ")
    val designs = this.drop(2)

    return towels to designs
}