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

    fun computePowerSquareLevel(powerLevels: Array<Array<Int>>, x: Int, y: Int): Int {
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
            val powerLevel = computePowerSquareLevel(powerLevels, x - 1, y - 1)
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
    val prevSizePowerRows = Array(300) { Array(300) { 0 } }
    val prevSizePowerCols = Array(300) { Array(300) { 0 } }

    fun computePowerSquareLevel(powerLevels: Array<Array<Int>>, topLeftX: Int, topLeftY: Int, size: Int): Int {
        val bottomRightX = topLeftX + size - 1
        val bottomRightY = topLeftY + size - 1
        val prevPowerLevel = prevSizePowerSquares[topLeftX][topLeftY]
        val prevPowerCol = prevSizePowerCols[bottomRightX][topLeftY]
        val prevPowerRow = prevSizePowerRows[topLeftX][bottomRightY]
        val powerBottomRight = powerLevels[bottomRightX][bottomRightY]

        val powerLevel = prevPowerLevel + prevPowerCol + prevPowerRow + powerBottomRight

        prevSizePowerSquares[topLeftX][topLeftY] = powerLevel
        prevSizePowerCols[bottomRightX][topLeftY] = prevPowerCol + powerBottomRight
        prevSizePowerRows[topLeftX][bottomRightY] = prevPowerRow + powerBottomRight

        return powerLevel
    }

    var maxPowerSquare = PowerSquare(Int.MIN_VALUE, 0, 0, 0)

    for (size in 1..300) {
        val max = 300 - size
        for (x in 1..max) {
            for (y in 1..max) {
                val powerLevel = computePowerSquareLevel(powerLevels, x - 1, y - 1, size)
                if (maxPowerSquare.powerLevel < powerLevel) {
                    maxPowerSquare = PowerSquare(powerLevel, x, y, size)
                }
            }
        }
    }

    return Triple(maxPowerSquare.x, maxPowerSquare.y, maxPowerSquare.size)
}
