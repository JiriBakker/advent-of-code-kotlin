package v2021

import util.countDistinct
import util.flip
import util.getCombinationPairs
import util.mapCombinationPairs
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

private fun Vec3.rotX()    = Vec3(x, -z, y)
private fun Vec3.rotMinX() = Vec3(x, z, -y)
private fun Vec3.rotY()    = Vec3(-z, y, x)
private fun Vec3.rotMinY() = Vec3(z, y, -x)
private fun Vec3.rotZ()    = Vec3(y, -x, z)
private fun Vec3.rotMinZ() = Vec3(-y, x, z)

private data class BeaconDelta(val pos1: Vec3, val pos2: Vec3, val rotateFunc: (Vec3) -> Vec3, val invRotateFunc: (Vec3) -> Vec3)

private data class ScannerOverlap(val scanner1: Scanner, val scanner2: Scanner, val overlap: Pair<BeaconDelta, BeaconDelta>)

private data class ScannerProperties(val offset: Vec3, val rotateFunc: RotateFunc)

private typealias RotateFunc = (Vec3) -> Vec3

private val rotateFuncsAndInverses =
    listOf<Pair<RotateFunc, RotateFunc>>(
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

private val beaconDeltaComparator: Comparator<BeaconDelta> =
    compareBy({ it.pos2.x - it.pos1.x }, { it.pos2.y - it.pos1.y }, { it.pos2.z - it.pos1.z })

private class Scanner(val id: Int, val beacons: List<Vec3>) {
    private val beaconDeltaSets: List<List<BeaconDelta>>

    init {
        val beaconDistances = beacons.getCombinationPairs().toList()

        beaconDeltaSets =
            rotateFuncsAndInverses
                .map { (rotateFunc, invRotateFunc) ->
                    beaconDistances
                        .map { (beacon1, beacon2) ->
                            BeaconDelta(
                                rotateFunc(beacon1),
                                rotateFunc(beacon2),
                                rotateFunc,
                                invRotateFunc
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
        .mapIndexed { id, lines ->
            val beacons =
                lines
                    .drop(1)
                    .map { line ->
                        val (x, y, z) = line.split(",").map { it.toInt() }
                        Vec3(x, y, z)
                    }
            Scanner(id, beacons)
        }


private fun List<Scanner>.findScannerProperties(): Map<Int, ScannerProperties> {
    val matches =
        this.mapCombinationPairs { scanner1, scanner2 ->
            scanner1.findOverlap(scanner2)
                ?.let { overlaps ->
                    listOf(
                        ScannerOverlap(scanner1, scanner2, overlaps.first()),
                        ScannerOverlap(scanner2, scanner1, overlaps.first().flip())
                    )
                }
        }.filterNotNull().flatten()

    val scannerProperties = mutableMapOf<Int, ScannerProperties>(
        0 to ScannerProperties(Vec3(0, 0, 0), { it })
    )

    matches
        .filter { it.scanner1.id == 0 }
        .forEach { scannerOverlap ->
            val (delta1, delta2) = scannerOverlap.overlap
            val inverseMapFunc = delta1.invRotateFunc
            val mapFunc = delta2.rotateFunc
            val pos1 = delta1.invRotateFunc(delta1.pos1)
            val pos2 = delta1.invRotateFunc(delta2.pos1)
            scannerProperties[scannerOverlap.scanner2.id] = ScannerProperties(pos1 - pos2, { inverseMapFunc(mapFunc(it)) })
        }

    fun hasScannerProperties(id: Int) = scannerProperties.keys.contains(id)

    while (scannerProperties.size < this.size) {
        matches
            .filter { it.scanner1.id != 0 }
            .forEach { scannerOverlap ->
                if (hasScannerProperties(scannerOverlap.scanner1.id) && !hasScannerProperties(scannerOverlap.scanner2.id)) {
                    val (initialOffset, initialRotate) = scannerProperties[scannerOverlap.scanner1.id]!!

                    val (delta1, delta2) = scannerOverlap.overlap
                    val inverseRotate = delta1.invRotateFunc
                    val rotate = delta2.rotateFunc
                    val pos1 = initialRotate(inverseRotate(delta1.pos1))
                    val pos2 = initialRotate(inverseRotate(delta2.pos1))

                    val scannerOffset = pos1 - pos2 + initialOffset
                    val scannerRotateFunc = { it: Vec3 -> initialRotate(inverseRotate(rotate(it))) }

                    scannerProperties[scannerOverlap.scanner2.id] = ScannerProperties(scannerOffset, scannerRotateFunc)
                }
            }
    }

    return scannerProperties
}

private fun Scanner.rotateBeacons(rotateFunc: RotateFunc) =
    beacons.map(rotateFunc)

private fun List<Vec3>.translateAll(offset: Vec3) =
    map { it + offset }

fun day19a(input: List<String>): Int {
    val scanners = input.parseScanners()

    val scannerProperties = scanners.findScannerProperties()

    return scanners
        .flatMap { scanner ->
            val (offset, rotateFunc) = scannerProperties[scanner.id]!!
            scanner.rotateBeacons(rotateFunc).translateAll(offset)
        }
        .countDistinct()
}

fun day19b(input: List<String>): Int {
    val scanners = input.parseScanners()

    val scannerProperties = scanners.findScannerProperties()

    return (scannerProperties.values).maxOf { (offset1, _) ->
        (scannerProperties.values).maxOf { (offset2, _) ->
            offset1.manhattanDistanceTo(offset2)
        }
    }
}