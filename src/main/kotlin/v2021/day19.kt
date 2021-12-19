package v2021

import util.DoNotAutoExecute
import util.splitByDoubleNewLine
import kotlin.math.abs

private data class Beacon(val x: Int, val y: Int, val z: Int)

private data class Vec3(val x: Int, val y: Int, val z: Int) {
    operator fun minus(other: Vec3) =
        Vec3(x - other.x, y - other.y, z - other.z)

    operator fun plus(other: Vec3) =
        Vec3(x + other.x, y + other.y, z + other.z)

    fun manhattanDistanceTo(other: Vec3) =
        abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
}

private data class Quad<T1,T2,T3,T4>(val first: T1, val second: T2, val third: T3, val fourth: T4)

private data class Delta(val delta: Vec3, val pos1: Vec3, val pos2: Vec3, val mapFunc: (Vec3) -> Vec3, val inverseMapFunc: (Vec3) -> Vec3)

private val deltaComparator: Comparator<Delta> = compareBy({ it.delta.x }, { it.delta.y }, { it.delta.z })
private val vec3Comparator: Comparator<Vec3> = compareBy({ it.x }, { it.y }, { it.z })


// 90 degrees CW about x-axis: (x, y, z) -> (x, -z, y)
// 90 degrees CCW about x-axis: (x, y, z) -> (x, z, -y)
//
// 90 degrees CW about y-axis: (x, y, z) -> (-z, y, x)
// 90 degrees CCW about y-axis: (x, y, z) -> (z, y, -x)
//
// 90 degrees CW about z-axis: (x, y, z) -> (y, -x, z)
// 90 degrees CCW about z-axis: (x, y, z) -> (-y, x, z)

private fun Vec3.rotX()    = Vec3(x, -z, y)
private fun Vec3.rotMinX() = Vec3(x, z, -y)
private fun Vec3.rotY()    = Vec3(-z, y, x)
private fun Vec3.rotMinY() = Vec3(z, y, -x)
private fun Vec3.rotZ()    = Vec3(y, -x, z)
private fun Vec3.rotMinZ() = Vec3(-y, x, z)

private class Scanner(val beacons: List<Vec3>) {

    val deltaSets: List<List<Delta>>

