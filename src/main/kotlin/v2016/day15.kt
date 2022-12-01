package v2016

import util.leastCommonMultiple
import util.safeMod

private data class Disc(val size: Long, val offset: Long)

private fun parseInput(input: List<String>): List<Disc> {
    return input.mapIndexed { discNr, line ->
        val segments = line.trimEnd('.').split(' ')
        val size = segments[3].toLong()
        val offset = segments[11].toLong()
        Disc(size, (offset + discNr + 1) % size)
    }
}

private fun findAlignmentTime(sumDiscsAbove: Disc, discBelow: Disc): Disc {
    var time = sumDiscsAbove.offset
    while (time < 10000000) {
        if ((time + discBelow.offset).safeMod(discBelow.size) == 0L) {
            return Disc(leastCommonMultiple(sumDiscsAbove.size, discBelow.size), time)
        }
        time += sumDiscsAbove.size
    }
    error("Discs never align (or takes too long)")
}

fun day15a(input: List<String>): Long {
    val discs = parseInput(input)

    return discs.fold(Disc(1, 0)) { sumDiscsAbove, disc ->
        findAlignmentTime(sumDiscsAbove, disc)
    }.offset
}

fun day15b(input: List<String>): Long {
    val discs = parseInput(input).plus(Disc(11, input.size + 1L))

    return discs.fold(Disc(1, 0)) { sumDiscsAbove, disc ->
        findAlignmentTime(sumDiscsAbove, disc)
    }.offset
}