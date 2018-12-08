package days.day03

import forEachCombinationPair

private class Fabric {
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

private class Claim private constructor(val id: Int, val x1: Int, val y1: Int, width: Int, height: Int) {
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

fun day03a(claimLines: List<String>): Int {
    val claims = claimLines.map { Claim.parse(it) }
    val fabric = Fabric()

    claims.forEach { fabric.applyClaim(it) }

    return fabric.countOverlaps()
}

fun day03b(claimLines: List<String>): Int? {
    val claims = claimLines.map { Claim.parse(it) }

    val idsWithoutOverlap: MutableSet<Int> = claims.map { it.id }.toMutableSet()

    claims.forEachCombinationPair {
        if (it.first.overlapsWith(it.second)) {
            idsWithoutOverlap.remove(it.first.id)
            idsWithoutOverlap.remove(it.second.id)
        }
    }

    return idsWithoutOverlap.singleOrNull()
}
