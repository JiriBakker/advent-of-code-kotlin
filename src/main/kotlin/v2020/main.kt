package v2020

import util.readInputLine
import util.readInputLines
import v2020.days.day01.day01a
import v2020.days.day01.day01b
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("Day01a: " + day01a(readInputLines("day01", 2020))) }
    time { print("Day01b: " + day01b(readInputLines("day01", 2020))) }
}
