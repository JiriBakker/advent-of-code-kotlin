private class Elf(val nr: Int) {
    var prev: Elf? = null
    var next: Elf? = null

    fun removeSelf() {
        next!!.prev = prev
        prev!!.next = next
    }
}

private fun generateElfCircle(nrOfElves: Int): Elf {
    val firstElf = Elf(1)
    var prevElf = firstElf
    (2..nrOfElves).forEach { nr ->
        val elf = Elf(nr)
        elf.prev = prevElf
        prevElf.next = elf
        prevElf = elf
    }
    firstElf.prev = prevElf
    prevElf.next = firstElf
    return firstElf
}

fun day19a(input: String): Int {
    val nrOfElves = input.toInt()

    var curElf = generateElfCircle(nrOfElves)
    while (curElf.next != curElf) {
        curElf = curElf.next!!
        curElf.removeSelf()
        curElf = curElf.next!!
    }
    return curElf.nr
}

fun day19b(input: String): Int {
    var elvesRemaining = input.toInt()

    var curElf = generateElfCircle(elvesRemaining)

    var oppositeElf = curElf
    repeat(elvesRemaining / 2) { oppositeElf = oppositeElf.next!! }

    while (oppositeElf.next != curElf) {
        oppositeElf.removeSelf()
        elvesRemaining--

        curElf = curElf.next!!
        oppositeElf = oppositeElf.next!!
        if (elvesRemaining % 2 == 0) {
            oppositeElf = oppositeElf.next!!
        }
    }

    return curElf.nr
}