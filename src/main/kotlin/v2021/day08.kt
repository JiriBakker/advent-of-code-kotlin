package v2021

import util.sorted

fun day08a(input: List<String>): Int {
    val outputs = input.map { it.split(" | ")[1].split(" ") }
    return outputs.sumOf { output -> output.count{ listOf(2,3,4,7).contains(it.length) } }
}

private fun determineDigitSignals(signals: List<String>): Map<String, Int> {
    class Digit {
        private val candidates = signals.toMutableList()

        fun length(l: Int) {
            candidates.removeIf { it.length != l }
        }

        fun matches(digit: Digit, count: Int) {
            candidates.removeIf { candidate ->
                candidate.count {
                    digit.letters.contains(it) } != count
            }
        }

        val letters get() = candidates.single().sorted()
    }

    fun digit(lambda: Digit.() -> Unit) = Digit().apply(lambda)

    val one   = digit { length(2) }
    val four  = digit { length(4) }
    val seven = digit { length(3) }
    val eight = digit { length(7) }

    val zero = digit {
        length(6)
        matches(four, 3)
        matches(seven, 3)
    }

    val two = digit {
        length(5)
        matches(one, 1)
        matches(four, 2)
    }

    val three = digit {
        length(5)
        matches(four, 3)
        matches(seven, 3)
    }

    val five = digit {
        length(5)
        matches(one, 1)
        matches(four, 3)
    }

    val six = digit {
        length(6)
        matches(one, 1)
    }

    val nine = digit {
        length(6)
        matches(four, 4)
        matches(seven, 3)
    }

    return mapOf(
        zero.letters  to 0,
        one.letters   to 1,
        two.letters   to 2,
        three.letters to 3,
        four.letters  to 4,
        five.letters  to 5,
        six.letters   to 6,
        seven.letters to 7,
        eight.letters to 8,
        nine.letters  to 9
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