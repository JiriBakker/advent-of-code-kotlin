// package v2021
//
// import util.DoNotAutoExecute
// import util.max
// import util.min
//
// private data class CuboidIntersect(val min: Int, val max: Int, val isFullIntersect: Boolean)
//
// private fun computeLeftovers(original: Cuboid, remainingX: List<IntRange>, remainingY: List<IntRange>, remainingZ: List<IntRange>) =
//     remainingZ.flatMap { rangeZ ->
//         remainingY.flatMap { rangeY ->
//             remainingX.flatMap { rangeX ->
//                 listOf(
//                     Cuboid(rangeX.first, rangeX.last, original.minY, original.maxY, original.minZ, original.maxZ),
//                     Cuboid(rangeX.first, rangeX.last, rangeY.first, rangeY.last, rangeZ.first, rangeZ.last),
//                     Cuboid(rangeX.first, rangeX.last, rangeY.first, rangeY.last, rangeZ.first, rangeZ.last),
//                     Cuboid(rangeX.first, rangeX.last, rangeY.first, rangeY.last, rangeZ.first, rangeZ.last),
//
//             }
//         }
//     }
//
// private data class Cuboid(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int, val minZ: Int, val maxZ: Int, val isOn: Boolean = true) {
//     fun combine(other: Cuboid): List<Cuboid> {
//         fun findRemaining(min1: Int, max1: Int, min2: Int, max2: Int) =
//             when {                                                                      // 1 = keep, 2 = excess, 3 = intersect
//                 min1 == min2 && max1 == max2 -> listOf()                                //     |3333333|
//                 min1 == min2 && max1 > max2  -> listOf(max2 + 1..max1)                  //     |3333333|111|
//                 min1 == min2 && max1 < max2  -> listOf()                                //     |3333333|222|
//                 min1 < min2 && max1 > max2   -> listOf(min1 until min2, max2 + 1..max1) // |111|3333333|111|
//                 min1 < min2 && max1 >= min2  -> listOf(min1 until min2)                 // |111|3333333|
//                 min1 > min2 && max1 == max2  -> listOf()                                // |222|3333333|
//                 min1 < min2 && max1 >= min2  -> listOf(min1 until min2)                 // |111|333|222|
//                 min1 <= max2 && max1 > max2  -> listOf(max2 + 1..max1)                  //     |222|333|111|
//                 min1 > min2 && max1 < max2   -> listOf()                                // |222|3333333|222|
//                 else -> null
//             }
//
//         val remainingX = findRemaining(minX, maxX, other.minX, other.maxX)
//         val remainingY = findRemaining(minY, maxY, other.minY, other.maxY)
//         val remainingZ = findRemaining(minZ, maxZ, other.minZ, other.maxZ)
//
//         return if (remainingX != null && remainingY != null && remainingZ != null) {
//             val leftovers = computeLeftovers(this, remainingX, remainingY, remainingZ)
//             if (other.isOn) {
//                 listOf(other).plus(leftovers)
//             } else {
//                 leftovers
//             }
//         } else {
//             if (other.isOn) {
//                 listOf(this, other)
//             } else {
//                 listOf(this)
//             }
//         }
//     }
//
//     val size get() = (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)
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
//     val result =
//         cuboids.fold(emptyList<Cuboid>()) { acc, cuboid ->
//             if (acc.isEmpty()) {
//                 acc.plus(cuboid)
//             } else {
//                 val result = acc.flatMap { other ->
//                     other.combine(cuboid)
//                 }
//                 result
//             }
//         }
//
//     val count = result.sumOf { it.size }
//
//     return count
// }
//
// @DoNotAutoExecute
// fun day22b(input: List<String>): Int {
//     return 0
// }