private data class Cup(val nr: Int) {
    lateinit var next: Cup
    lateinit var below: Cup
}

private fun Cup.find(nr: Int): Cup {
    var cup = this
    while (cup.nr != nr) {
        cup = cup.next
    }
    return cup
}

private fun Cup.findLast(): Cup {
    var cup = this
    while (cup.next.nr != this.nr) {
        cup = cup.next
    }
    return cup
}

private fun Cup.collectAll(): List<Cup> {
    val cups = mutableListOf<Cup>()
    var cup = this
    do {
        cups.add(cup)
        cup = cup.next
    }
    while (cup != this)
    return cups
}

private fun parseCups(input: String): Cup {
    val cups = input.map { Cup(it.toString().toInt()) }

    var prevCup = cups.first()
    cups.last().next = prevCup
    cups.drop(1).forEach { cup ->
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

    curCup.next = nextCup3.next
    nextCup3.next = destination.next
    destination.next = nextCup

    return curCup.next
}

fun day23a(input: List<String>, nrOfMoves: Int = 100): Long {
    var curCup = parseCups(input.first())

    repeat(nrOfMoves) {
        curCup = move(curCup)
    }

    return curCup
        .find(1)
        .collectAll()
        .drop(1)
        .joinToString("") { it.nr.toString() }
        .toLong()
}

fun day23b(input: List<String>, nrOfMoves: Int = 10_000_000): Long {
    val startCup = parseCups(input.first())

    // TODO non-hardcoded values?
    val cup1 = startCup.find(1)
    var below = startCup.find(9)

    var curCup = startCup.findLast()
    (10 .. 1_000_000).forEach { nr ->
        val cup = Cup(nr)
        cup.below = below
        curCup.next = cup
        below = cup
        curCup = cup
    }

    curCup.next = startCup
    cup1.below = curCup

    curCup = startCup

    repeat(nrOfMoves) {
        curCup = move(curCup)
    }

    return cup1.next.nr.toLong() * cup1.next.next.nr.toLong()
}

