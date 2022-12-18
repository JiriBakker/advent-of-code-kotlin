import Day18.Cube
import Day18.CubeSide.X_NEG
import Day18.CubeSide.X_POS
import Day18.CubeSide.Y_NEG
import Day18.CubeSide.Y_POS
import Day18.CubeSide.Z_NEG
import Day18.CubeSide.Z_POS
import Day18.ExposedSide
import Day18.parseCubes

fun day18a(input: List<String>): Int {
    val cubes = input.parseCubes()

    return cubes.sumOf { it.exposedSides.size }
}

fun day18b(input: List<String>): Int {
    val cubes = input.parseCubes()

    val exposedSides = cubes.flatMap { cube ->
        cube.exposedSides.map { side ->
            ExposedSide(side, cube.x, cube.y, cube.z, cube)
        }
    }

    val startSide = exposedSides.filter { it.side == X_NEG }.minBy { it.x }

    val visited = mutableSetOf<ExposedSide>()

    val toVisit = ArrayDeque<ExposedSide>()
    toVisit.add(startSide)

    while (toVisit.isNotEmpty()) {
        val curSide = toVisit.removeFirst()

        if (!visited.add(curSide)) {
            continue
        }

        val (side, x, y, z, parentCube) = curSide

        when (side) {
            X_NEG -> {
                listOfNotNull(
                    exposedSides.firstOrNull { it.side == Y_NEG && it.x == x - 1 && it.y == y + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.x == x && it.y == y + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Y_POS && it.x == x - 1 && it.y == y - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.x == x && it.y == y - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_NEG && it.x == x - 1 && it.y == y && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.x == x && it.y == y && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_POS && it.x == x - 1 && it.y == y && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.x == x && it.y == y && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.cube == parentCube },
                )
                    .filter { !visited.contains(it) }
                    .forEach { toVisit.add(it) }
            }

            X_POS -> {
                listOfNotNull(
                    exposedSides.firstOrNull { it.side == Y_NEG && it.x == x + 1 && it.y == y + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.x == x && it.y == y + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Y_POS && it.x == x + 1 && it.y == y - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.x == x && it.y == y - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_NEG && it.x == x + 1 && it.y == y && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.x == x && it.y == y && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_POS && it.x == x + 1 && it.y == y && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.x == x && it.y == y && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.cube == parentCube },
                )
                    .filter { !visited.contains(it) }
                    .forEach { toVisit.add(it) }
            }

            Y_NEG -> {
                listOfNotNull(
                    exposedSides.firstOrNull { it.side == X_NEG && it.y == y - 1 && it.x == x + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.y == y && it.x == x + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == X_POS && it.y == y - 1 && it.x == x - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.y == y && it.x == x - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_NEG && it.y == y - 1 && it.x == x && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.y == y && it.x == x && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_POS && it.y == y - 1 && it.x == x && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.y == y && it.x == x && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.cube == parentCube },
                )
                    .filter { !visited.contains(it) }
                    .forEach { toVisit.add(it) }
            }

            Y_POS -> {
                listOfNotNull(
                    exposedSides.firstOrNull { it.side == X_NEG && it.y == y + 1 && it.x == x + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.y == y && it.x == x + 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == X_POS && it.y == y + 1 && it.x == x - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.y == y && it.x == x - 1 && it.z == z }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_NEG && it.y == y + 1 && it.x == x && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.y == y && it.x == x && it.z == z + 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Z_POS && it.y == y + 1 && it.x == x && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.y == y && it.x == x && it.z == z - 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.cube == parentCube },
                )
                    .filter { !visited.contains(it) }
                    .forEach { toVisit.add(it) }
            }

            Z_NEG -> {
                listOfNotNull(
                    exposedSides.firstOrNull { it.side == X_NEG && it.z == z - 1 && it.x == x + 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.z == z && it.x == x + 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == X_POS && it.z == z - 1 && it.x == x - 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.z == z && it.x == x - 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Y_NEG && it.z == z - 1 && it.x == x && it.y == y + 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.z == z && it.x == x && it.y == y + 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Y_POS && it.z == z - 1 && it.x == x && it.y == y - 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_NEG && it.z == z && it.x == x && it.y == y - 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.cube == parentCube },
                )
                    .filter { !visited.contains(it) }
                    .forEach { toVisit.add(it) }
            }

            Z_POS -> {
                listOfNotNull(
                    exposedSides.firstOrNull { it.side == X_NEG && it.z == z + 1 && it.x == x + 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.z == z && it.x == x + 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == X_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == X_POS && it.z == z + 1 && it.x == x - 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.z == z && it.x == x - 1 && it.y == y }
                        ?: exposedSides.firstOrNull { it.side == X_NEG && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Y_NEG && it.z == z + 1 && it.x == x && it.y == y + 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.z == z && it.x == x && it.y == y + 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_POS && it.cube == parentCube },

                    exposedSides.firstOrNull { it.side == Y_POS && it.z == z + 1 && it.x == x && it.y == y - 1 }
                        ?: exposedSides.firstOrNull { it.side == Z_POS && it.z == z && it.x == x && it.y == y - 1 }
                        ?: exposedSides.firstOrNull { it.side == Y_NEG && it.cube == parentCube },
                )
                    .filter { !visited.contains(it) }
                    .forEach { toVisit.add(it) }
            }
        }
    }

    return visited.size
}

object Day18 {

    fun List<String>.parseCubes(): List<Cube> {
        val cubes =
            this.map { line ->
                val (x, y, z) = line.split(",").map(String::toInt)
                Cube(x, y, z)
            }

        cubes.forEachIndexed { index, cube ->
            cubes.drop(index + 1).forEach { otherCube ->
                cube.checkTouching(otherCube)
            }
        }

        return cubes
    }

    data class ExposedSide(val side: CubeSide, val x: Int, val y: Int, val z: Int, val cube: Cube)
    class Cube(val x: Int, val y: Int, val z: Int) {
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