package days

private class Fabric() {
    private val inches: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    fun applyClaim(claim: Claim) {
        for (x in claim.x1 until claim.x2) {
            for (y in claim.y1 until claim.y2) {
                val pos = Pair(x, y)
                val current = inches.getOrDefault(pos, 0)
                inches[pos] = current + 1
            }
        }
    }

    fun countOverlaps(): Int {
        return inches.count { it.value > 1 }
    }
}

private data class Claim private constructor(val id: Int, val x1: Int, val y1: Int, private val width: Int, private val height: Int) {
    val x2 = x1 + width
    val y2 = y1 + height

    companion object {
        fun parse(claimLine: String): Claim {
            val atIndex = claimLine.indexOf('@')
            val colonIndex = claimLine.indexOf(':')

            val id = claimLine.substring(1, atIndex).trim().toInt()
            val (x, y) = claimLine.substring(atIndex + 1, colonIndex).split(',').map { it.trim().toInt() }
            val (width, height) = claimLine.substring(colonIndex + 1).split('x').map { it.trim().toInt() }
            return Claim(id, x, y, width, height)
        }
    }

    fun overlapsWith(other: Claim): Boolean {
        val xOverlap = Math.max(0, Math.min(this.x2, other.x2) - Math.max(this.x1, other.x1))
        val yOverlap = Math.max(0, Math.min(this.y2, other.y2) - Math.max(this.y1, other.y1))
        return xOverlap != 0 && yOverlap != 0
    }
}

fun <T> List<T>.combinationPairs(): Sequence<Pair<T,T>> {
    val list = this
    return sequence {
        for (i1 in 0 until list.size) {
            for (i2 in (i1 + 1) until list.size) {
                yield(Pair(list[i1], list[i2]))
            }
        }
    }
}

fun day03a(claimLines: List<String>): Int {
    val claims = claimLines.map { Claim.parse(it) }
    val fabric = Fabric()

    claims.forEach { fabric.applyClaim(it) }

    return fabric.countOverlaps()
}

fun day03b(claimLines: List<String>): Int? {
    val claims = claimLines.map { Claim.parse(it) }

    val idsWithoutOverlap: MutableSet<Int> = claims.map { it.id }.toMutableSet()

    claims.combinationPairs().forEach {
        if (it.first.overlapsWith(it.second)) {
            idsWithoutOverlap.remove(it.first.id)
            idsWithoutOverlap.remove(it.second.id)
        }
    }

    return idsWithoutOverlap.singleOrNull()
}
