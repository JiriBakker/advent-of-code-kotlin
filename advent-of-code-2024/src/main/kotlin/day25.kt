fun day25a(input: List<String>): Int {
    val (locks, keys) = input.parseLocksAndKeys()

    var matchCount = 0
    for (lock in locks) {
        for (key in keys) {
            if (fits(key, lock)) {
                matchCount++
            }
        }
    }

    return matchCount
}

private fun List<String>.parseLocksAndKeys(): Pair<List<Heights>, List<Heights>> {
    val locks = mutableListOf<Heights>()
    val keys = mutableListOf<Heights>()

    for (lines in this.chunked(8)) {
        val isLock = lines[0][0] == '#'
        if (isLock) {
            locks.add(lines.parseHeights('.'))
        } else {
            keys.add(lines.parseHeights('#'))
        }
    }

    return locks to keys
}

private fun List<String>.parseHeights(char: Char): Heights {
    val heights = mutableMapOf<Int, Int>()
    for (height in this.indices) {
        val line = this[height]
        if (0 !in heights && line[0] == char) {
            heights[0] = height
        }
        if (1 !in heights && line[1] == char) {
            heights[1] = height
        }
        if (2 !in heights && line[2] == char) {
            heights[2] = height
        }
        if (3 !in heights && line[3] == char) {
            heights[3] = height
        }
        if (4 !in heights && line[4] == char) {
            heights[4] = height
        }
    }

    return Heights(
        heights[0]!!,
        heights[1]!!,
        heights[2]!!,
        heights[3]!!,
        heights[4]!!
    )
}

private data class Heights(val h1: Int, val h2: Int, val h3: Int, val h4: Int, val h5: Int)

private fun fits(key: Heights, lock: Heights): Boolean {
    return key.h1 > lock.h1 - 1
        && key.h2 > lock.h2 - 1
        && key.h3 > lock.h3 - 1
        && key.h4 > lock.h4 - 1
        && key.h5 > lock.h5 - 1
}
