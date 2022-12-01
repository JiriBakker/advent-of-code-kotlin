package v2016

import java.math.BigInteger
import java.util.PriorityQueue

data class Pos(val x: Int, val y: Int)

private fun exploreLocations(favNr: Int, maxSteps: Int): Sequence<Pair<Pos, Int>> {
    fun isPosOpen(pos: Pos): Boolean {
        val (x, y) = pos
        return x >= 0
            && y >= 0
            && BigInteger.valueOf((x.toLong() * x + 3 * x + 2 * x * y + y + y * y) + favNr).bitCount() % 2 == 0
    }

    return sequence {
        val visited = mutableSetOf<Pos>()
        val toVisit = PriorityQueue<Pair<Pos, Int>> { a, b -> a.second.compareTo(b.second) }
        toVisit.add(Pos(1, 1) to 0)

        while (toVisit.isNotEmpty()) {
            val (curPos, curSteps) = toVisit.poll()

            if (!visited.add(curPos)) {
                continue
            }

            yield(curPos to curSteps)
            if (curSteps >= maxSteps) {
                continue
            }

            listOf(
                Pos(curPos.x - 1, curPos.y),
                Pos(curPos.x + 1, curPos.y),
                Pos(curPos.x, curPos.y - 1),
                Pos(curPos.x, curPos.y + 1)
            )
                .filter(::isPosOpen)
                .forEach { toVisit.add(it to curSteps + 1) }
        }
    }
}

fun day13a(input: String, targetX: Int = 31, targetY: Int = 39): Int {
    return exploreLocations(input.toInt(), Int.MAX_VALUE)
        .first { (pos, _) -> pos.x == targetX && pos.y == targetY }
        .second
}


fun day13b(input: String, maxSteps: Int = 50): Int {
    return exploreLocations(input.toInt(), maxSteps)
        .toList()
        .size
}
