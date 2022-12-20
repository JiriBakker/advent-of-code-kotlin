import Day20.computeGrooveCoordinates
import Day20.moveNrs
import Day20.parseNumbers
import kotlin.math.abs

fun day20a(input: List<String>): Long {
    val nrs = input.parseNumbers()
    nrs.moveNrs()
    return nrs.computeGrooveCoordinates()
}

fun day20b(input: List<String>): Long {
    val nrs = input.parseNumbers(multiplier = 811589153L)
    repeat(10) { nrs.moveNrs() }
    return nrs.computeGrooveCoordinates()
}

object Day20 {

    fun List<String>.parseNumbers(multiplier: Long = 1L): List<Nr> {
        var prev: Nr? = null
        val nrs = this.map { line ->
            val nr = Nr(line.toLong() * multiplier, prev)
            prev?.apply { next = nr }
            prev = nr
            nr
        }

        val firstNr = nrs.first()
        firstNr.prev = prev
        prev!!.next = firstNr

        return nrs
    }

    fun List<Nr>.moveNrs() {
        val nrs = this

        fun Nr.move() {
            val positions = (abs(nr) % (nrs.size - 1)).toInt()
            if (positions == 0) {
                return
            }

            val newPrev =
                if (nr > 0) {
                    var next = this.next!!
                    repeat(positions) { next = next.next!! }
                    next.prev!!
                } else {
                    var prev = this.prev!!
                    repeat(positions) { prev = prev.prev!! }
                    prev
                }

            val newNext = newPrev.next!!

            this.extract()
            this.insertBetween(newPrev, newNext)
        }

        nrs.forEach { nr ->
            nr.move()
        }
    }

    fun List<Nr>.computeGrooveCoordinates(): Long {
        var groveCoordinates = 0L

        var cur = this.first { it.nr == 0L }
        repeat(3) {
            repeat(1000 % this.size) {
                cur = cur.next!!
            }
            groveCoordinates += cur.nr
        }

        return groveCoordinates
    }

    class Nr(val nr: Long, var prev: Nr?) {
        var next: Nr? = null

        fun insertBetween(newPrev: Nr, newNext: Nr) {
            newPrev.next = this
            newNext.prev = this
            this.prev = newPrev
            this.next = newNext
        }

        fun extract() {
            this.next!!.prev = this.prev!!
            this.prev!!.next = this.next!!
        }
    }
}