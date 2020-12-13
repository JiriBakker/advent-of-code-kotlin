package v2020.days.day13

import util.leastCommonMultiple
import util.safeMod

fun day13a(input: List<String>): Long {
    val departTime = input[0].toLong()
    val busIntervals = input[1].replace("x,", "").split(",").map { it.toLong() }

    fun computeTimeToWait(busNr: Long): Long {
        return busNr - departTime % busNr
    }

    return busIntervals
        .minByOrNull { computeTimeToWait(it) }!!
        .let { computeTimeToWait(it) * it }
}

private class Bus(val interval: Long, val offset: Long)

private fun findAlignmentTime(bus1: Bus, bus2: Bus): Bus {
    var time = bus1.offset
    while (time < Long.MAX_VALUE) {
        val busesAlign = (time + bus2.offset).safeMod(bus2.interval) == 0L
        if (busesAlign) {
            return Bus(leastCommonMultiple(bus1.interval, bus2.interval), time)
        }
        time += bus1.interval
    }
    error("Buses never align (or takes too long)")
}

fun day13b(input: List<String>): Long {
    val buses = input[1]
        .split(",")
        .mapIndexed { index, busNr -> if (busNr == "x") null else Bus(busNr.toLong(), index.toLong()) }
        .filterNotNull()

    return buses.reduce { acc, bus -> findAlignmentTime(acc, bus) }.offset
}

