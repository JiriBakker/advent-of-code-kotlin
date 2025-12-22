import util.sumOfLong

fun day09a(input: List<String>) =
    input.sumOfLong { line ->
        var nrs = line.split(" ").map(String::toLong)

        var nextNumber = 0L
        while (!nrs.all { it == 0L }) {
            nextNumber += nrs.last()
            nrs = findDiffs(nrs)
        }

        nextNumber
    }

fun day09b(input: List<String>) =
    input.sumOfLong { line ->
        var nrs = line.split(" ").map(String::toLong)

        val diffs = mutableListOf<Long>()
        while (!nrs.all { it == 0L }) {
            diffs.add(nrs.first())
            nrs = findDiffs(nrs)
        }

        diffs.reversed().reduce { prev, cur -> cur - prev }
    }

private fun findDiffs(nrs: List<Long>) =
    nrs.zipWithNext { nr1, nr2 -> nr2 - nr1 }