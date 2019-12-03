package v2019

import v2019.days.day01.day01a
import v2019.days.day01.day01b
import v2019.days.day02.day02a
import v2019.days.day02.day02b_binarySearch
import v2019.days.day03.day03a
import v2019.days.day03.day03b
import v2019.extra.infiA
import v2019.extra.infiB
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("InfiA: " + infiA(readInputLines("infi"))) }
    time { print("InfiB: " + infiB(readInputLines("infi"))) }
    time { print("Day01a: " + day01a(readInputLines("day01"))) }
    time { print("Day01b: " + day01b(readInputLines("day01"))) }
    time { print("Day02a: " + day02a(readInputLine("day02"))) }
    time { print("Day02b: " + day02b_binarySearch(readInputLine("day02"))) }
    time { print("Day03a: " + day03a(readInputLines("day03"))) }
    time { print("Day03b: " + day03b(readInputLines("day03"))) }
}
