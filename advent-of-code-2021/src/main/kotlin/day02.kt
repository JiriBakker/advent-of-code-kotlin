private fun List<String>.parseMovements() =
    map { line ->
        line.split(" ")
            .let { (direction, amount) ->
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
                "down"    -> y += amount
                "up"      -> y -= amount
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
                "down" -> aim += amount
                "up"   -> aim -= amount
            }
        }

    return x * y
}