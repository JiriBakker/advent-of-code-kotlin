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
import v2016.days.day08.day08a
import v2016.days.day08.day08b
import v2016.days.day09.day09a
import v2016.days.day09.day09b
import v2016.days.day10.day10a
import v2016.days.day10.day10b
import v2016.days.day11.day11a
import v2016.days.day11.day11b
import v2016.days.day12.day12a
import v2016.days.day12.day12b
import v2016.days.day13.day13a
import v2016.days.day13.day13b
import v2016.days.day14.day14a
import v2016.days.day14.day14b
import v2016.days.day15.day15a
import v2016.days.day15.day15b
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
    time { print("Day08a: " + day08a(readInputLines("day08", 2016))) }
    time { print("Day08b:\n" + day08b(readInputLines("day08", 2016))) }
    time { print("Day09a: " + day09a(readInputLine("day09", 2016))) }
    time { print("Day09b: " + day09b(readInputLine("day09", 2016))) }
    time { print("Day10a: " + day10a(readInputLines("day10", 2016))) }
    time { print("Day10b: " + day10b(readInputLines("day10", 2016))) }
    time { print("Day11a: " + day11a(readInputLines("day11", 2016))) }
    // time { print("Day11b: " + day11b(readInputLines("day11", 2016))) } // Too slow
    time { print("Day12a: " + day12a(readInputLines("day12", 2016))) }
    time { print("Day12b: " + day12b(readInputLines("day12", 2016))) }
    time { print("Day13a: " + day13a(readInputLine("day13", 2016))) }
    time { print("Day13b: " + day13b(readInputLine("day13", 2016))) }
    time { print("Day14a: " + day14a(readInputLine("day14", 2016))) }
    // time { print("Day14b: " + day14b(readInputLine("day14", 2016))) } // Too slow
    time { print("Day15a: " + day15a(readInputLines("day15", 2016))) }
    time { print("Day15b: " + day15b(readInputLines("day15", 2016))) }
}
