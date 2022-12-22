import Day22.computePassword
import Day22.parse
import Day22.PosAndDirection
import Day22.applyInstructions
import util.isInt

fun day22a(input: List<String>): Long {
    val (grid, instructions) = input.parse()

    fun getMinX(y: Int) = grid[y]!!.minOf { it.key }
    fun getMaxX(y: Int) = grid[y]!!.maxOf { it.key }
    fun getMinY(x: Int) = grid.filter { it.value.contains(x) }.minOf { it.key }
    fun getMaxY(x: Int) = grid.filter { it.value.contains(x) }.maxOf { it.key }

    val (curX, curY, dx, dy) =
        grid.applyInstructions(instructions) { (curX, curY, dx, dy) ->
            var nextX = curX
            var nextY = curY
            when {
                dx ==  1 -> nextX = getMinX(curY)
                dx == -1 -> nextX = getMaxX(curY)
                dy ==  1 -> nextY = getMinY(curX)
                dy == -1 -> nextY = getMaxY(curX)
            }
            PosAndDirection(nextX, nextY, dx, dy)
        }

    return computePassword(curX, curY, dx, dy)
}

fun day22b(input: List<String>): Long {
    val (grid, instructions) = input.parse()

    val edgeTransitions = mutableMapOf<Pair<Int, Int>, MutableMap<String, Triple<Int, Int, String>>>()

    fun linkEdge(x1Start: Int, y1Start: Int, x2Start: Int, y2Start: Int, dx1: Int = 0, dy1: Int = 0, dx2: Int = 0, dy2: Int = 0, face1: String, face2: String) {
        (0 until 50).forEach {
            val x1 = x1Start + it * dx1
            val y1 = y1Start + it * dy1
            val x2 = x2Start + it * dx2
            val y2 = y2Start + it * dy2
            edgeTransitions.getOrPut(x1 to y1) { mutableMapOf() }.put(face1, Triple(x2, y2, face2))
            edgeTransitions.getOrPut(x2 to y2) { mutableMapOf() }.put(face2, Triple(x1, y1, face1))
        }
    }

    // Input specific edges
    linkEdge( 50,   0,   0, 149, dy1 = 1, dy2 = -1, face1 = "left",   face2 = "left")   // A
    linkEdge( 50,   0,   0, 150, dx1 = 1, dy2 =  1, face1 = "top",    face2 = "left")   // B
    linkEdge( 99,   0, 100,   0, dy1 = 1, dy2 =  1, face1 = "right",  face2 = "left")   // C
    linkEdge( 50,  49,  50,  50, dx1 = 1, dx2 =  1, face1 = "bottom", face2 = "top")    // D
    linkEdge( 99,  50, 100,  49, dy1 = 1, dx2 =  1, face1 = "right",  face2 = "bottom") // E
    linkEdge( 99, 100, 149,  49, dy1 = 1, dy2 = -1, face1 = "right",  face2 = "right")  // F
    linkEdge(100,   0,   0, 199, dx1 = 1, dx2 =  1, face1 = "top",    face2 = "bottom") // G
    linkEdge( 49, 150,  50, 149, dy1 = 1, dx2 =  1, face1 = "right",  face2 = "bottom") // H
    linkEdge( 50,  99,  50, 100, dx1 = 1, dx2 =  1, face1 = "bottom", face2 = "top")    // I
    linkEdge(  0, 100,  50,  50, dx1 = 1, dy2 =  1, face1 = "top",    face2 = "left")   // J
    linkEdge( 49, 100,  50, 100, dy1 = 1, dy2 =  1, face1 = "right",  face2 = "left")   // K
    linkEdge(  0, 149,   0, 150, dx1 = 1, dx2 =  1, face1 = "bottom", face2 = "top")    // L

    val (curX, curY, dx, dy) =
        grid.applyInstructions(instructions) { (curX, curY, dx, dy) ->
            var newDx = dx
            var newDy = dy

            val direction =
                when {
                    dx ==  1 -> "right"
                    dx == -1 -> "left"
                    dy ==  1 -> "bottom"
                    else     -> "top"
                }

            val (nextX, nextY, linkedDirection) = edgeTransitions[curX to curY]!![direction]!!

            if (direction == linkedDirection) {
                newDx = -dx
                newDy = -dy
            } else if (direction == "right" || direction == "left") {
                if (linkedDirection == "top")    { newDx =  0; newDy =  1 }
                if (linkedDirection == "bottom") { newDx =  0; newDy = -1 }
            } else {
                if (linkedDirection == "left")   { newDx =  1; newDy =  0 }
                if (linkedDirection == "right")  { newDx = -1; newDy =  0 }
            }

            PosAndDirection(nextX, nextY, newDx, newDy)
        }

    return computePassword(curX, curY, dx, dy)
}


object Day22 {

    data class PosAndDirection(val x: Int, val y: Int, val dx: Int, val dy: Int)

    fun Map<Int, Map<Int, Char>>.applyInstructions(
        instructions: List<String>,
        wrapFunc: (PosAndDirection) -> PosAndDirection
    ): List<Int> {
        var curX = this[0]!!.minOf { it.key }
        var curY = 0

        var dx = 1
        var dy = 0

        instructions.forEach { instruction ->
            if (instruction.isInt()) {
                repeat(instruction.toInt()) {
                    val potentialNextX = curX + dx
                    val potentialNextY = curY + dy

                    // Wrap
                    val (actualNextX, actualNextY, newDx, newDy) =
                        if (this[potentialNextY]?.get(potentialNextX) == null) {
                            wrapFunc.invoke(PosAndDirection(curX, curY, dx, dy))
                        } else {
                            PosAndDirection(potentialNextX, potentialNextY, dx, dy)
                        }

                    if (this[actualNextY]!![actualNextX] == '#') {
                        return@forEach
                    }

                    dx = newDx
                    dy = newDy

                    curX = actualNextX
                    curY = actualNextY
                }
            } else {
                when (instruction) {
                    "R" -> {
                        val oldDx = dx
                        dx = -dy
                        dy = oldDx
                    }
                    "L" -> {
                        val oldDx = dx
                        dx = dy
                        dy = -oldDx
                    }
                    else -> throw Exception("Unknown instruction: $instruction")
                }
            }
        }

        return listOf(curX, curY, dx, dy)
    }

    fun computePassword(x: Int, y: Int, dx: Int, dy: Int): Long {
        val facingScore =
            when {
                dx == 1  -> 0
                dy == 1  -> 1
                dx == -1 -> 2
                dy == -1 -> 3
                else     -> throw Exception("Unexpected direction: $dx, $dy")
            }

        return (y + 1) * 1000L + (x + 1) * 4 + facingScore
    }

    fun List<String>.parse(): Pair<Map<Int, Map<Int, Char>>, List<String>> {
        val grid = mutableMapOf<Int, MutableMap<Int, Char>>()

        (0 until this.size - 1).forEach { y ->
            val row = grid.getOrPut(y) { mutableMapOf() }
            this[y].forEachIndexed { x, char ->
                if (char != ' ') {
                    row[x] = char
                }
            }
        }

        return grid to this.last().toInstructions()
    }

    private fun String.toInstructions(): List<String> {
        val instructions = mutableListOf<String>()
        var curInstruction = ""
        this.forEach { char ->
            if (char.isDigit()) {
                curInstruction += char
            } else {
                instructions.add(curInstruction)
                instructions.add(char.toString())
                curInstruction = ""
            }
        }

        if (curInstruction.isNotEmpty()) {
            instructions.add(curInstruction)
        }

        return instructions
    }
}