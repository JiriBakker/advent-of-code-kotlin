fun day14a(input: List<String>, maxX: Int = 101, maxY: Int = 103): Int {
    val robots = input.parseRobots()

    repeat(100) {
        robots.forEach { robot -> robot.step(maxX, maxY) }
    }

    val topLeftQuadrant = robots.count { robot -> robot.x < maxX / 2 && robot.y < maxY / 2 }
    val topRightQuadrant = robots.count { robot -> robot.x > maxX / 2 && robot.y < maxY / 2 }
    val bottomLeftQuadrant = robots.count { robot -> robot.x < maxX / 2 && robot.y > maxY / 2 }
    val bottomRightQuadrant = robots.count { robot -> robot.x > maxX / 2 && robot.y > maxY / 2 }

    return topLeftQuadrant * topRightQuadrant * bottomLeftQuadrant * bottomRightQuadrant
}

fun day14b(input: List<String>, maxX: Int = 101, maxY: Int = 103): Int {
    val maxSeconds = 100000

    val robots = input.parseRobots()

    repeat(maxSeconds) { second ->
        robots.forEach { robot -> robot.step(maxX, maxY) }

        if (detectChristmasTree(robots)) {
            return second + 1
        }
    }

    throw Exception("No christmas tree found after $maxSeconds seconds")
}

private fun detectChristmasTree(robots: List<Robot>): Boolean {
    // When showing the christmas tree, we assume all robots to be in a unique position
    val robotPositions = robots.map { it.x to it.y }.groupBy { it }
    return robotPositions.all { it.value.size == 1 }
}

private class Robot(x: Int, y: Int, dx: Int, dy: Int) {
    var x = x
        private set

    var y = y
        private set

    var dx = dx
        private set

    var dy = dy
        private set

    fun step(maxX: Int, maxY: Int) {
        x += dx
        y += dy
        while (x < 0) {
            x += maxX
        }
        if (x >= maxX) {
            x %= maxX
        }
        while (y < 0) {
            y += maxY
        }
        if (y >= maxY) {
            y %= maxY
        }
    }
}


private fun List<String>.parseRobots() =
    map { line ->
        val (position, velocity) = line.split(" ")
        val (x, y) = position.drop(2).split(",").map { it.toInt() }
        val (dx, dy) = velocity.drop(2).split(",").map { it.toInt() }
        Robot(x, y, dx, dy)
    }