import Day18.countNeighboursIn
import Day18.countNeighboursNotIn
import Day18.fillBoundingBox
import Day18.parseLavaCubes

fun day18a(input: List<String>): Int {
    val lavaCubes = input.parseLavaCubes()

    return lavaCubes.countNeighboursNotIn(lavaCubes)
}

fun day18b(input: List<String>): Int {
    val lavaCubes = input.parseLavaCubes()

    val fillerCubes = lavaCubes.fillBoundingBox()

    return lavaCubes.countNeighboursIn(fillerCubes)
}

object Day18 {

    fun List<String>.parseLavaCubes() =
        this.map { line ->
            val (x, y, z) = line.split(",").map(String::toInt)
            Cube(x, y, z)
        }.toSet()

    data class Cube(val x: Int, val y: Int, val z: Int) {
        fun neighbours() =
            listOf(
                Cube(x - 1, y, z),
                Cube(x + 1, y, z),
                Cube(x, y - 1, z),
                Cube(x, y + 1, z),
                Cube(x, y, z - 1),
                Cube(x, y, z + 1)
            )
    }

    fun Collection<Cube>.countNeighboursIn(cubes: Set<Cube>) =
        sumOf { cube -> cube.neighbours().count { cubes.contains(it) } }

    fun Collection<Cube>.countNeighboursNotIn(cubes: Set<Cube>) =
        sumOf { cube -> cube.neighbours().count { !cubes.contains(it) } }

    fun Collection<Cube>.fillBoundingBox(): Set<Cube> {
        val minX = this.minOf { it.x } - 1
        val maxX = this.maxOf { it.x } + 1
        val minY = this.minOf { it.y } - 1
        val maxY = this.maxOf { it.y } + 1
        val minZ = this.minOf { it.z } - 1
        val maxZ = this.maxOf { it.z } + 1

        val fillerCubes = mutableSetOf<Cube>()
        val toCheck = mutableListOf(Cube(minX, minY, minZ))

        while (toCheck.isNotEmpty()) {
            val fillerCube = toCheck.removeLast()

            if (fillerCube.x !in minX .. maxX
                || fillerCube.y !in minY .. maxY
                || fillerCube.z !in minZ .. maxZ
            ) {
                continue
            }

            if (fillerCube in this) {
                continue
            }

            if (!fillerCubes.add(fillerCube)) {
                continue
            }

            toCheck.addAll(fillerCube.neighbours())
        }

        return fillerCubes
    }
}