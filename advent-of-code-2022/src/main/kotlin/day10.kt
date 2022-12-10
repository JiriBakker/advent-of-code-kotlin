import Day10.followInstructions

fun day10a(input: List<String>): Long {
    var sum = 0L

    input.followInstructions { cycle, x ->
        if ((cycle - 20) % 40 == 0) {
            sum += cycle * x
        }
    }

    return sum
}

fun day10b(input: List<String>): String {
    val screen = (0 until 6).associateWith { mutableSetOf<Int>() }

    var spritePos = 1
    fun draw(cycle: Int) {
        val row = (cycle - 1) / 40
        val col = (cycle - 1) % 40
        if (col - spritePos in 0 .. 2) {
            screen[row]!!.add(col)
        }
    }

    var prevX = 0
    input.followInstructions { cycle, x ->
        if (x != prevX) {
            spritePos = x - 1
            prevX = x
        }
        draw(cycle)
    }

    return (0 until 6).joinToString("\n") { y ->
        (0 until 40).joinToString("") { x ->
            if (screen[y]!!.contains(x)) "#"
            else "."
        }
    }
}

object Day10 {
    fun List<String>.followInstructions(midCycleCallback: (Int, Int) -> Unit) {
        var x = 1
        var cycle = 0

        fun nextCycle() {
            cycle++
            midCycleCallback(cycle, x)
        }

        this.map { instruction ->
            if (instruction.startsWith("addx ")) {
                nextCycle()
                nextCycle()
                x += instruction.drop(5).toInt()
            } else {
                nextCycle()
            }
        }
    }
}