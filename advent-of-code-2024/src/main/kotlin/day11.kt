import util.sumOfLong

fun day11a(input: List<String>, blinks: Int = 25) =
    input[0].countStonesAfterBlinks(blinks)

fun day11b(input: List<String>) =
    input[0].countStonesAfterBlinks(75)

private fun String.countStonesAfterBlinks(blinks: Int): Long {
    val stones = this.split(" ").map {it.toLong() }

    val cache = mutableMapOf<Pair<Long, Int>, Long>()
    fun countChildStones(nr: Long, blinksRemaining: Int): Long {
        if (blinksRemaining == 0) {
            return 1
        }

        if (nr to blinksRemaining in cache) {
            return cache[nr to blinksRemaining]!!
        }

        val nextStoneNrs =
            if (nr == 0L) {
                listOf(1L)
            } else if (nr.toString().length % 2 == 0) {
                val nr1 = nr.toString().substring(0, nr.toString().length / 2).toLong()
                val nr2 = nr.toString().substring(nr.toString().length / 2).toLong()
                listOf(nr1, nr2)
            } else {
                listOf(nr * 2024L)
            }

        return nextStoneNrs.sumOfLong { nextNr ->
            countChildStones(nextNr, blinksRemaining - 1)
        }.also { cache[nr to blinksRemaining] = it }
    }

    return stones.sumOfLong { countChildStones(it, blinks) }
}