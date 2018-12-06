package days

private data class CoordinateArea(val id: Int, val x: Int, val y: Int) {
    var isInfinite = false
        private set

    fun markAsInfinite() {
        this.isInfinite = true
    }

    fun distanceTo(x: Int, y: Int): Int {
        return Math.abs(this.x - x) + Math.abs(this.y - y)
    }

    var nrOfClaimedCells = 0
        private set

    fun claimCell() {
        nrOfClaimedCells++
    }
}

private fun getGridBounds(coordinateAreas: List<CoordinateArea>): List<Int> {
    val xCoordinates = coordinateAreas.map { it.x }
    val yCoordinates = coordinateAreas.map { it.y }
    val minX = xCoordinates.min()!!
    val maxX = xCoordinates.max()!!
    val minY = yCoordinates.min()!!
    val maxY = yCoordinates.max()!!

    return listOf(minX, maxX, minY, maxY)
}

private data class ClosestAreaResult(val coordinateArea: CoordinateArea, val minDistance: Int, val hasConflict: Boolean)

private fun parseCoordinateLines(coordinateLines: List<String>): List<CoordinateArea> {
    return coordinateLines
        .map { it.split(", ") }
        .mapIndexed { index, coordinates ->  CoordinateArea(index, coordinates[0].toInt(), coordinates[1].toInt()) }
}

fun day06a(coordinateLines: List<String>): Int? {
    val coordinateAreas = parseCoordinateLines(coordinateLines)

    val (minX, maxX, minY, maxY) = getGridBounds(coordinateAreas)

    val isOuterBoundCoordinate = { x: Int, y: Int -> x == minX || x == maxX || y == minY || y == maxY }

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val closestAreaResult =
                coordinateAreas.fold(null as ClosestAreaResult?) {
                    intermediateResult, coordinateArea ->
                        val distance = coordinateArea.distanceTo(x, y)
                        if (intermediateResult == null || distance < intermediateResult.minDistance) {
                            ClosestAreaResult(coordinateArea, distance, false)
                        }
                        else if (distance == intermediateResult.minDistance) {
                            ClosestAreaResult(coordinateArea, distance, true)
                        }
                        else {
                            intermediateResult
                        }
                }!!

            if (isOuterBoundCoordinate(x, y)) {
                closestAreaResult.coordinateArea.markAsInfinite()
            }
            else if (!closestAreaResult.hasConflict) {
                closestAreaResult.coordinateArea.claimCell()
            }
        }
    }

    return coordinateAreas.filter { !it.isInfinite }.map { it.nrOfClaimedCells }.max()
}

fun day06b(coordinateLines: List<String>, limit: Int = 10000): Int {
    val coordinateAreas = parseCoordinateLines(coordinateLines)

    val (minX, maxX, minY, maxY) = getGridBounds(coordinateAreas)

    var nrOfCellsWithinLimit = 0

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val summedDistance = coordinateAreas.sumBy { it.distanceTo(x, y) }
            if (summedDistance < limit) {
                nrOfCellsWithinLimit++
            }
        }
    }

    return nrOfCellsWithinLimit
}