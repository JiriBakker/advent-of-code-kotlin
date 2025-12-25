import util.sumOfLong

fun day14a(input: List<String>): Long {
    val grid = input.map { it.toMutableList() }
    grid.moveAll(0, -1)
    return grid.measureWeight()
}

fun day14b(input: List<String>): Long {
    val grid = input.map { it.toMutableList() }

    val weights = (0 until 1000)
        .map {
            grid.moveAll(0, -1)
            grid.moveAll(-1, 0)
            grid.moveAll(0, 1)
            grid.moveAll(1, 0)
            grid.measureWeight()
        }

    return weights
        .findCycle()
        .extrapolateTo(1_000_000_000, weights)
}

private fun List<MutableList<Char>>.moveAll(dx: Int, dy: Int) {
    var y = if (dy == -1) 0 else this.size - 1

    while (y in this.indices) {
        var x = if (dx == -1) 0 else this[0].size - 1
        while (x in this[0].indices) {
            if (this[y][x] == 'O') {
                this.move(x, y, dx, dy)
            }
            x -= if (dx == 0) 1 else dx
        }
        y -= if (dy == 0) 1 else dy
    }
}

private fun List<MutableList<Char>>.move(x: Int, y: Int, dx: Int, dy: Int) {
    var curX = x + dx
    var curY = y + dy

    while (curX in this[0].indices && curY in this.indices) {
        if (this[curY][curX] != '.') break
        curX += dx
        curY += dy
    }

    curX -= dx
    curY -= dy

    if (curX != x || curY != y) {
        this[curY][curX] = 'O'
        this[y][x] = '.'
    }
}

private fun List<List<Char>>.measureWeight(): Long {
    return this.indices.sumOfLong { y ->
        this[y].indices.sumOfLong { x ->
            if (this[y][x] == 'O') this.size - y.toLong()
            else 0
        }
    }
}

private fun List<Long>.findCycle(): Pair<Int, Int> {
    for (cycleSize in 2 until this.size) {
        val firstOffsetWithCycle = this.indices.firstOrNull { offset -> this.isCycle(offset, cycleSize) }
        if (firstOffsetWithCycle != null) {
            return firstOffsetWithCycle to cycleSize
        }
    }
    throw Exception("No cycle found")
}

private fun List<Long>.isCycle(offset: Int, cycleSize: Int): Boolean {
    if (offset + cycleSize + cycleSize >= this.size) return false
    return this.subList(offset, offset + cycleSize) == this.subList(offset + cycleSize, offset + cycleSize + cycleSize)
}

private fun Pair<Int, Int>.extrapolateTo(cycle: Int, weights: List<Long>): Long {
    val (offset, cycleSize) = this
    return weights[(cycle - 1 - offset) % cycleSize + offset]
}