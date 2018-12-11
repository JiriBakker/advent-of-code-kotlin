package days.day11

fun computePowerLevel(x: Int, y: Int, serialNumber: Int): Int {
    val rackId = x + 10
    var powerLevel = rackId * y
    powerLevel += serialNumber
    powerLevel *= rackId
    powerLevel = (powerLevel % 1000) / 100
    powerLevel -= 5
    return powerLevel
}

private class PowerSquare(val powerLevel: Int, val x: Int, val y: Int, val size: Int)

fun day11a(serialNumberString: String): Pair<Int, Int> {
    val serialNumber = serialNumberString.toInt()
    val powerLevels = Array(300) { x -> Array(300) { y -> computePowerLevel(x + 1, y + 1, serialNumber) } }

    var maxPowerSquare: PowerSquare? = null

    fun computeTotalPower(powerLevels: Array<Array<Int>>, x: Int, y: Int): Int {
        return sequence {
            for (xi in x until (x + 3)) {
                for (yi in y until (y + 3)) {
                    yield(powerLevels[xi][yi])
                }
            }
        }.sum()
    }

    for (x in 1..298) {
        for (y in 1..298) {
            val powerLevel = computeTotalPower(powerLevels, x - 1, y - 1)
            if (maxPowerSquare == null || maxPowerSquare.powerLevel < powerLevel) {
                maxPowerSquare = PowerSquare(powerLevel, x, y, 3)
            }
        }
    }

    return Pair(maxPowerSquare!!.x, maxPowerSquare!!.y)
}

fun day11b(serialNumberString: String): Triple<Int, Int, Int> {
    val serialNumber = serialNumberString.toInt()
    val powerLevels = Array(300) { x -> Array(300) { y -> computePowerLevel(x + 1, y + 1, serialNumber) } }

    val prevSizePowerSquares = Array(300) { Array(300) { 0 } }
    fun computeTotalPower(powerLevels: Array<Array<Int>>, x: Int, y: Int, size: Int): Int {
        val prevPowerLevel = prevSizePowerSquares[x + 1][y + 1]
        return sequence {
            for (xi in x until (x + size - 1)) {
                yield(powerLevels[xi][y + size - 1])
            }
            for (yi in y until (y + size)) {
                yield(powerLevels[x + size - 1][yi])
            }
        }.sum() + prevPowerLevel
    }

    var maxPowerSquare = PowerSquare(Int.MIN_VALUE, 0, 0, 0)

    for (size in 1..300) {
        val max = 300 - size
        for (x in 1..max) {
            for (y in 1..max) {
                val powerLevel = computeTotalPower(powerLevels, x - 1, y - 1, size)
                prevSizePowerSquares[x][y] = powerLevel
                if (maxPowerSquare.powerLevel < powerLevel) {
                    maxPowerSquare = PowerSquare(powerLevel, x, y, size)
                }
            }
        }
    }

    return Triple(maxPowerSquare.x, maxPowerSquare.y, maxPowerSquare.size)
}
