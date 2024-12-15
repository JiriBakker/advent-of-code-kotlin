fun day15a(input: List<String>): Long {
    val grid = input.takeWhile(String::isNotEmpty).map(String::toCharArray)
    val moves = input.drop(grid.size + 1).joinToString("")

    var (curX, curY) = grid.findStartPosition()
    grid[curY][curX] = '.'

    fun moveUp() {
        if (grid[curY - 1][curX] == '#') {
            return
        }
        if (grid[curY - 1][curX] == '.') {
            curY--
            return
        }
        var y = curY - 1
        while (grid[y - 1][curX] == 'O') {
            y--
        }
        if (grid[y - 1][curX] == '.') {
            grid[y - 1][curX] = 'O'
            grid[curY - 1][curX] = '.'
            curY--
        }
    }

    fun moveDown() {
        if (grid[curY + 1][curX] == '#') {
            return
        }
        if (grid[curY + 1][curX] == '.') {
            curY++
            return
        }
        var y = curY + 1
        while (grid[y + 1][curX] == 'O') {
            y++
        }
        if (grid[y + 1][curX] == '.') {
            grid[y + 1][curX] = 'O'
            grid[curY + 1][curX] = '.'
            curY++
        }
    }

    fun moveLeft() {
        if (grid[curY][curX - 1] == '#') {
            return
        }
        if (grid[curY][curX - 1] == '.') {
            curX--
            return
        }
        var x = curX - 1
        while (grid[curY][x - 1] == 'O') {
            x--
        }
        if (grid[curY][x - 1] == '.') {
            grid[curY][x - 1] = 'O'
            grid[curY][curX - 1] = '.'
            curX--
        }
    }

    fun moveRight() {
        if (grid[curY][curX + 1] == '#') {
            return
        }
        if (grid[curY][curX + 1] == '.') {
            curX++
            return
        }
        var x = curX + 1
        while (grid[curY][x + 1] == 'O') {
            x++
        }
        if (grid[curY][x + 1] == '.') {
            grid[curY][x + 1] = 'O'
            grid[curY][curX + 1] = '.'
            curX++
        }
    }

    for (move in moves) {
        println("Move: $move")
        when (move) {
            '^' -> moveUp()
            'v' -> moveDown()
            '<' -> moveLeft()
            '>' -> moveRight()
        }
    }


    var sum = 0L
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == 'O') {
                sum += x + y*100
            }
        }
    }

    return sum
}

