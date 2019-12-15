package v2015

import v2015.days.day01.day01a
import v2015.days.day01.day01b
import v2015.util.readInputLine
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("Day01a: " + day01a(readInputLine("day01"))) }
    time { print("Day01b: " + day01b(readInputLine("day01"))) }
}
