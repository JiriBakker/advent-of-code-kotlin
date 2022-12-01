package v2020

import util.leastCommonMultiple
import util.safeMod

fun day13a(input: List<String>): Long {
    val departTime = input[0].toLong()
    val busIntervals = input[1].replace("x,", "").split(",").map { it.toLong() }

    fun computeTimeToWait(busInterval: Long): Long {
        return busInterval - departTime % busInterval
    }

    return busIntervals
        .minByOrNull { computeTimeToWait(it) }!!
        .let { computeTimeToWait(it) * it }
}

private class BusCycle(val interval: Long, val offset: Long)

private fun findAlignmentTime(busCycle1: BusCycle, busCycle2: BusCycle): BusCycle {
    var time = busCycle1.offset
    while (time < Long.MAX_VALUE) {
        val doBusCyclesAlign = (time + busCycle2.offset).safeMod(busCycle2.interval) == 0L
        if (doBusCyclesAlign) {
            return BusCycle(leastCommonMultiple(busCycle1.interval, busCycle2.interval), time)
        }
        time += busCycle1.interval
    }
    error("Buses never align (or takes too long)")
}

fun day13b(input: List<String>): Long {
    val buses = input[1]
        .split(",")
        .mapIndexedNotNull { index, busNr -> if (busNr == "x") null else BusCycle(busNr.toLong(), index.toLong()) }

    return buses.reduce { acc, bus -> findAlignmentTime(acc, bus) }.offset
}

