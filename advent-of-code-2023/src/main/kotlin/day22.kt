import util.component6
import util.countDistinct
import kotlin.math.max
import kotlin.math.min

private data class Brick(
    var xTop: Int,
    var yTop: Int,
    var zTop: Int,
    var xBottom: Int,
    var yBottom: Int,
    var zBottom: Int,
) {
    val supportedBy = mutableSetOf<Brick>()
    val supports = mutableSetOf<Brick>()

    fun moveDownUntilBlocked(bricks: List<Brick>) {
        val otherBricks = bricks.filter { it != this }
        while (zBottom > 1) {
            val nextBrick = Brick(xTop, yTop, zTop - 1, xBottom, yBottom, zBottom - 1)
            otherBricks
                .filter { brick -> brick.intersects(nextBrick) }
                .forEach { brick ->
                    this.supportedBy.add(brick)
                    brick.supports.add(this)
                }

            if (this.supportedBy.isNotEmpty()) {
                return
            }

            this.zTop--
            this.zBottom--
        }
    }

    fun intersects(other: Brick): Boolean {
        fun overlaps(start1: Int, end1: Int, start2: Int, end2: Int): Boolean {
            return start1 <= end2 && end1 >= start2
        }

        return overlaps(this.xBottom, this.xTop, other.xBottom, other.xTop) &&
            overlaps(this.yBottom, this.yTop, other.yBottom, other.yTop) &&
            overlaps(this.zBottom, this.zTop, other.zBottom, other.zTop)
    }
}

fun day22a(input: List<String>): Int {
    val bricks = input.map { line ->
        val (x1, y1, z1, x2, y2, z2) = line.split(",", "~").map(String::toInt)
        Brick(max(x1, x2), max(y1, y2), max(z1, z2), min(x1, x2), min(y1, y2), min(z1, z2))
    }

    bricks
        .sortedBy { it.zBottom }
        .forEach { brick ->
            brick.moveDownUntilBlocked(bricks)
        }


    val nrOfSupportingBricks = bricks.filter { it.supportedBy.size == 1 }.map { it.supportedBy.first() }.countDistinct()

    return bricks.size - nrOfSupportingBricks
}

fun day22b(input: List<String>): Int {
    val bricks = input.map { line ->
        val (x1, y1, z1, x2, y2, z2) = line.split(",", "~").map(String::toInt)
        Brick(max(x1, x2), max(y1, y2), max(z1, z2), min(x1, x2), min(y1, y2), min(z1, z2))
    }

    bricks
        .sortedBy { it.zBottom }
        .forEach { brick ->
            brick.moveDownUntilBlocked(bricks)
        }


    return bricks.sumOf { brickToDisintegrate ->
        val potentiallyAffectedBricks = mutableSetOf<Brick>()
        val stableBricks = mutableSetOf<Brick>()

        val toCheck = mutableListOf<Brick>()
        toCheck.addAll(brickToDisintegrate.supports)
        while (toCheck.isNotEmpty()) {
            val brick = toCheck.removeFirst()
            potentiallyAffectedBricks.add(brick)
            toCheck.addAll(brick.supports)
        }

        potentiallyAffectedBricks.forEach { potentiallyAffectedBrick ->
            val bricksBelow = mutableListOf(potentiallyAffectedBrick)

            while (bricksBelow.isNotEmpty()) {
                val brickBelow = bricksBelow.removeFirst()

                if (brickBelow == brickToDisintegrate) continue

                if (brickBelow.supportedBy.all { supportBrick -> supportBrick in stableBricks && supportBrick != brickToDisintegrate }) {
                    stableBricks.add(potentiallyAffectedBrick)
                    stableBricks.add(brickBelow)
                    break
                }

                bricksBelow.addAll(brickBelow.supportedBy)
            }
        }

        potentiallyAffectedBricks.count { potentiallyAffectedBrick ->
            potentiallyAffectedBrick !in stableBricks && potentiallyAffectedBrick != brickToDisintegrate
        }
    }

}