package v2021

import util.getCombinationPairs
import util.splitByDoubleNewLine
import kotlin.math.abs

private data class Vec3(val x: Int, val y: Int, val z: Int) {
    operator fun minus(other: Vec3) =
        Vec3(x - other.x, y - other.y, z - other.z)

    operator fun plus(other: Vec3) =
        Vec3(x + other.x, y + other.y, z + other.z)

    fun manhattanDistanceTo(other: Vec3) =
        abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
}

private data class BeaconDelta(val pos1: Vec3, val pos2: Vec3, val mapFunc: (Vec3) -> Vec3, val inverseMapFunc: (Vec3) -> Vec3)

private val beaconDeltaComparator: Comparator<BeaconDelta> = compareBy({ it.pos2.x - it.pos1.x }, { it.pos2.y - it.pos1.y }, { it.pos2.z - it.pos1.z })
private val vec3Comparator: Comparator<Vec3> = compareBy({ it.x }, { it.y }, { it.z })

private fun Vec3.rotX()    = Vec3(x, -z, y)
private fun Vec3.rotMinX() = Vec3(x, z, -y)
private fun Vec3.rotY()    = Vec3(-z, y, x)
private fun Vec3.rotMinY() = Vec3(z, y, -x)
private fun Vec3.rotZ()    = Vec3(y, -x, z)
private fun Vec3.rotMinZ() = Vec3(-y, x, z)

private typealias RotateFunc = (Vec3) -> Vec3

private class Scanner(val beacons: List<Vec3>) {
    val beaconDeltaSets: List<List<BeaconDelta>>

    init {
        val beaconDistances = beacons.getCombinationPairs().toList()

        beaconDeltaSets = listOf<Pair<RotateFunc, RotateFunc>>(
            { delta: Vec3 -> delta } to { delta -> delta },
            { delta: Vec3 -> delta.rotX() } to { delta -> delta.rotMinX() },
            { delta: Vec3 -> delta.rotY() } to { delta -> delta.rotMinY() },
            { delta: Vec3 -> delta.rotZ() } to { delta -> delta.rotMinZ() },
            { delta: Vec3 -> delta.rotX().rotX() } to { delta -> delta.rotMinX().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotY() } to { delta -> delta.rotMinY().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotZ() } to { delta -> delta.rotMinZ().rotMinX() },
            { delta: Vec3 -> delta.rotY().rotX() } to { delta -> delta.rotMinX().rotMinY() },
            { delta: Vec3 -> delta.rotY().rotY() } to { delta -> delta.rotMinY().rotMinY() },
            { delta: Vec3 -> delta.rotZ().rotY() } to { delta -> delta.rotMinY().rotMinZ() },
            { delta: Vec3 -> delta.rotZ().rotZ() } to { delta -> delta.rotMinZ().rotMinZ() },
            { delta: Vec3 -> delta.rotX().rotX().rotX() } to { delta -> delta.rotMinX().rotMinX().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotX().rotY() } to { delta -> delta.rotMinY().rotMinX().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotX().rotZ() } to { delta -> delta.rotMinZ().rotMinX().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotY().rotX() } to { delta -> delta.rotMinX().rotMinY().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotY().rotY() } to { delta -> delta.rotMinY().rotMinY().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotZ().rotZ() } to { delta -> delta.rotMinZ().rotMinZ().rotMinX() },
            { delta: Vec3 -> delta.rotY().rotX().rotX() } to { delta -> delta.rotMinX().rotMinX().rotMinY() },
            { delta: Vec3 -> delta.rotY().rotY().rotY() } to { delta -> delta.rotMinY().rotMinY().rotMinY() },
            { delta: Vec3 -> delta.rotZ().rotZ().rotZ() } to { delta -> delta.rotMinZ().rotMinZ().rotMinZ() },
            { delta: Vec3 -> delta.rotX().rotX().rotX().rotY() } to { delta -> delta.rotMinY().rotMinX().rotMinX().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotX().rotY().rotX() } to { delta -> delta.rotMinX().rotMinY().rotMinX().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotY().rotX().rotX() } to { delta -> delta.rotMinX().rotMinX().rotMinY().rotMinX() },
            { delta: Vec3 -> delta.rotX().rotY().rotY().rotY() } to { delta -> delta.rotMinY().rotMinY().rotMinY().rotMinX() }
        )
            .map { (rotationFunc, inverseRotationFunc) ->
                beaconDistances
                    .map { (beacon1, beacon2) ->
                        BeaconDelta(
                            rotationFunc(beacon1),
                            rotationFunc(beacon2),
                            rotationFunc,
                            inverseRotationFunc
                        )
                    }
                    .sortedWith(beaconDeltaComparator)
            }

    }

