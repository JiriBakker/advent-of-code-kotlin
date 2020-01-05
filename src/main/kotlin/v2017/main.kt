package v2017

import util.readInputLine
import util.readInputLines
import v2016.days.day02.day02a
import v2016.days.day02.day02b
import v2017.days.day01.day01a
import v2017.days.day01.day01b
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("Day01a: " + day01a(readInputLine("day01", 2017))) }
    time { print("Day01b: " + day01b(readInputLine("day01", 2017))) }
    time { print("Day02a: " + day02a(readInputLines("day02", 2017))) }
    time { print("Day02b: " + day02b(readInputLines("day02", 2017))) }
}
