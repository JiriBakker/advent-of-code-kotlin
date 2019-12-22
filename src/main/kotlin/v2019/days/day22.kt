package v2019.days.day22

import v2019.util.safeMod
import v2019.util.rem
import v2019.util.modPow
import v2019.util.modInverse
import java.math.BigInteger

private fun parseInstructions(input: List<String>): List<Pair<String, Long>> {
    return input.map {
        val segments = it.split(" ")
        when (segments[1]) {
            "with" -> "deal" to segments[3].toLong()
            "into" -> "reverse" to 0L
            else -> "cut" to segments[1].toLong()
        }
    }
}

fun day22a(input: List<String>, nrOfCards: Long = 10007, cardNrToTrack: Long = 2019): Long {
    val instructions = parseInstructions(input)

    return instructions.fold(cardNrToTrack) { index, instruction ->
        when (instruction.first) {
            "deal" -> (instruction.second * index) % nrOfCards
            "reverse" -> (nrOfCards - 1) - index
            else -> (index - instruction.second).safeMod(nrOfCards)
        }
    }
}

fun day22b(
    input: List<String>,
    nrOfCards: Long = 119315717514047,
    targetIndex: Long = 2020,
    repeats: Long = 101741582076661
): Long {
    val instructions = parseInstructions(input)

    var offset = BigInteger.ZERO
    var delta = BigInteger.ONE

    instructions.forEach { instruction ->
        when (instruction.first) {
            "reverse" -> {
                delta = (delta * BigInteger.valueOf(-1)) % nrOfCards
                offset = (offset + delta) % nrOfCards
            }
            "cut" -> {
                offset = (offset + (delta * BigInteger.valueOf(instruction.second))) % nrOfCards
            }
            else -> {
                delta = (delta * BigInteger.valueOf(instruction.second).modInverse(nrOfCards)) % nrOfCards
            }
        }
    }

    val finalDelta = delta.modPow(repeats, nrOfCards)
    val finalOffset = (offset * (BigInteger.ONE - finalDelta) * (BigInteger.ONE - delta).modInverse(nrOfCards)) % nrOfCards
    val finalIndex = (finalOffset + finalDelta * BigInteger.valueOf(targetIndex)) % nrOfCards

    return finalIndex.toLong()
}