fun day15b(input: List<String>, widen: Boolean = true): Long {
    val grid = input.takeWhile(String::isNotEmpty).map { line ->
        if (widen) {
            line.map {
                when (it) {
                    '@' -> "@."
                    'O' -> "[]"
                    '.' -> ".."
                    else -> "##"
                }
            }.joinToString("").toCharArray()
        } else {
            line.toCharArray()
        }
    }

    val boxes = mutableListOf<Box>()
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == '[') {
                boxes.add(Box(x, y, x + 1))
            }
        }
    }

    boxes.forEach { println("Box at ${it.x}-${it.x2}, ${it.y}") }

    val moves = input.drop(grid.size + 1).joinToString("")

    var (curX, curY) = grid.findStartPosition()
    grid[curY][curX] = '.'

    fun canPushBoxUp(x: Int, y: Int): List<Box> {
        val box = boxes.find { (it.x == x && it.y == y) || (it.x2 == x && it.y == y) } ?: error("No box found at $x, $y")
        if (grid[y - 1][box.x] == '#' || grid[y - 1][box.x2!!] == '#') {
            println("Blocked by edge ($x, $y)")
            return emptyList()
        }
        if (grid[y - 1][box.x] == '.' && (box.x2 == null || grid[y -1][box.x2!!] == '.')) {
            println("Free space above ($x, $y)")
            return listOf(box)
        }

        val boxesAbove = boxes.filter { it.y == y - 1 && (it.x == box.x || it.x2 == box.x || it.x == box.x2) }
        val canPushBoxesAbove = boxesAbove.map { canPushBoxUp(it.x, it.y) }
        if (canPushBoxesAbove.any { it.isEmpty() }) {
            println("Blocked by box above ($x, $y)")
            return emptyList()
        }

        println("Can push boxes above ($x, $y)")
        return canPushBoxesAbove.flatten().distinct() + box
    }

    fun pushBoxUp(box: Box) {
        grid[box.y - 1][box.x] = '['
        grid[box.y][box.x] = '.'
        if (box.x2 != null) {
            grid[box.y - 1][box.x2!!] = ']'
            grid[box.y ][box.x2!!] = '.'
        }
        box.y--
    }

    fun moveUp() {
        if (grid[curY - 1][curX] == '#') {
            return
        }
        if (grid[curY - 1][curX] == '.') {
            curY--
            return
        }

        val boxesAbove = canPushBoxUp(curX, curY - 1)
        if (boxesAbove.isNotEmpty()) {
            boxesAbove.forEach { pushBoxUp(it) }
            curY--
        }
    }

    fun canPushBoxDown(x: Int, y: Int): List<Box> {
        val box = boxes.find { (it.x == x && it.y == y) || (it.x2 == x && it.y == y) } ?: error("No box found at $x, $y")
        println("\nChecking box ${box.x}-${box.x2!!}, $y")
        if (grid[y + 1][box.x] == '#' || grid[y + 1][box.x2!!] == '#') {
            println("Blocked by edge ($x, $y)")
            return emptyList()
        }
        if (grid[y + 1][box.x] == '.' && grid[y + 1][box.x2!!] == '.') {
            println("Free space below ($x, $y)")
            return listOf(box)
        }

        val boxesBelow = boxes.filter { it.y == y + 1 && (it.x == box.x || it.x2 == box.x || it.x == box.x2) }
        boxesBelow.forEach { println("Box below at ${it.x}-${it.x2}, ${it.y}") }
        val canPushBoxesBelow = boxesBelow.map { canPushBoxDown(it.x, it.y) }
        if (canPushBoxesBelow.any { it.isEmpty() }) {
            println("Blocked by box below ($x, $y)")
            return emptyList()
        }

        println("Can push boxes below ($x, $y)")
        return canPushBoxesBelow.flatten().distinct() + box
    }

    fun pushBoxDown(box: Box) {
        grid[box.y + 1][box.x] = '['
        grid[box.y][box.x] = '.'
        if (box.x2 != null) {
            grid[box.y + 1][box.x2!!] = ']'
            grid[box.y ][box.x2!!] = '.'
        }
        box.y++
    }

    fun moveDown() {
        if (grid[curY + 1][curX] == '#') {
            return
        }
        if (grid[curY + 1][curX] == '.') {
            curY++
            return
        }

        val boxesBelow = canPushBoxDown(curX, curY + 1)
        if (boxesBelow.isNotEmpty()) {
            boxesBelow.forEach { println("Pushing box down ${it.x}-${it.x2}, ${it.y}") }
            boxesBelow.forEach { pushBoxDown(it) }
            curY++
        }
    }

    fun canPushBoxLeft(x: Int, y: Int): List<Box> {
        val box = boxes.find { it.x2 == x && it.y == y } ?: error("No box found at $x, $y")
        if (grid[y][box.x - 1] == '#') {
            println("Blocked by edge ($x, $y)")
            return emptyList()
        }
        if (grid[y][box.x - 1] == '.') {
            println("Free space left ($x, $y)")
            return listOf(box)
        }

        val boxesLeft = boxes.filter { it.y == box.y && it.x2 == box.x - 1 }
        val canPushBoxesLeft = boxesLeft.map { canPushBoxLeft(it.x2!!, it.y) }
        if (canPushBoxesLeft.any { it.isEmpty() }) {
            println("Blocked by box left ($x, $y)")
            return emptyList()
        }

        println("Can push boxes left ($x, $y) (${boxesLeft.size} boxes to the left)")
        return canPushBoxesLeft.flatten().distinct() + box
    }

    fun pushBoxLeft(box: Box) {
        println("Pushing box left ${box.x},${box.y}")
        grid[box.y][box.x] = ']'
        grid[box.y][box.x - 1] = '['
        grid[box.y][box.x2!!] = '.'
        box.x--
        box.x2 = box.x2!! - 1
    }

    fun moveLeft() {
        if (grid[curY][curX - 1] == '#') {
            return
        }
        if (grid[curY][curX - 1] == '.') {
            curX--
            return
        }

        val boxesLeft = canPushBoxLeft(curX - 1, curY)
        if (boxesLeft.isNotEmpty()) {
            boxesLeft.forEach { pushBoxLeft(it) }
            curX--
        }
    }

    fun canPushBoxRight(x: Int, y: Int): List<Box> {
        val box = boxes.find { it.x == x && it.y == y } ?: error("No box found at $x, $y")
        if (grid[y][box.x2!! + 1] == '#') {
            println("Blocked by edge ($x, $y)")
            return emptyList()
        }
        if (grid[y][box.x2!! + 1] == '.') {
            println("Free space left ($x, $y)")
            return listOf(box)
        }

        val boxesRight = boxes.filter { it.y == box.y && it.x == box.x2!! + 1 }
        val canPushBoxesRight = boxesRight.map { canPushBoxRight(it.x, it.y) }
        if (canPushBoxesRight.any { it.isEmpty() }) {
            println("Blocked by box left ($x, $y)")
            return emptyList()
        }

        println("Can push boxes right ($x, $y) (${boxesRight.size} boxes to the right)")
        return canPushBoxesRight.flatten().distinct() + box
    }

    fun pushBoxRight(box: Box) {
        println("Pushing box Right ${box.x},${box.y}")
        grid[box.y][box.x] = '.'
        grid[box.y][box.x2!!] = '['
        grid[box.y][box.x2!! + 1] = ']'
        box.x++
        box.x2 = box.x2!! + 1
    }

    fun moveRight() {
        if (grid[curY][curX + 1] == '#') {
            return
        }
        if (grid[curY][curX + 1] == '.') {
            curX++
            return
        }

        val boxesRight = canPushBoxRight(curX + 1, curY)
        if (boxesRight.isNotEmpty()) {
            boxesRight.forEach { pushBoxRight(it) }
            curX++
        }
    }

    for (move in moves) {
        when (move) {
            '^' -> moveUp()
            'v' -> moveDown()
            '<' -> moveLeft()
            '>' -> moveRight()
        }
    }


    var sum = 0L
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == '[') {
                sum += x + y*100
            }
        }
    }

    return sum
}

private class Box(var x: Int, var y: Int, var x2: Int? = null)

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