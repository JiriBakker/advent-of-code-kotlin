package v2017

import util.readInputLine
import util.readInputLines
import v2017.days.day01.day01a
import v2017.days.day01.day01b
import v2017.days.day02.day02a
import v2017.days.day02.day02b
import v2017.days.day03.day03a
import v2017.days.day03.day03b
import v2017.days.day04.day04a
import v2017.days.day04.day04b
import v2017.days.day05.day05a
import v2017.days.day05.day05b
import v2017.days.day06.day06a
import v2017.days.day06.day06b
import v2017.days.day07.day07a
import v2017.days.day07.day07b
import v2017.days.day08.day08a
import v2017.days.day08.day08b
import v2017.days.day09.day09a
import v2017.days.day09.day09b
import v2017.days.day10.day10a
import v2017.days.day10.day10b
import v2017.days.day11.day11a
import v2017.days.day11.day11b
import v2017.days.day12.day12a
import v2017.days.day12.day12b
import v2017.days.day13.day13a
import v2017.days.day13.day13b
import v2017.days.day14.day14a
import v2017.days.day14.day14b
import v2017.days.day15.day15a
import v2017.days.day15.day15b
import v2017.days.day16.day16a
import v2017.days.day16.day16b
import v2017.days.day17.day17a
import v2017.days.day17.day17b
import v2017.days.day18.day18a
import v2017.days.day18.day18b
import v2017.days.day19.day19a
import v2017.days.day19.day19b
import v2017.days.day20.day20a
import v2017.days.day20.day20b
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
    time { print("Day03a: " + day03a(readInputLine("day03", 2017))) }
    time { print("Day03b: " + day03b(readInputLine("day03", 2017))) }
    time { print("Day04a: " + day04a(readInputLines("day04", 2017))) }
    time { print("Day04b: " + day04b(readInputLines("day04", 2017))) }
    time { print("Day05a: " + day05a(readInputLines("day05", 2017))) }
    time { print("Day05b: " + day05b(readInputLines("day05", 2017))) }
    time { print("Day06a: " + day06a(readInputLine("day06", 2017))) }
    time { print("Day06b: " + day06b(readInputLine("day06", 2017))) }
    time { print("Day07a: " + day07a(readInputLines("day07", 2017))) }
    time { print("Day07b: " + day07b(readInputLines("day07", 2017))) }
    time { print("Day08a: " + day08a(readInputLines("day08", 2017))) }
    time { print("Day08b: " + day08b(readInputLines("day08", 2017))) }
    time { print("Day09a: " + day09a(readInputLine("day09", 2017))) }
    time { print("Day09b: " + day09b(readInputLine("day09", 2017))) }
    time { print("Day10a: " + day10a(readInputLine("day10", 2017))) }
    time { print("Day10b: " + day10b(readInputLine("day10", 2017))) }
    time { print("Day11a: " + day11a(readInputLine("day11", 2017))) }
    time { print("Day11b: " + day11b(readInputLine("day11", 2017))) }
    time { print("Day12a: " + day12a(readInputLines("day12", 2017))) }
    time { print("Day12b: " + day12b(readInputLines("day12", 2017))) }
    time { print("Day13a: " + day13a(readInputLines("day13", 2017))) }
    time { print("Day13b: " + day13b(readInputLines("day13", 2017))) }
    time { print("Day14a: " + day14a(readInputLine("day14", 2017))) }
    time { print("Day14b: " + day14b(readInputLine("day14", 2017))) }
    time { print("Day15a: " + day15a(readInputLines("day15", 2017))) }
    time { print("Day15b: " + day15b(readInputLines("day15", 2017))) }
    time { print("Day16a: " + day16a(readInputLine("day16", 2017))) }
    time { print("Day16b: " + day16b(readInputLine("day16", 2017))) }
    time { print("Day17a: " + day17a(readInputLine("day17", 2017))) }
    time { print("Day17b: " + day17b(readInputLine("day17", 2017))) }
    time { print("Day18a: " + day18a(readInputLines("day18", 2017))) }
    time { print("Day18b: " + day18b(readInputLines("day18", 2017))) }
    time { print("Day19a: " + day19a(readInputLines("day19", 2017))) }
    time { print("Day19b: " + day19b(readInputLines("day19", 2017))) }
    time { print("Day20a: " + day20a(readInputLines("day20", 2017))) }
    time { print("Day20b: " + day20b(readInputLines("day20", 2017))) }
}
