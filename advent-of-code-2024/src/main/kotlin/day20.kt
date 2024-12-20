fun day20a(input: List<String>, minSave: Int = 100): Int {
    val grid = input.parseGrid()

    val noCheatingLength = grid.findPath()!!.length

    var count = 0

    for (y in 1 until grid.size - 1) {
        for (x in 1 until grid[y].size - 1) {
            val gridCopy = grid.toMutableList().map { it.copyOf() }
            if (gridCopy[y][x] != '#') {
                continue
            }

            gridCopy[y][x] = '.'

            val pathLength = gridCopy.findPath(maxLength = noCheatingLength)?.length ?: Int.MAX_VALUE

            if (pathLength <= noCheatingLength - minSave) {
                count++
            }
        }
    }

    return count
}


fun day20b(input: List<String>, minSave: Int = 100): Int {
    val grid = input.parseGrid()

    val normalPath = grid.findPath()!!
    val normalPathLength = normalPath.length

    val uniqueCheats = mutableSetOf<Pair<Pair<Int,Int>,Pair<Int,Int>>>()

    return grid.findPathWithCheats(normalPath = normalPath)
        .count { state ->
            val (_, _, _, cheats) = state
            if (cheats.size > 1 && !uniqueCheats.add(cheats.first() to cheats.last())) {
                false
            } else {
                println("Found unique path with length ${state.length} and cheats $cheats")
                state.length <= normalPathLength - minSave
            }
        }
}


private fun List<String>.parseGrid(): List<CharArray> {
    return this.map { it.toCharArray() }
}

private fun List<CharArray>.findPos(char: Char): Pair<Int, Int> {
    for (y in this.indices) {
        for (x in this[y].indices) {
            if (this[y][x] == char) {
                return x to y
            }
        }
    }

    error("No start position found")
}

private fun List<CharArray>.findPath(maxLength: Int = Int.MAX_VALUE): PathState? {
    val grid = this
    val (startX, startY) = grid.findPos('S')
    val (endX, endY) = grid.findPos('E')

    val queue = mutableListOf(PathState(startX, startY, null))
    val visited = mutableSetOf<Pair<Int, Int>>()


    while (queue.isNotEmpty()) {
        val state = queue.removeFirst()
        val (x, y, parent, cheats) = state
         // println("Checking $x, $y, $length")

        fun addToQueue(nextX: Int, nextY: Int) {
            if (nextX < 0 || nextX >= grid[0].size || nextY < 0 || nextY >= grid.size) {
                return
            }

            if (state.length + 1 >= maxLength) {
                return
            }

            if (visited.contains(nextX to nextY)) {
                return
            }

            if (grid[nextY][nextX] == '#') {
                return
            }

            queue.add(PathState(nextX, nextY, state))
        }

        if (x == endX && y == endY) {
            println("Found path with length ${state.length}")
            return state
        }

        if (!visited.add(x to y)) {
            continue
        }

        addToQueue(x + 1, y)
        addToQueue(x - 1, y)
        addToQueue(x, y + 1)
        addToQueue(x, y - 1)
    }

    return null
}

private fun List<CharArray>.findPathWithCheats(normalPath: PathState): Sequence<PathState> {
    val normalPathLength = normalPath.length
    val normalPathPositions = generateSequence(normalPath) { it.parent }.map { it.x to it.y }.toList()

    val grid = this
    val (startX, startY) = grid.findPos('S')
    val (endX, endY) = grid.findPos('E')

    val queue = mutableListOf(PathState(startX, startY, null, emptyList()))
    val visited = mutableSetOf<Triple<Int, Int, List<Pair<Int,Int>>>>()

    return sequence {
        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            val (x, y, parent, cheats) = state
            // println("Checking $x, $y, $length")

            fun addToQueue(nextX: Int, nextY: Int, nextCheats: List<Pair<Int, Int>>) {
                if (nextX < 0 || nextX >= grid[0].size || nextY < 0 || nextY >= grid.size) {
                    return
                }

                if (state.length + 1 >= normalPathLength) {
                    return
                }

                val cheatCount = nextCheats.size

                var nextCheatsFinal = nextCheats
                if (grid[nextY][nextX] == '#') {
                    if (cheatCount == 19) {
                        return
                    }
                    if (grid[y][x] != '#') {
                        if (cheatCount > 0) {
                            // Already cheated before
                            return
                        }
                        // Starting cheat (add previous 'free' pos)
                        nextCheatsFinal = mutableListOf(x to y, nextX to nextY)
                    } else {
                        // Continue cheat
                        nextCheatsFinal = nextCheats.plus(nextX to nextY)
                    }
                } else if (grid[y][x] == '#') {
                    if (cheatCount == 20) {
                        return
                    }
                    // Ending cheat
                    nextCheatsFinal = nextCheats.plus(nextX to nextY)
                }

                if (visited.contains(Triple(nextX, nextY, nextCheatsFinal))) {
                    return
                }

                queue.add(PathState(nextX, nextY, state, nextCheatsFinal))
            }

            if (x == endX && y == endY) {
                println("===============================Found path with length ${state.length} and cheats $cheats")
                yield(state)
                continue
            }


            if (!visited.add(Triple(x, y, cheats))) {
                continue
            }

            addToQueue(x + 1, y, cheats)
            addToQueue(x - 1, y, cheats)
            addToQueue(x, y + 1,  cheats)
            addToQueue(x, y - 1, cheats)
        }
    }
}

private data class PathState(val x: Int, val y: Int, val parent: PathState?, val cheats: List<Pair<Int, Int>> = emptyList()) {
    val length: Int by lazy { parent?.length?.plus(1) ?: 0 }
}

