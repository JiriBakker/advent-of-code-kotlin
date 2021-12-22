// package v2021
//
// import util.DoNotAutoExecute
//
// private data class CuboidIntersect(val min: Int, val max: Int, val intersectType: CuboidIntersectType)
//
// private enum class CuboidIntersectType {
//     FULL,
//     LOWER,
//     UPPER
// }
//
// private data class Cuboid(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int, val minZ: Int, val maxZ: Int, val isOn: Boolean = true) {
//     fun combine(other: Cuboid): List<Cuboid> {
//         fun findIntersect(min1: Int, max1: Int, min2: Int, max2: Int) =
//             when {
//                 min1 >= min2 && max1 <= max2 -> CuboidIntersect(min1, max1, CuboidIntersectType.FULL)
//                 min1 in min2..max2 -> CuboidIntersect(min1, other.maxX, CuboidIntersectType.UPPER)
//                 max1 in min2..max2 -> CuboidIntersect(min2, max1, CuboidIntersectType.LOWER)
//                 else -> null
//             }
//
//         val intersectX = findIntersect(minX, maxX, other.minX, other.maxX)
//         val intersectY = findIntersect(minY, maxY, other.minY, other.maxY)
//         val intersectZ = findIntersect(minZ, maxZ, other.minZ, other.maxZ)
//
//         return if (intersectX != null && intersectY != null && intersectZ != null) {
//             if (other.isOn) {
//                 listOf(this).plus(other.minus(intersectX, intersectY, intersectZ))
//             } else {
//                 this.minus(intersectX, intersectY, intersectZ)
//             }
//         } else {
//             listOf(this, other)
//         }
//     }
//
//     fun minus(intersectX: CuboidIntersect, intersectY: CuboidIntersect, intersectZ: CuboidIntersect): List<Cuboid> {
//         if (intersectX.intersectType == CuboidIntersectType.FULL) {
//             if (intersectY.intersectType == CuboidIntersectType.FULL) {
//                 if (intersectZ.intersectType == CuboidIntersectType.FULL) { // F, F, F
//                     listOf(
//                         Cuboid(minX, maxX, minY, intersectY.min, minZ, maxZ),
//                         Cuboid(minX, maxX, intersectY.max, maxY, minZ, maxZ),
//                         Cuboid(minX, intersectX.min, intersectY.min, intersectY.max, minZ, maxZ),
//                         Cuboid(intersectX.max, maxX, intersectY.min, intersectY.max, minZ, maxZ),
//                         Cuboid(intersectX.min, intersectX.max, intersectY.min, intersectY.max, minZ, intersectZ.min),
//                         Cuboid(intersectX.min, intersectX.max, intersectY.min, intersectY.max, intersectZ.max, maxZ)
//                     )
//                 } else if (intersectZ.intersectType == CuboidIntersectType.UPPER) { // F, F, U
//                     listOf(
//                         Cuboid(minX, maxX, minY, maxY, minZ, intersectZ.min),
//                         Cuboid(minX, maxX, minY, intersectY.min, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, maxX, intersectY.max, maxY, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, intersectX.min, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                         Cuboid(intersectX.max, maxX, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                     )
//                 } else { // F, F, L
//                     listOf(
//                         Cuboid(minX, maxX, minY, maxY, intersectZ.max, maxZ),
//                         Cuboid(minX, maxX, minY, intersectY.min, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, maxX, intersectY.max, maxY, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, intersectX.min, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                         Cuboid(intersectX.max, maxX, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                     )
//                 }
//             } else if (intersectY.intersectType == CuboidIntersectType.UPPER) {
//                 if (intersectZ.intersectType == CuboidIntersectType.FULL) { // F, U, F
//                     listOf(
//                         Cuboid(minX, maxX, minY, intersectY.min, minZ, maxZ),
//                         Cuboid(minX, maxX, intersectY.max, maxY, minZ, maxZ),
//                         Cuboid(minX, intersectX.min, intersectY.min, intersectY.max, minZ, maxZ),
//                         Cuboid(intersectX.max, maxX, intersectY.min, intersectY.max, minZ, maxZ),
//                         Cuboid(intersectX.min, intersectX.max, intersectY.min, intersectY.max, minZ, intersectZ.min),
//                         Cuboid(intersectX.min, intersectX.max, intersectY.min, intersectY.max, intersectZ.max, maxZ)
//                     )
//                 } else if (intersectZ.intersectType == CuboidIntersectType.UPPER) { // F, U, U
//                     listOf(
//                         Cuboid(minX, maxX, minY, maxY, minZ, intersectZ.min),
//                         Cuboid(minX, maxX, minY, intersectY.min, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, maxX, intersectY.max, maxY, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, intersectX.min, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                         Cuboid(intersectX.max, maxX, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                     )
//                 } else { // F, U, L
//                     listOf(
//                         Cuboid(minX, maxX, minY, maxY, intersectZ.max, maxZ),
//                         Cuboid(minX, maxX, minY, intersectY.min, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, maxX, intersectY.max, maxY, intersectZ.min, intersectZ.max),
//                         Cuboid(minX, intersectX.min, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                         Cuboid(intersectX.max, maxX, intersectY.min, intersectY.max, intersectZ.min, intersectZ.max),
//                     )
//                 }
//             }
//         }
//
//
//         listOf(
//             Cuboid(this.minX, )
//         )
//     }
//
//
// }
//
// private fun List<String>.parseCuboids() =
//     map { line ->
//         val (isOn, coordinates) = line.split(" ")
//         val axis = coordinates.split(",")
//         val (minX, maxX) = axis[0].trimStart('x', '=').split("..")
//         val (minY, maxY) = axis[1].trimStart('y', '=').split("..")
//         val (minZ, maxZ) = axis[2].trimStart('z', '=').split("..")
//         Cuboid(minX.toInt(), maxX.toInt(), minY.toInt(), maxY.toInt(), minZ.toInt(), maxZ.toInt(), isOn == "on")
//     }
//
// fun day22a(input: List<String>): Int {
//     val cuboids = input.parseCuboids()
//
//     cuboids.fold(emptyList<Cuboid>()) { acc, cuboid ->
//         if (acc.isEmpty()) {
//             acc.plus(cuboid)
//         } else {
//             acc.flatMap { other ->
//                 cuboid.combine(other)
//             }
//         }
//     }
//
//     return 0
// }
//
// @DoNotAutoExecute
// fun day22b(input: List<String>): Int {
//     return 0
// }