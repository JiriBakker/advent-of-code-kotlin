package v2016

import util.readInputLine
import v2016.days.day01.day01a
import v2016.days.day01.day01b
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("Day01a: " + day01a(readInputLine("day01", 2016))) }
    time { print("Day01b: " + day01b(readInputLine("day01", 2016))) }
}
