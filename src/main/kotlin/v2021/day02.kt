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

    input.parseMovements().forEach { (direction, amount) ->
        when (direction) {
            "forward" -> x += amount
            "down" -> y += amount
            "up" -> y -= amount
            else -> throw Error("Unknown direction: $direction")
        }
    }
    return x * y
}

fun day02b(input: List<String>): Int {
    var x = 0
    var y = 0
    var aim = 0

    input.forEach { movement ->
        val (direction, amount) = movement.split(" ")
        when (direction) {
            "forward" -> {
                x += amount.toInt()
                y += aim * amount.toInt()
            }
            "down" -> {
                aim += amount.toInt()
            }
            "up" -> {
                aim -= amount.toInt()
            }
            else -> throw Error("Unknown direction: $direction")
        }
    }
    return x * y
}