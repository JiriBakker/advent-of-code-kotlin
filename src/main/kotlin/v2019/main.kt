package v2019

import v2019.days.day01.day01a
import v2019.days.day01.day01b
import v2019.days.day02.day02a
import v2019.days.day02.day02b_binarySearch
import v2019.days.day03.day03a
import v2019.days.day03.day03b
import v2019.days.day04.day04a
import v2019.days.day04.day04b
import v2019.days.day05.day05a
import v2019.days.day05.day05b
import v2019.days.day06.day06a
import v2019.days.day06.day06b
import v2019.days.day07.day07a
import v2019.days.day07.day07b
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
    time { print("Day04a: " + day04a(readInputLine("day04"))) }
    time { print("Day04b: " + day04b(readInputLine("day04"))) }
    time { print("Day05a: " + day05a(readInputLine("day05"))) }
    time { print("Day05b: " + day05b(readInputLine("day05"))) }
    time { print("Day06a: " + day06a(readInputLines("day06"))) }
    time { print("Day06b: " + day06b(readInputLines("day06"))) }
    time { print("Day07a: " + day07a(readInputLine("day07"))) }
    time { print("Day07b: " + day07b(readInputLine("day07"))) }
}
