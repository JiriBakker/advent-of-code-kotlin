import Day15.parseSensors
import java.math.BigInteger
import kotlin.math.abs

fun day15a(input: List<String>, y: Long = 10): Int {
    val sensors = input.parseSensors()
    val beacons = sensors.map { it.beaconX to it.beaconY }.toSet()

    val minX = sensors.minOf { it.minX }
    val maxX = sensors.maxOf { it.maxX }

    val withinSensorRangeCount =
        (minX - 1 .. maxX + 1).count { x ->
            sensors.any { it.isInRange(x, y) }
        }

    val beaconCount =
        beacons.count { it.second == y }

    return withinSensorRangeCount - beaconCount
}

fun day15b(input: List<String>, max: Long): BigInteger {
    val sensors = input.parseSensors()

    sensors.forEach { sensor ->
        // Unless the beacon is at the bottom right end, it will always have a sensor that 'touches' it with its top-left edge
        sensor.topLeftEdgeCoordinates
            .forEach { (x, y) ->
                if (x in 0..max && y in 0..max
                    && sensors.none { it.isInRange(x, y) }
                ) {
                    return 4000000.toBigInteger() * x.toBigInteger() + y.toBigInteger()
                }
            }
    }

    throw Exception("No possible beacon location found")
}

object Day15 {

    fun List<String>.parseSensors() =
        this.map { line ->
            val lineParts = line.split(" ")
            val x = lineParts[2].drop(2).dropLast(1).toLong()
            val y = lineParts[3].drop(2).dropLast(1).toLong()
            val beaconX = lineParts[8].drop(2).dropLast(1).toLong()
            val beaconY = lineParts[9].drop(2).toLong()
            Sensor(x, y, beaconX, beaconY)
        }

    class Sensor(val x: Long, val y: Long, val beaconX: Long, val beaconY: Long) {
        private val beaconFreeRange = abs(x - beaconX) + abs(y - beaconY)

        val minX = x - beaconFreeRange
        val maxX = x + beaconFreeRange

        fun isInRange(otherX: Long, otherY: Long) =
            (abs(x - otherX) + abs(y - otherY)) <= beaconFreeRange

        val topLeftEdgeCoordinates get() =
            sequence {
                var curX = minX - 1

                (y downTo y - beaconFreeRange).forEach {
                    yield(curX to it)
                    curX++
                }
            }

    }
}