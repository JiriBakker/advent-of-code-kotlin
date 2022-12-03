import util.partitionIndexed
import util.safeMod

private fun applyOperations(operations: List<String>): List<BooleanArray> {
    val display = List(6) { BooleanArray(60) }

    operations
        .map { it.split(" ") }
        .forEach { operation ->
            when {
                operation[0] == "rect" -> {
                    val (width, height) = operation[1].split("x").map(String::toInt)
                    for (y in 0 until height) {
                        for (x in 0 until width) {
                            display[y][x] = true
                        }
                    }
                }
                operation[1] == "row" -> {
                    val y = operation[2].split('=')[1].toInt()
                    val distance = operation[4].toInt()
                    (0 until 50)
                        .map { x -> display[y][(x - distance).safeMod(50)] }
                        .forEachIndexed { x, isLightOn -> display[y][x] = isLightOn }
                }
                operation[1] == "column" -> {
                    val x = operation[2].split('=')[1].toInt()
                    val distance = operation[4].toInt()
                    (0 until 6)
                        .map { y -> display[(y - distance).safeMod(6)][x] }
                        .forEachIndexed { y, isLightOn -> display[y][x] = isLightOn }
                }
                else -> error("Unknown operation: $operation")
            }
        }

    return display
}

fun day08a(input: List<String>): Int {
    val display = applyOperations(input)
    return display.sumOf { row -> row.count { it } }
}

fun day08b(input: List<String>): String {
    val display = applyOperations(input)
    return display.joinToString("") { row ->
        row.map { if (it) "█" else " " }.plus('\n').joinToString("")
    }
}