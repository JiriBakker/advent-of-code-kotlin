fun day02a(input: List<String>) =
    input.sumOf { line ->
        val id = line.split(':').first().split(' ').last().toInt()
        val maxCubes = getMaxCubesForGame(line)

        if (maxCubes["red"]!! <= 12 && maxCubes["green"]!! <= 13 && maxCubes["blue"]!! <= 14)
            id
        else
            0
    }

fun day02b(input: List<String>) =
    input.sumOf { line ->
        val maxCubes = getMaxCubesForGame(line)
        maxCubes["red"]!! * maxCubes["green"]!! * maxCubes["blue"]!!
    }

private fun getMaxCubesForGame(line: String): Map<String, Int> {
    return line
        .split(": ")[1] // Drop game ID part
        .split("; ")    // Split reveals
        .flatMap { reveal ->
            reveal
                .split(", ")
                .map {
                    val color = it.split(' ')[1]
                    val count = it.split(' ').first().toInt()
                    color to count
                }
        }
        .groupBy { (color, _) -> color }
        .mapValues { reveals -> reveals.value.maxOf { (_, count) -> count } } // Take make of count per color
}
