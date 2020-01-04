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
import v2016.days.day12.day12a
import v2016.days.day12.day12b
import v2016.days.day13.day13a
import v2016.days.day13.day13b
import v2016.days.day14.day14a
import v2016.days.day15.day15a
import v2016.days.day15.day15b
import v2016.days.day16.day16a
import v2016.days.day16.day16b
import v2016.days.day17.day17a
import v2016.days.day17.day17b
import v2016.days.day18.day18a
import v2016.days.day18.day18b
import v2016.days.day19.day19a
import v2016.days.day19.day19b
import v2016.days.day20.day20a
import v2016.days.day20.day20b
import v2016.days.day21.day21a
import v2016.days.day21.day21b
import v2016.days.day22.day22a
import v2016.days.day22.day22b
import v2016.days.day23.day23a
import v2016.days.day23.day23b
import v2016.days.day24.day24a
import v2016.days.day24.day24b
import v2016.days.day25.day25a
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
    time { print("Day16a: " + day16a(readInputLine("day16", 2016))) }
    time { print("Day16b: " + day16b(readInputLine("day16", 2016))) }
    time { print("Day17a: " + day17a(readInputLine("day17", 2016))) }
    time { print("Day17b: " + day17b(readInputLine("day17", 2016))) }
    time { print("Day18a: " + day18a(readInputLine("day18", 2016))) }
    time { print("Day18b: " + day18b(readInputLine("day18", 2016))) }
    time { print("Day19a: " + day19a(readInputLine("day19", 2016))) }
    time { print("Day19b: " + day19b(readInputLine("day19", 2016))) }
    time { print("Day20a: " + day20a(readInputLines("day20", 2016))) }
    time { print("Day20b: " + day20b(readInputLines("day20", 2016))) }
    time { print("Day21a: " + day21a(readInputLines("day21", 2016))) }
    time { print("Day21b: " + day21b(readInputLines("day21", 2016))) }
    time { print("Day22a: " + day22a(readInputLines("day22", 2016))) }
    time { print("Day22b: " + day22b(readInputLines("day22", 2016))) }
    time { print("Day23a: " + day23a(readInputLines("day23", 2016))) }
    time { print("Day23b: " + day23b(readInputLines("day23", 2016))) }
    time { print("Day24a: " + day24a(readInputLines("day24", 2016))) }
    time { print("Day24b: " + day24b(readInputLines("day24", 2016))) }
    time { print("Day25a: " + day25a(readInputLines("day25", 2016))) }
}
