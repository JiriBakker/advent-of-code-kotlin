package v2019.extra

import java.util.PriorityQueue

private class Flat(val x: Int, val y: Int)

private class Jump(val x: Int, val y: Int)

private fun parseCoordinates(line: String): Pair<Int, Int> {
    val coordinates = line.split(",")
    return Pair(coordinates[0].toInt(), coordinates[1].toInt())
}

private fun readInput(lines: List<String>): Pair<List<Flat>, List<Jump>> {
    var readingFlats = true
    val output: Pair<MutableList<Flat>, MutableList<Jump>> = Pair(mutableListOf(), mutableListOf())
    for (line in lines) {
        when (line) {
            "flats" -> readingFlats = true
            "sprongen" -> readingFlats = false
            else -> {
                val coordinates = parseCoordinates(line)
                when {
                    readingFlats -> output.first.add(Flat(coordinates.first, coordinates.second))
                    else -> output.second.add(Jump(coordinates.first, coordinates.second))
                }
            }
        }
    }

    return output
}

private fun canJumpTo(from: Flat, to: Flat, jump: Jump): Boolean {
    return to.x == from.x + jump.x + 1 && to.y <= from.y + jump.y
}

fun infiA(lines: List<String>): Int {
    val (flats, jumps) = readInput(lines)
    var jumpNr = 0

    flats
        .reduce { currentFlat, nextFlat ->
            if (canJumpTo(currentFlat, nextFlat, jumps[jumpNr])) {
                jumpNr++
                nextFlat
            }
            else currentFlat
        }

    return jumpNr + 1
}

private class FlatOption(val flat: Flat, val index: Int, val energy: Int)

fun infiB(lines: List<String>): Int {
    val (flats, _) = readInput(lines)

    val options = PriorityQueue<FlatOption> { a, b -> a.energy - b.energy }
    val visited: MutableSet<FlatOption> = mutableSetOf()

    options.add(FlatOption(flats[0], 0, 0))

    while (options.isNotEmpty()) {
        val current = options.poll()

        if (current.flat == flats.last()) {
            return current.energy
        }

        if (visited.any { it.flat == current.flat }) {
            continue
        }
        visited.add(current)

        val remainingFlats = flats.drop(current.index + 1)

        for (x in 0..4) {
            for (y in 0..4) {
                val nextFlatIndex = remainingFlats.indexOfFirst { canJumpTo(current.flat, it, Jump(x, y)) }
                if (nextFlatIndex >= 0) {
                    options.add(FlatOption(remainingFlats[nextFlatIndex], nextFlatIndex, current.energy + x + y))
                }
            }
        }
    }

    return 0
}