private fun List<String>.parseDots(): List<Pair<Int, Int>> =
    takeWhile { it.isNotEmpty() }
        .map { line ->
            val (x, y) = line.split(",").map { it.toInt() }
            x to y
        }

private fun List<String>.parseFoldInstructions(): List<Pair<String, Int>> =
    dropWhile { it.isNotEmpty() }
        .drop(1)
        .map { line ->
            line
                .split(" ")
                .get(2)
                .split("=")
                .let { it[0] to it[1].toInt() }
        }

private class Manual(points: List<Pair<Int,Int>>) {

    private val dots = points.toMutableSet()

    val dotCount get() = dots.count()

    fun foldX(foldLineX: Int) {
        dots.toList().forEach { (x, y) ->
            if (x > foldLineX) {
                val foldedX = foldLineX - (x - foldLineX)
                dots.remove(x to y)
                dots.add(foldedX to y)
            }
        }
    }

    fun foldY(foldLineY: Int) {
        dots.toList().forEach { (x, y) ->
            if (y > foldLineY) {
                val foldedY = foldLineY - (y - foldLineY)
                dots.remove(x to y)
                dots.add(x to foldedY)
            }
        }
    }

    fun toStrings(): List<String> {
        val maxX = dots.maxOf { it.first }
        val maxY = dots.maxOf { it.second }

        return (0 .. maxY).map { y ->
            (0 .. maxX).joinToString("") { x ->
                if (dots.any { it.first == x && it.second == y }) "#" else "."
            }
        }
    }
}

private fun Manual.fold(foldInstructions: List<Pair<String, Int>>) {
    foldInstructions.forEach { (axis, value) ->
        when (axis) {
            "x" -> foldX(value)
            "y" -> foldY(value)
        }
    }
}

fun day13a(input: List<String>): Int {
    val dots = input.parseDots()
    val foldInstructions = input.parseFoldInstructions()

    val manual = Manual(dots)

    manual.fold(foldInstructions.take(1))

    return manual.dotCount
}

fun day13b(input: List<String>): List<String> {
    val dots = input.parseDots()
    val foldInstructions = input.parseFoldInstructions()

    val manual = Manual(dots)

    manual.fold(foldInstructions)

    return manual.toStrings()
}