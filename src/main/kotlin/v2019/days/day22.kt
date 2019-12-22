package v2019.days.day22

import v2019.util.rem
import v2019.util.times
import v2019.util.minus
import v2019.util.modPow
import v2019.util.modInverse
import java.math.BigInteger

fun day22a(instructions: List<String>, nrOfCards: Long = 10007, cardNrToTrack: Long = 2019): Long {
    return instructions.fold(cardNrToTrack) { index, instruction ->
        val segments = instruction.split(" ")
        when (segments[1]) {
            "with" -> {
                val increment = segments[3].toLong()
                (increment * index) % nrOfCards
            }
            "into" -> (nrOfCards - 1) - index
            else -> {
                val cutIndex = segments[1].toLong()
                (index - cutIndex) % nrOfCards
            }
        }
    }
}


fun day22b(
    instructions: List<String>,
    nrOfCards: Long = 119315717514047,
    targetIndex: Long = 2020,
    repeats: Long = 101741582076661
): Long {
    var offset = BigInteger.ZERO
    var delta = BigInteger.ONE

    instructions.forEach { instruction ->
        val segments = instruction.split(" ")
        when (segments[1]) {
            "into" -> {
                delta = (delta * -1) % nrOfCards
                offset = (offset + delta) % nrOfCards
            }
            "with" -> {
                val increment = segments[3].toInt()
                delta = (delta * increment.modInverse(nrOfCards)) % nrOfCards
            }
            else -> {
                val cutIndex = segments[1].toInt()
                offset = (offset + (delta * cutIndex)) % nrOfCards
            }

        }
    }

    val finalDelta = delta.modPow(repeats, nrOfCards)
    val finalOffset = (offset * (1 - finalDelta) * (1 - delta).modInverse(nrOfCards)) % nrOfCards
    val finalIndex = (finalOffset + finalDelta * targetIndex) % nrOfCards

    return finalIndex.toLong()
}
