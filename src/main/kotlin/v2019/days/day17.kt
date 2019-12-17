package v2019.days.day17

import v2019.intCoder.generateProgramOutput
import v2019.intCoder.parseIntCodes
import java.util.ArrayDeque

fun day17a(input: String): Long {
    val intCodes = parseIntCodes(input)

    val grid =
        generateProgramOutput(intCodes) { 0 }
            .map { it.toChar() }
            .joinToString("")
            .trim()
            .split('\n')

    grid.forEach(::println)

    val intersections = sequence {
        for (y in 1 until grid.size - 1) {
            for (x in 1 until grid[0].length - 1) {
                if (grid[y][x] == '#' &&
                    grid[y - 1][x] == '#' &&
                    grid[y + 1][x] == '#' &&
                    grid[y][x - 1] == '#' &&
                    grid[y][x + 1] == '#') {
                    yield(x to y)
                }
            }
        }
    }

    return intersections.sumBy { it.first * it.second }.toLong()
}

fun day17b(input: String): Long {
    val a = ("B,C,B,A,B,C,A,B,C,A\n" +
        "R,12,R,4,L,12,L,12\n" +
        "R,8,R,10,R,10\n" +
        "R,4,R,8,R,10,R,12\n" +
        "n\n").toList().map { it.toLong() }
    val movementInput = ArrayDeque<Long>(a)

    val intCodes = parseIntCodes(input)
    intCodes[0] = 2

    return generateProgramOutput(intCodes) { movementInput.poll() }.last()

}
