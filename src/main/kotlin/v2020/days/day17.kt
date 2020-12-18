package v2020.days.day17

private class Space3D(input: List<String>) {
    private var cubes: Map<Int, Map<Int, Map<Int, Boolean>>>

    private var minX: Int = Int.MAX_VALUE
    private var maxX: Int = Int.MIN_VALUE
    private var minY: Int = Int.MAX_VALUE
    private var maxY: Int = Int.MIN_VALUE
    private var minZ: Int = Int.MAX_VALUE
    private var maxZ: Int = Int.MIN_VALUE

    init {
        cubes = mapOf(
            0 to input.mapIndexed { y, row ->
                y to row.mapIndexed { x, cube ->
                    if (cube == '#') x to true
                    else null
                }.filterNotNull().toMap()
            }.toMap())
    }

    private fun updateMinMax() {
        minX = Int.MAX_VALUE
        maxX = Int.MIN_VALUE
        minY = Int.MAX_VALUE
        maxY = Int.MIN_VALUE
        minZ = Int.MAX_VALUE
        maxZ = Int.MIN_VALUE
        cubes.forEach { (z, slice) ->
            if (z < minZ) minZ = z
            if (z > maxZ) maxZ = z
            slice.forEach { (y, row) ->
                if (y < minY) minY = y
                if (y > maxY) maxY = y
                row.forEach { (x, cube) ->
                    if (x < minX) minX = x
                    if (x > maxX) maxX = x
                }
            }
        }
    }

    fun print() {
        updateMinMax()
        for (z in minZ..maxZ) {
            println("z=$z ${this.countActive()}")
            for (y in minY..maxY) {
                for (x in minX..maxX) {
                    print(if (isActive(x, y, z)) '#' else '.')
                }
                println()
            }
            println()
        }
    }

    fun iterate() {
        updateMinMax()
        val newCubes = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, Boolean>>>()

        fun set(x: Int, y: Int, z: Int) {
            val slice = newCubes.getOrPut(z) { mutableMapOf() }
            val row = slice.getOrPut(y) { mutableMapOf() }
            row[x] = true
        }

        for (z in minZ-1..maxZ+1) {
            for (y in minY-1..maxY+1) {
                for (x in minX-1..maxX+1) {
                    val isCubeActive = isActive(x, y, z)
                    val activeNeighbourCount = countActiveNeighbours(x, y, z)
                    if (isCubeActive) {
                        if (activeNeighbourCount in 2..3) {
                            set(x, y, z)
                        }
                    } else if (activeNeighbourCount == 3) {
                        set(x, y, z)
                    }
                }
            }
        }

        cubes = newCubes
    }

    private fun isActive(x: Int, y: Int, z: Int): Boolean =
        cubes[z]?.get(y)?.get(x) == true

    private fun countActiveNeighbours(x: Int, y: Int, z: Int): Int {
        var count = 0
        for (dz in -1..1) {
            for (dy in -1..1) {
                for (dx in -1..1) {
                    if (dz == 0 && dy == 0 && dx == 0) {
                        continue
                    }
                    if (isActive(x + dx, y + dy, z + dz)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    fun countActive(): Int {
        return cubes.values.sumBy { slice -> slice.values.sumBy { row -> row.count() } }
    }
}

private class Space4D(input: List<String>) {
    private var spaceTime: Map<Int, Map<Int, Map<Int, Map<Int, Boolean>>>>

    private var minX: Int = Int.MAX_VALUE
    private var maxX: Int = Int.MIN_VALUE
    private var minY: Int = Int.MAX_VALUE
    private var maxY: Int = Int.MIN_VALUE
    private var minZ: Int = Int.MAX_VALUE
    private var maxZ: Int = Int.MIN_VALUE
    private var minW: Int = Int.MAX_VALUE
    private var maxW: Int = Int.MIN_VALUE

    init {
        spaceTime = mapOf(
            0 to mapOf(
                0 to input.mapIndexed { y, row ->
                    y to row.mapIndexed { x, cube ->
                        if (cube == '#') x to true
                        else null
                    }.filterNotNull().toMap()
                }.toMap()
            ).toMap())
    }

    private fun updateMinMax() {
        minX = Int.MAX_VALUE
        maxX = Int.MIN_VALUE
        minY = Int.MAX_VALUE
        maxY = Int.MIN_VALUE
        minZ = Int.MAX_VALUE
        maxZ = Int.MIN_VALUE
        minW = Int.MAX_VALUE
        maxW = Int.MIN_VALUE
        spaceTime.forEach { (w, space) ->
            if (w < minW) minW = w
            if (w > maxW) maxW = w
            space.forEach { (z, slice) ->
                if (z < minZ) minZ = z
                if (z > maxZ) maxZ = z
                slice.forEach { (y, row) ->
                    if (y < minY) minY = y
                    if (y > maxY) maxY = y
                    row.forEach { (x, cube) ->
                        if (x < minX) minX = x
                        if (x > maxX) maxX = x
                    }
                }
            }
        }
    }

    fun print() {
        updateMinMax()
        for (w in minW..maxW) {
            for (z in minZ..maxZ) {
                println("z=$z w=$w")
                for (y in minY..maxY) {
                    for (x in minX..maxX) {
                        print(if (isActive(x, y, z, w)) '#' else '.')
                    }
                    println()
                }
                println()
            }
            println()
        }
    }

    fun iterate() {
        updateMinMax()
        val newSpaceTime = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, Boolean>>>>()

        fun setActive(x: Int, y: Int, z: Int, w: Int) {
            val space = newSpaceTime.getOrPut(w) { mutableMapOf() }
            val slice = space.getOrPut(z) { mutableMapOf() }
            val row = slice.getOrPut(y) { mutableMapOf() }
            row[x] = true
        }

        for (w in minW-1..maxW+1) {
            for (z in minZ - 1..maxZ + 1) {
                for (y in minY - 1..maxY + 1) {
                    for (x in minX - 1..maxX + 1) {
                        val isCubeActive = isActive(x, y, z, w)
                        val activeNeighbourCount = countActiveNeighbours(x, y, z, w)
                        if (isCubeActive) {
                            if (activeNeighbourCount in 2..3) {
                                setActive(x, y, z, w)
                            }
                        } else if (activeNeighbourCount == 3) {
                            setActive(x, y, z, w)
                        }
                    }
                }
            }
        }

        spaceTime = newSpaceTime
    }

    private fun isActive(x: Int, y: Int, z: Int, w: Int): Boolean =
        spaceTime[w]?.get(z)?.get(y)?.get(x) == true

    private fun countActiveNeighbours(x: Int, y: Int, z: Int, w: Int): Int {
        var count = 0
        for (dw in -1..1) {
            for (dz in -1..1) {
                for (dy in -1..1) {
                    for (dx in -1..1) {
                        if (dz == 0 && dy == 0 && dx == 0 && dw == 0) {
                            continue
                        }
                        if (isActive(x + dx, y + dy, z + dz, w + dw)) {
                            count++
                        }
                    }
                }
            }
        }
        return count
    }

    fun countActive(): Int {
        return spaceTime.values.sumBy { space -> space.values.sumBy { slice -> slice.values.sumBy { row -> row.count() } } }
    }
}


fun day17a(input: List<String>): Int {
    val space = Space3D(input)
    space.print()

    (1..6).forEach {
        space.iterate()
        space.print()
    }

    return space.countActive()
}

fun day17b(input: List<String>): Int {
    val space = Space4D(input)
    // space.print()

    (1..6).forEach {
        space.iterate()
        // space.print()
    }

    return space.countActive()
}

