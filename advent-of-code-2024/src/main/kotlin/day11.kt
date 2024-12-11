import util.sumOfLong

fun day11a(input: List<String>, blinks: Int = 25) =
    input[0].countStonesAfterBlinks(blinks)

fun day11b(input: List<String>) =
    input[0].countStonesAfterBlinks(75)

private fun String.countStonesAfterBlinks(blinks: Int): Long {
    val stones = this.split(" ")

    val cache = mutableMapOf<Pair<String, Int>, Long>()
    fun countChildStones(nr: String, blinksRemaining: Int): Long {
        if (blinksRemaining == 0) {
            return 1
        }

        if (nr to blinksRemaining in cache) {
            return cache[nr to blinksRemaining]!!
        }

        val nextStoneNrs =
            if (nr.toLong() == 0L) {
                listOf("1")
            } else if (nr.length % 2 == 0) {
                val nr1 = nr.substring(0, nr.length / 2)
                val nr2 = nr.substring(nr.length / 2).toLong().toString()
                listOf(nr1, nr2)
            } else {
                listOf((nr.toLong() * 2024L).toString())
            }

        return nextStoneNrs.sumOfLong { nextNr ->
            countChildStones(nextNr, blinksRemaining - 1)
        }.also { cache[nr to blinksRemaining] = it }
    }

    return stones.sumOfLong { countChildStones(it, blinks) }
}