package v2016

import util.readInputLine
import util.readInputLines
import v2016.days.day01.day01a
import v2016.days.day01.day01b
import v2016.days.day02.day02a
import v2016.days.day02.day02b
import v2016.days.day03.day03a
import v2016.days.day03.day03b
import v2016.days.day04.day04a
import v2016.days.day04.day04b
import v2016.days.day05.day05a
import v2016.days.day05.day05b
import v2016.days.day06.day06a
import v2016.days.day06.day06b
import v2016.days.day07.day07a
import v2016.days.day07.day07b
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("Day01a: " + day01a(readInputLine("day01", 2016))) }
    time { print("Day01b: " + day01b(readInputLine("day01", 2016))) }
    time { print("Day02a: " + day02a(readInputLines("day02", 2016))) }
    time { print("Day02b: " + day02b(readInputLines("day02", 2016))) }
    time { print("Day03a: " + day03a(readInputLines("day03", 2016))) }
    time { print("Day03b: " + day03b(readInputLines("day03", 2016))) }
    time { print("Day04a: " + day04a(readInputLines("day04", 2016))) }
    time { print("Day04b: " + day04b(readInputLines("day04", 2016))) }
    time { print("Day05a: " + day05a(readInputLine("day05", 2016))) }
    time { print("Day05b: " + day05b(readInputLine("day05", 2016))) }
    time { print("Day06a: " + day06a(readInputLines("day06", 2016))) }
    time { print("Day06b: " + day06b(readInputLines("day06", 2016))) }
    time { print("Day07a: " + day07a(readInputLines("day07", 2016))) }
    time { print("Day07b: " + day07b(readInputLines("day07", 2016))) }
}
