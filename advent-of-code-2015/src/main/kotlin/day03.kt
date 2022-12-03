fun day03a(input: String): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(0 to 0)

    input.fold(0 to 0) { pos, char ->
        val nextPos = when (char) {
            '^'  -> pos.first to pos.second - 1
            '>'  -> pos.first + 1 to pos.second
            'v'  -> pos.first to pos.second + 1
            else -> pos.first - 1 to pos.second
        }
        visited.add(nextPos)
        nextPos
    }

    return visited.size
}

fun day03b(input: String): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(0 to 0)

    fun performMoves(moves: List<Char>) {
        moves.fold(0 to 0) { pos, char ->
            val nextPos = when (char) {
                '^'  -> pos.first to pos.second - 1
                '>'  -> pos.first + 1 to pos.second
                'v'  -> pos.first to pos.second + 1
                else -> pos.first - 1 to pos.second
            }
            visited.add(nextPos)
            nextPos
        }
    }

    val inputChunked = input.chunked(2)
    val santaMoves = inputChunked.map { it[0] }
    val roboMoves = inputChunked.map { it[1] }

    performMoves(santaMoves)
    performMoves(roboMoves)

    return visited.size
}
