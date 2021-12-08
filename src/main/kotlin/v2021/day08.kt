package v2021

import util.sorted

fun day08a(input: List<String>): Int {
    val outputs = input.map { it.split(" | ")[1].split(" ") }
    return outputs.sumOf { output -> output.count{ listOf(2,3,4,7).contains(it.length) } }
}

private fun determineDigitSignals(signals: List<String>): Map<String, Int> {
    fun String.countMatches(other: String) = count { other.contains(it) }

    val one   = signals.first { it.length == 2 }
    val seven = signals.first { it.length == 3 }
    val four  = signals.first { it.length == 4 }
    val eight = signals.first { it.length == 7 }


    val zero = signals.first { signal ->
        signal.length == 6
            && four.countMatches(signal)  == 3
            && seven.countMatches(signal) == 3
    }

    val two = signals.first { signal ->
        signal.length == 5
            && one.countMatches(signal)  == 1
            && four.countMatches(signal) == 2
    }

    val three = signals.first { signal ->
        signal.length == 5
            && four.countMatches(signal)  == 3
            && seven.countMatches(signal) == 3
    }

    val five = signals.first { signal ->
        signal.length == 5
            && one.countMatches(signal)  == 1
            && four.countMatches(signal) == 3
    }

    val six = signals.first { signal ->
        signal.length == 6
            && one.countMatches(signal) == 1
    }

    val nine = signals.first { signal ->
        signal.length == 6
            && four.countMatches(signal)  == 4
            && seven.countMatches(signal) == 3
    }

    return mapOf(
        zero.sorted()  to 0,
        one.sorted()   to 1,
        two.sorted()   to 2,
        three.sorted() to 3,
        four.sorted()  to 4,
        five.sorted()  to 5,
        six.sorted()   to 6,
        seven.sorted() to 7,
        eight.sorted() to 8,
        nine.sorted()  to 9
    )
}

fun day08b(input: List<String>): Int {
    return input
        .map { it.split(" | ") }
        .sumOf { (signals, output) ->
            val digitSignals = determineDigitSignals(signals.split(" "))
            val outputSignals = output.split(" ").map { it.sorted() }

            digitSignals[outputSignals[0]]!! * 1000 +
                digitSignals[outputSignals[1]]!! * 100 +
                digitSignals[outputSignals[2]]!! * 10 +
                digitSignals[outputSignals[3]]!!
        }
}