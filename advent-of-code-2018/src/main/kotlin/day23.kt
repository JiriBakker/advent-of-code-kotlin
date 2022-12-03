import util.getBounds
import util.sortMappedByDescending

import Day23.Pos

private object Day23 {
    class Pos(val x: Int, val y: Int, val z: Int) {
        fun distanceTo(other: Pos): Int {
            return Math.abs(other.x - x) +
                Math.abs(other.y - y) +
                Math.abs(other.z - z)
        }
    }
}

private class Nanobot(val pos: Pos, val radius: Int) {
    fun isInRadius(other: Nanobot): Boolean {
        return pos.distanceTo(other.pos) <= radius
    }

    fun isInRadius(pos: Pos): Boolean {
        return this.pos.distanceTo(pos) <= radius
    }
}

private class BoundingBox(val min: Pos, val max: Pos) {
    val width = max.x - min.x + 1
    val height = max.y - min.y + 1
    val depth = max.z - min.z + 1

    val center = Pos(
        min.x + (width / 2),
        min.y + (height / 2),
        min.z + (depth / 2)
    )

    fun split(divisor: Int): Sequence<BoundingBox> {
        return sequence {
            val deltaX = Math.max(width / divisor, 1)
            val deltaY = Math.max(height / divisor, 1)
            val deltaZ = Math.max(depth / divisor, 1)
            for (x in min.x..max.x step deltaX) {
                for (y in min.y..max.y step deltaY) {
                    for (z in min.z..max.z step deltaZ) {
                        yield(
                            BoundingBox(
                                Pos(x, y, z),
                                Pos(
                                    x + deltaX - 1,
                                    y + deltaY - 1,
                                    z + deltaZ - 1
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "$min <> $max ($center)"
    }
}

private fun parse(inputLines: List<String>): List<Nanobot> {
    return inputLines.map {
        val (x, y, z, radius) = it.substring(5).split(",", ">, r=").map(String::toInt)
        Nanobot(Pos(x, y, z), radius)
    }
}

fun day23a(inputLines: List<String>): Int {
    val nanobots = parse(inputLines)

    val nanobotWithLargestRadius = nanobots.maxByOrNull { it.radius }!!

    return nanobots.count { nanobotWithLargestRadius.isInRadius(it) }
}

fun day23b(inputLines: List<String>): Int? {
    val nanobots = parse(inputLines)

    fun countInRadius(box: BoundingBox): Int {
        return nanobots.count { it.isInRadius(box.center) }
    }

    val (minX, maxX) = nanobots.getBounds { it.pos.x }
    val (minY, maxY) = nanobots.getBounds { it.pos.y }
    val (minZ, maxZ) = nanobots.getBounds { it.pos.z }

    var curBoundingBoxes = listOf(
        BoundingBox(
            Pos(minX, minY, minZ),
            Pos(maxX, maxY, maxZ)
        )
    )
    do {
        curBoundingBoxes =
            curBoundingBoxes
                .flatMap { it.split(4).toList() }
                .sortMappedByDescending(::countInRadius)
                .take(4)
    } while (curBoundingBoxes.none { it.width == 1 })

    val maxNrInRange = curBoundingBoxes.map(::countInRadius).maxOrNull()!!
    val origin = Pos(0, 0, 0)

    return curBoundingBoxes
        .filter { countInRadius(it) == maxNrInRange }
        .map { it.center.distanceTo(origin) }
        .minOrNull()
}
