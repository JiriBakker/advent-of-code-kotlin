package v2018

import util.getBounds

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

private data class ClosestAreaResult(val coordinateArea: CoordinateArea, val minDistance: Int, val hasConflict: Boolean)

private fun parseCoordinateLines(coordinateLines: List<String>): List<CoordinateArea> {
    return coordinateLines
        .map { it.split(", ") }
        .mapIndexed { index, coordinates ->
            CoordinateArea(
                index,
                coordinates[0].toInt(),
                coordinates[1].toInt()
            )
        }
}

fun day06a(coordinateLines: List<String>): Int? {
    val coordinateAreas = parseCoordinateLines(coordinateLines)

    val (minX, maxX) = coordinateAreas.getBounds { it.x }
    val (minY, maxY) = coordinateAreas.getBounds { it.y }

    val isOuterBoundCoordinate = { x: Int, y: Int -> x == minX || x == maxX || y == minY || y == maxY }

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val closestAreaResult =
                coordinateAreas.fold(null as ClosestAreaResult?) { intermediateResult, coordinateArea ->
                    val distance = coordinateArea.distanceTo(x, y)
                    if (intermediateResult == null || distance < intermediateResult.minDistance) {
                        ClosestAreaResult(coordinateArea, distance, false)
                    } else if (distance == intermediateResult.minDistance) {
                        ClosestAreaResult(coordinateArea, distance, true)
                    } else {
                        intermediateResult
                    }
                }!!

            if (!closestAreaResult.hasConflict) {
                if (isOuterBoundCoordinate(x, y)) {
                    closestAreaResult.coordinateArea.markAsInfinite()
                } else {
                    closestAreaResult.coordinateArea.claimCell()
                }
            }
        }
    }

    return coordinateAreas.filter { !it.isInfinite }.map { it.nrOfClaimedCells }.maxOrNull()
}

private fun <T> List<T>.sumOfWhile(selector: (T) -> Int, predicate: (Int) -> Boolean): Int {
    return this.fold(0) {
        sum, item ->
            val newSum = sum + selector(item)
            if (!predicate(newSum)) {
                return newSum
            }
            newSum
    }
}

fun day06b(coordinateLines: List<String>, limit: Int = 10000): Int {
    val coordinateAreas = parseCoordinateLines(coordinateLines)

    val (minX, maxX) = coordinateAreas.getBounds { it.x }
    val (minY, maxY) = coordinateAreas.getBounds { it.y }

    var nrOfCellsWithinLimit = 0

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val summedDistance = coordinateAreas.sumOfWhile({ it.distanceTo(x, y) }) { it < limit }
            if (summedDistance < limit) {
                nrOfCellsWithinLimit++
            }
        }
    }

    return nrOfCellsWithinLimit
}