    fun findOverlap(other: Scanner): List<Pair<BeaconDelta, BeaconDelta>>? {
        this.beaconDeltaSets.forEach { deltas1 ->
            other.beaconDeltaSets.forEach { deltas2 ->
                val matches = mutableListOf<Pair<BeaconDelta, BeaconDelta>>()
                var index1 = 0
                var index2 = 0

                while (index1 < deltas1.size && index2 < deltas2.size) {
                    val compareResult = beaconDeltaComparator.compare(deltas1[index1], deltas2[index2])
                    when {
                        compareResult < 0 -> index1++
                        compareResult > 0 -> index2++
                        else -> {
                            matches.add(deltas1[index1] to deltas2[index2])
                            index1++
                            index2++
                        }
                    }

                    if (matches.size == 12) {
                        return matches
                    }
                }
            }
        }

        return null
    }
}

private fun List<String>.parseScanners() =
    splitByDoubleNewLine()
        .map { lines ->
            val beacons =
                lines
                    .drop(1)
                    .map { line ->
                        val (x, y, z) = line.split(",").map { it.toInt() }
                        Vec3(x, y, z)
                    }
            Scanner(beacons)
        }

private fun <T,U> Pair<T,U>.flip() = second to first

private fun List<Scanner>.findScannerPositions(): Map<Int, Pair<Vec3, RotateFunc>> {
    val scanners = this

    val matches =
        scanners.withIndex().flatMap { (index1, scanner1) ->
            (index1 + 1 until scanners.size).flatMap { index2 ->
                val scanner2 = scanners[index2]

                scanner1.findOverlap(scanner2)
                    ?.let { overlaps ->
                        val firstOverlap = overlaps.first()
                        listOf(
                            (index1 to index2) to firstOverlap,
                            (index2 to index1) to firstOverlap.flip()
                        )
                    } ?: emptyList()

            }
        }

    val scannerPositions = mutableMapOf<Int, Pair<Vec3, RotateFunc>>(
        0 to (Vec3(0, 0, 0) to { it })
    )

    matches
        .filter { it.first.first == 0 }
        .forEach { overlap ->
            val (delta1, delta2) = overlap.second
            val inverseMapFunc = delta1.inverseMapFunc
            val mapFunc = delta2.mapFunc
            val pos1 = delta1.inverseMapFunc(delta1.pos1)
            val pos2 = delta1.inverseMapFunc(delta2.pos1)
            scannerPositions[overlap.first.second] = (pos1 - pos2) to { inverseMapFunc(mapFunc(it)) }
        }

    while (scannerPositions.size < scanners.size) {
        matches
            .filter { it.first.first != 0 }
            .forEach { overlap ->
                if (scannerPositions.keys.contains(overlap.first.first) && !scannerPositions.keys.contains(overlap.first.second)) {
                    val (initialOffset, initialRotate) = scannerPositions[overlap.first.first]!!

                    val (delta1, delta2) = overlap.second
                    val inverseRotate = delta1.inverseMapFunc
                    val rotate = delta2.mapFunc
                    val pos1 = initialRotate(inverseRotate(delta1.pos1))
                    val pos2 = initialRotate(inverseRotate(delta2.pos1))

                    val scannerOffset = pos1 - pos2 + initialOffset
                    val scannerRotation = { it: Vec3 -> initialRotate(inverseRotate(rotate(it))) }

                    scannerPositions[overlap.first.second] = scannerOffset to scannerRotation
                }
            }
    }

    return scannerPositions
}

fun day19a(input: List<String>): Int {
    val scanners = input.parseScanners()

    val translations = scanners.findScannerPositions()

    val uniqueBeacons =
        scanners.flatMapIndexed { index, scanner ->
            val (delta, mapFunc) = translations[index]!!
            val mappedBeacons = scanner.beacons.map { beacon ->
                delta + mapFunc(beacon)
            }
            mappedBeacons
        }.toSet().sortedWith(vec3Comparator)

    return uniqueBeacons.count()
}

fun day19b(input: List<String>): Int {
    val scanners = input.parseScanners()

    val scannerPositions = scanners.findScannerPositions()

    return (scannerPositions.values).maxOf { (value1, _) ->
        (scannerPositions.values).maxOf { (value2, _) ->
            value1.manhattanDistanceTo(value2)
        }
    }
}