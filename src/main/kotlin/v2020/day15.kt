package v2020

private fun parseNrs(input: List<String>): List<Int> =
    input.first().split(",").map(String::toInt)

private fun findNthNumber(nrs: List<Int>, n: Int): Int {
    val spokenLast = mutableMapOf<Int, Int>()

    nrs.forEachIndexed { turn, nr -> spokenLast[nr] = turn + 1 }

    var nextNr = 0
    var turn = nrs.size + 1

    while (turn < n) {
        val lastTurn = spokenLast[nextNr]
        spokenLast[nextNr] = turn

        nextNr = if (lastTurn != null) {
            turn - lastTurn
        } else {
            0
        }

        turn++
    }

    return nextNr
}

fun day15a(input: List<String>): Int {
    val nrs = parseNrs(input)
    return findNthNumber(nrs, 2020)
}

fun day15b(input: List<String>): Int {
    val nrs = parseNrs(input)
    return findNthNumber(nrs, 30000000)
}
