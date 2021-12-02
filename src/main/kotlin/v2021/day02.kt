package v2021

private fun List<String>.parseMovements(): List<Pair<String, Int>> {
    return map { line ->
        val (direction, amount) = line.split(" ")
        direction to amount.toInt()
    }
}

fun day02a(input: List<String>): Int {
    var x = 0
    var y = 0

    input.parseMovements()
        .forEach { (direction, amount) ->
            when (direction) {
                "forward" -> x += amount
                "down" -> y += amount
                "up" -> y -= amount
            }
        }
    return x * y
}

fun day02b(input: List<String>): Int {
    var x = 0
    var y = 0
    var aim = 0

    input.parseMovements()
        .forEach { (direction, amount) ->
            when (direction) {
                "forward" -> {
                    x += amount
                    y += aim * amount
                }
                "down" -> {
                    aim += amount
                }
                "up" -> {
                    aim -= amount
                }
            }
        }

    return x * y
}