    init {
        val baseDeltas =
            beacons.flatMapIndexed { i, beacon ->
                val (x1, y1, z1) = beacon
                (i + 1 until beacons.size).map { i2 ->
                    val beacon2 = beacons[i2]
                    val (x2, y2, z2) = beacon2
                    Triple(Vec3(x2 - x1, y2 - y1, z2 - z1), beacon, beacon2)
                }
            }

        deltaSets = listOf<Pair<(Vec3) -> Vec3, (Vec3) -> Vec3>>(
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
            .map { (mapFunc, inverseMapFunc) ->
                baseDeltas
                    .map { Delta(mapFunc(it.first), mapFunc(it.second), mapFunc(it.third), mapFunc, inverseMapFunc) }
                    .sortedWith(deltaComparator)
            }

    }

    fun findOverlap(other: Scanner): List<Pair<Delta, Delta>>? {
        this.deltaSets.forEach { deltas1 ->
            other.deltaSets.forEach { deltas2 ->
                val matches = mutableListOf<Pair<Delta, Delta>>()
                var index1 = 0
                var index2 = 0

                while (index1 < deltas1.size && index2 < deltas2.size) {
                    val compareResult = deltaComparator.compare(deltas1[index1], deltas2[index2])
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

fun day19a(input: List<String>): Int {
    val scanners = input.parseScanners()

    val overlaps =
        scanners.withIndex().flatMap { (index1, scanner1) ->
            (index1 + 1 until scanners.size).mapNotNull { index2 ->
                val scanner2 = scanners[index2]
                scanner1.findOverlap(scanner2)
                    ?.let { overlap ->
                        (index1 to index2) to
                            overlap
                                .first()
                    }
            }
        }

    val translations = mutableMapOf<Int, Pair<Vec3, (Vec3) -> Vec3>>(
        0 to (Vec3(0, 0, 0) to { it })
    )

    overlaps.filter { it.first.first == 0 }.forEach { overlap ->
        val (delta1, delta2) = overlap.second
        val inverseMapFunc = delta1.inverseMapFunc
        val mapFunc = delta2.mapFunc
        val pos1 = delta1.inverseMapFunc(delta1.pos1)
        val pos2 = delta1.inverseMapFunc(delta2.pos1)
        translations[overlap.first.second] = (pos1 - pos2) to { inverseMapFunc(mapFunc(it)) }
    }

    while (translations.size < scanners.size) {
        overlaps.filter { it.first.first != 0 }
            .forEach { overlap ->


                if (translations.keys.contains(overlap.first.first) && !translations.keys.contains(overlap.first.second)) {
                    val (t0Delta, t0MapFunc) = translations[overlap.first.first]!!

                    val (delta1, delta2) = overlap.second
                    val inverseMapFunc = delta1.inverseMapFunc
                    val mapFunc = delta2.mapFunc
                    val pos1 = t0MapFunc(delta1.inverseMapFunc(delta1.pos1))
                    val pos2 = t0MapFunc(delta1.inverseMapFunc(delta2.pos1))

                    val newMapFunc = { it: Vec3 -> t0MapFunc(inverseMapFunc(mapFunc(it))) }

                    val vec2 = pos1 - pos2

                    translations[overlap.first.second] = vec2 + t0Delta to newMapFunc
                } else if (!translations.keys.contains(overlap.first.first) && translations.keys.contains(overlap.first.second)) {
                    val (t0Delta, t0MapFunc) = translations[overlap.first.second]!!

                    val (delta2, delta1) = overlap.second
                    val inverseMapFunc = delta1.inverseMapFunc
                    val mapFunc = delta2.mapFunc
                    val pos1 = t0MapFunc(delta1.inverseMapFunc(delta1.pos1))
                    val pos2 = t0MapFunc(delta1.inverseMapFunc(delta2.pos1))

                    val newMapFunc = { it: Vec3 -> t0MapFunc(inverseMapFunc(mapFunc(it))) }

                    val vec2 = pos1 - pos2

                    translations[overlap.first.first] = vec2 + t0Delta to newMapFunc
                }
            }
    }


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

@DoNotAutoExecute
fun day19b(input: List<String>): Int {
    val scanners = input.parseScanners()

    val overlaps =
        scanners.withIndex().flatMap { (index1, scanner1) ->
            (index1 + 1 until scanners.size).mapNotNull { index2 ->
                val scanner2 = scanners[index2]
                scanner1.findOverlap(scanner2)
                    ?.let { overlap ->
                        (index1 to index2) to
                            overlap
                                .first()
                    }
            }
        }

    val translations = mutableMapOf<Int, Pair<Vec3, (Vec3) -> Vec3>>(
        0 to (Vec3(0, 0, 0) to { it })
    )

    overlaps.filter { it.first.first == 0 }.forEach { overlap ->
        val (delta1, delta2) = overlap.second
        val inverseMapFunc = delta1.inverseMapFunc
        val mapFunc = delta2.mapFunc
        val pos1 = delta1.inverseMapFunc(delta1.pos1)
        val pos2 = delta1.inverseMapFunc(delta2.pos1)
        translations[overlap.first.second] = (pos1 - pos2) to { inverseMapFunc(mapFunc(it)) }
    }

    while (translations.size < scanners.size) {
        overlaps.filter { it.first.first != 0 }
            .forEach { overlap ->
                if (translations.keys.contains(overlap.first.first) && !translations.keys.contains(overlap.first.second)) {
                    val (t0Delta, t0MapFunc) = translations[overlap.first.first]!!

                    val (delta1, delta2) = overlap.second
                    val inverseMapFunc = delta1.inverseMapFunc
                    val mapFunc = delta2.mapFunc
                    val pos1 = t0MapFunc(delta1.inverseMapFunc(delta1.pos1))
                    val pos2 = t0MapFunc(delta1.inverseMapFunc(delta2.pos1))

                    val newMapFunc = { it: Vec3 -> t0MapFunc(inverseMapFunc(mapFunc(it))) }

                    val vec2 = pos1 - pos2

                    translations[overlap.first.second] = vec2 + t0Delta to newMapFunc
                } else if (!translations.keys.contains(overlap.first.first) && translations.keys.contains(overlap.first.second)) {
                    val (t0Delta, t0MapFunc) = translations[overlap.first.second]!!

                    val (delta2, delta1) = overlap.second
                    val inverseMapFunc = delta1.inverseMapFunc
                    val mapFunc = delta2.mapFunc
                    val pos1 = t0MapFunc(delta1.inverseMapFunc(delta1.pos1))
                    val pos2 = t0MapFunc(delta1.inverseMapFunc(delta2.pos1))

                    val newMapFunc = { it: Vec3 -> t0MapFunc(inverseMapFunc(mapFunc(it))) }

                    val vec2 = pos1 - pos2

                    translations[overlap.first.first] = vec2 + t0Delta to newMapFunc
                }
            }
    }

    return (translations.values).maxOf { (value1, _) ->
        (translations.values).maxOf { (value2, _) ->
            value1.manhattanDistanceTo(value2)
        }
    }
}