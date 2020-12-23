package v2020.days.day23

private data class Cup(val nr: Int) {
    lateinit var prev: Cup
    lateinit var next: Cup
    lateinit var below: Cup
}

private fun parseCups(input: String): Cup {
    val cups = input.map { Cup(it.toString().toInt()) }

    var prevCup = cups.first()
    prevCup.prev = cups.last()
    cups.last().next = prevCup
    cups.drop(1).forEach { cup ->
        cup.prev = prevCup
        prevCup.next = cup
        prevCup = cup
    }

    val orderedCups = cups.associateBy { it.nr }.toSortedMap()

    var prevOrderedCup = orderedCups.values.last()
    orderedCups.values.forEach { cup ->
        cup.below = prevOrderedCup
        prevOrderedCup = cup
    }

    return cups.first()
}

private fun move(curCup: Cup): Cup {
    val nextCup = curCup.next
    val nextCup2 = nextCup.next
    val nextCup3 = nextCup2.next

    var destination = curCup.below
    while (destination == nextCup || destination == nextCup2 || destination == nextCup3) {
        destination = destination.below
    }

    destination.next.prev = nextCup3
    curCup.next = nextCup3.next
    curCup.next.prev = curCup
    nextCup3.next = destination.next
    destination.next = nextCup
    nextCup.prev = destination

    return curCup.next
}

fun day23a(input: List<String>, nrOfMoves: Int = 100): Long {
    var curCup = parseCups(input.first())

    repeat(nrOfMoves) {
        curCup = move(curCup)
    }

    fun findCup(nr: Int): Cup {
        var cup = curCup
        while (cup.nr != nr) {
            cup = cup.next
        }
        return cup
    }

    val cup1 = findCup(1)
    curCup = cup1.next
    var result = ""
    while (curCup != cup1) {
        result = "$result${curCup.nr}"
        curCup = curCup.next
    }

    return result.toLong()
}

fun day23b(input: List<String>, nrOfMoves: Int = 10_000_000): Long {
    val startCup = parseCups(input.first())

    fun findCup(nr: Int): Cup {
        var cup = startCup
        while (cup.nr != nr) {
            cup = cup.next
        }
        return cup
    }

    // TODO non-hardcoded values?
    val cup1 = findCup(1)
    var below = findCup(9)

    var curCup = startCup.prev
    (10 .. 1_000_000).forEach { nr ->
        val cup = Cup(nr)
        cup.prev = curCup
        cup.below = below
        curCup.next = cup
        below = cup
        curCup = cup
    }

    startCup.prev = curCup
    curCup.next = startCup
    cup1.below = curCup

    curCup = startCup

    repeat(nrOfMoves + 10) {
        curCup = move(curCup)
    }

    return cup1.next.nr.toLong() * cup1.next.next.nr.toLong()
}

