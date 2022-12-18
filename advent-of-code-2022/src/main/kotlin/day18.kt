import Day18.Cube
import Day18.CubeSide.X_NEG
import Day18.CubeSide.X_POS
import Day18.CubeSide.Y_NEG
import Day18.CubeSide.Y_POS
import Day18.CubeSide.Z_NEG
import Day18.CubeSide.Z_POS
import Day18.parseCubes
import Day18.updateCoveredSides

fun day18a(input: List<String>): Int {
    val cubes = input.parseCubes()
    cubes.updateCoveredSides()

    return cubes.sumOf { it.exposedSides.size }
}

fun day18b(input: List<String>): Int {
    val cubes = input.parseCubes()
    cubes.updateCoveredSides()

    val cubesInSpace = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, Cube>>>()
    cubes.forEach { cube ->
        cubesInSpace
            .getOrPut(cube.z) { mutableMapOf() }
            .getOrPut(cube.y) { mutableMapOf() }
            .put(cube.x, cube)

    }

    fun isEmptyPosition(x: Int, y: Int, z: Int) =
        cubesInSpace[z]?.get(y)?.containsKey(x) != true

    val minX = cubes.minOf { it.x } - 1
    val maxX = cubes.maxOf { it.x } + 1
    val minY = cubes.minOf { it.y } - 1
    val maxY = cubes.maxOf { it.y } + 1
    val minZ = cubes.minOf { it.z } - 1
    val maxZ = cubes.maxOf { it.z } + 1

    val startFillCube = Cube(minX, minY, minZ)

    val filledCubes = mutableSetOf<Cube>()

    val toFill = ArrayDeque<Cube>()
    toFill.add(startFillCube)

    while (toFill.isNotEmpty()) {
        val cube = toFill.removeFirst()

        if (!filledCubes.add(cube)) {
            continue
        }

        val (x, y, z) = cube

        listOf(
            Cube(x - 1, y, z),
            Cube(x + 1, y, z),
            Cube(x, y - 1, z),
            Cube(x, y + 1, z),
            Cube(x, y, z - 1),
            Cube(x, y, z + 1)
        )
            .filter { (x, y, z) ->
                x in minX..maxX
                    && y in minY..maxY
                    && z in minZ..maxZ
                    && isEmptyPosition(x, y, z)
            }
            .forEach { toFill.add(it) }
    }

    filledCubes.updateCoveredSides()

    val width  = maxX - minX + 1
    val height = maxY - minY + 1
    val depth  = maxZ - minZ + 1

    val outerSurface = width*height*2 + width*depth*2 + height*depth*2

    return filledCubes.sumOf { it.exposedSides.size } - outerSurface
}

object Day18 {

    fun List<String>.parseCubes() =
        this.map { line ->
            val (x, y, z) = line.split(",").map(String::toInt)
            Cube(x, y, z)
        }

    fun Collection<Cube>.updateCoveredSides() =
        this.forEachIndexed { index, cube ->
            this.drop(index + 1).forEach { otherCube ->
                cube.checkTouching(otherCube)
            }
        }

    data class Cube(val x: Int, val y: Int, val z: Int) {
        private val coveredSides = mutableMapOf(
            X_NEG to false,
            X_POS to false,
            Y_NEG to false,
            Y_POS to false,
            Z_NEG to false,
            Z_POS to false
        )

        val exposedSides get() = coveredSides.filter { !it.value }.keys

        fun checkTouching(other: Cube) {
            when {
                this.y == other.y && this.z == other.z -> {
                    when (this.x) {
                        other.x - 1 -> {
                            this.coverSide(X_POS)
                            other.coverSide(X_NEG)
                        }

                        other.x + 1 -> {
                            this.coverSide(X_NEG)
                            other.coverSide(X_POS)
                        }
                    }
                }

                this.x == other.x && this.z == other.z -> {
                    when (this.y) {
                        other.y - 1 -> {
                            this.coverSide(Y_POS)
                            other.coverSide(Y_NEG)
                        }

                        other.y + 1 -> {
                            this.coverSide(Y_NEG)
                            other.coverSide(Y_POS)
                        }
                    }
                }

                this.x == other.x && this.y == other.y -> {
                    when (this.z) {
                        other.z - 1 -> {
                            this.coverSide(Z_POS)
                            other.coverSide(Z_NEG)
                        }

                        other.z + 1 -> {
                            this.coverSide(Z_NEG)
                            other.coverSide(Z_POS)
                        }
                    }
                }
            }
        }

        private fun coverSide(side: CubeSide) {
            coveredSides[side] = true
        }
    }

    enum class CubeSide {
        X_NEG,
        X_POS,
        Y_NEG,
        Y_POS,
        Z_NEG,
        Z_POS
    }
}