package v2022

fun day03a(input: List<String>) =
    input.sumOf { backpack ->
        val common = backpack.firstCompartment.intersect(backpack.secondCompartment)
        common.computePriority()
    }

fun day03b(input: List<String>) =
    input
        .chunked(3)
        .sumOf { (firstLine, secondLine, thirdLine) ->
            val common = intersect(firstLine, secondLine, thirdLine)
            common.computePriority()
        }

private val String.firstCompartment get() = take(length / 2).toSet()
private val String.secondCompartment get() = drop(length / 2).toSet()

private fun Set<Char>.computePriority() =
    if (('A' .. 'Z').contains(this.first())) {
        this.first() - 'A' + 27
    } else {
        this.first() - 'a' + 1
    }

private fun intersect(first: String, second: String, third: String) =
    first.toSet().intersect(second.toSet()).intersect(third.toSet())