fun day15a(input: List<String>): Long {
    val grid = input.takeWhile(String::isNotEmpty).map { line -> line.replace('O', '[').toCharArray() }

    val moves = input.drop(grid.size + 1).joinToString("")

    val boxes = grid.findBoxes(width = 1)

    val (startX, startY) = grid.findStartPosition()
    grid[startY][startX] = '.'

    return grid
        .applyMoves(startX, startY, moves, boxes)
        .computeGpsCoordinatesSum()
}

fun day15b(input: List<String>): Long {
    val grid = input.takeWhile(String::isNotEmpty).widen().map(String::toCharArray)


    val moves = input.drop(grid.size + 1).joinToString("")

    val boxes = grid.findBoxes()

    val (startX, startY) = grid.findStartPosition()
    grid[startY][startX] = '.'

    return grid
        .applyMoves(startX, startY, moves, boxes)
        .computeGpsCoordinatesSum()
}

private class Box(var leftX: Int, var rightX: Int, var y: Int)

private fun List<String>.widen() =
    this.map { line ->
        line.map {
            when (it) {
                '@' -> "@."
                'O' -> "[]"
                '.' -> ".."
                else -> "##"
            }
        }.joinToString("")
    }

private fun List<CharArray>.findBoxes(width: Int = 2): List<Box> {
    val boxes = mutableListOf<Box>()
    for (y in this.indices) {
        for (x in this[y].indices) {
            if (this[y][x] == '[') {
                boxes.add(Box(x, x + (width - 1), y))
            }
        }
    }
    return boxes
}

private fun List<CharArray>.findStartPosition(): Pair<Int, Int> {
    for (y in indices) {
        for (x in this[y].indices) {
            if (this[y][x] == '@') {
                return x to y
            }
        }
    }
    error("No start position found")
}

private fun List<CharArray>.applyMoves(startX: Int, startY: Int, moves: String, boxes: List<Box>): List<CharArray> {
    var curX = startX
    var curY = startY

    fun findBoxesToMove(x: Int, y: Int, dx: Int, dy: Int): List<Box> {
        val box = boxes.find { (it.leftX == x && it.y == y) || (it.rightX == x && it.y == y) } ?: error("No box found at $x, $y")

        if (this[box.y + dy][box.leftX + dx] == '#' || this[box.y + dy][box.rightX + dx] == '#') {
            // Blocked by wall
            return emptyList()
        }

        if (this[box.y + dy][box.leftX + dx] == '.' && this[box.y + dy][box.rightX + dx] == '.') {
            // Has empty space to move to
            return listOf(box)
        }

        val boxesToMoveDirectly =
            if (dy == 0) {
                // Horizontal contact
                boxes.filter { it.y == box.y && (it.rightX == box.leftX + dx || it.leftX == box.rightX + dx) && it != box }
            } else {
                // Vertical contact
                boxes.filter { it.y == box.y + dy && (it.leftX == box.leftX || it.rightX == box.leftX || it.leftX == box.rightX ) }
            }

        val allBoxesToMove = boxesToMoveDirectly.map { findBoxesToMove(it.leftX, it.y, dx, dy) }
        if (allBoxesToMove.any { it.isEmpty() }) {
            // If any of the boxes can't move (empty list), then we can't move this box either
            return emptyList()
        }

        return allBoxesToMove.flatten().distinct() + box
    }

    fun moveBox(box: Box, dx: Int, dy: Int) {
        this[box.y][box.leftX] = '.'
        this[box.y][box.rightX] = '.'
        this[box.y + dy][box.rightX + dx] = ']'
        this[box.y + dy][box.leftX + dx] = '['
        box.y += dy
        box.leftX += dx
        box.rightX += dx
    }

    fun move(dx: Int, dy: Int) {
        if (this[curY + dy][curX + dx] == '#') {
            return
        }
        if (this[curY + dy][curX + dx] == '.') {
            curX += dx
            curY += dy
            return
        }

        val boxesToPush = findBoxesToMove(curX + dx, curY + dy, dx, dy)
        if (boxesToPush.isNotEmpty()) {
            boxesToPush.forEach { moveBox(it, dx, dy) }
            curX += dx
            curY += dy
        }
    }

    for (move in moves) {
        when (move) {
            '^' -> move(0, -1)
            'v' -> move(0, 1)
            '<' -> move(-1, 0)
            '>' -> move(1, 0)
        }
    }

    return this
}

private fun List<CharArray>.computeGpsCoordinatesSum(): Long {
    var sum = 0L
    for (y in this.indices) {
        for (x in this[y].indices) {
            if (this[y][x] == '[') {
                sum += x + y*100
            }
        }
    }
    return sum
}
