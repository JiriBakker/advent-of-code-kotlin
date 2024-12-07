fun day06a(input: List<String>): Int {
    return computePathLength(input)!!
}

fun day06b(input: List<String>): Int {
    return input.indices.sumOf { obstructionY ->
        input[obstructionY].indices.count { obstructionX ->
            input[obstructionY][obstructionX] == '.'
                && computePathLength(input, obstructionX, obstructionY) == null
        }
    }
}

private fun List<String>.getStartPosition() =
    this
        .withIndex()
        .first { (_, row) -> row.contains('^') }
        .let { (y, row) -> row.indexOf('^') to y }

private fun Pair<Int,Int>.getNextDirection() =
    when (this) { // x, y
        0 to -1 ->  1 to  0  // up    -> right
        1 to  0 ->  0 to  1  // right -> down
        0 to  1 -> -1 to  0  // down  -> left
        else    ->  0 to -1  // left  -> up
    }

private fun computePathLength(input: List<String>, obstructionX: Int = -1, obstructionY: Int = -1): Int? {
    val (startX, startY) = input.getStartPosition()

    val visited = mutableSetOf<Pair<Pair<Int, Int>,Pair<Int,Int>>>()

    var x = startX
    var y = startY

    var dx = 0
    var dy = -1

    while (y in input.indices && x in input[y].indices) {
        if (input[y][x] == '#' || (x == obstructionX && y == obstructionY)) {
            x -= dx
            y -= dy

            (dx to dy).getNextDirection()
                .let { (newDx, newDy) ->
                    dx = newDx
                    dy = newDy
                }
        } else {
            val cur = (x to y) to (dx to dy)
            if (cur in visited) {
                return null // Looped path, will never end
            }
            visited += cur
        }

        x += dx
        y += dy
    }

    return visited.map { it.first }.distinct().size
}