package v2019

import v2019.days.day01.day01a
import v2019.days.day01.day01b
import v2019.days.day02.day02a
import v2019.days.day02.day02b
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
import v2019.days.day08.day08a
import v2019.days.day08.day08b
import v2019.days.day09.day09a
import v2019.days.day09.day09b
import v2019.days.day10.day10a
import v2019.days.day10.day10b
import v2019.days.day11.day11a
import v2019.days.day11.day11b
import v2019.days.day12.day12a
import v2019.days.day12.day12b
import v2019.days.day13.day13a
import v2019.days.day13.day13b
import v2019.days.day14.day14a
import v2019.days.day14.day14b
import v2019.days.day15.day15a
import v2019.days.day15.day15b
import v2019.days.day16.day16a
import v2019.days.day16.day16b
import v2019.days.day17.day17a
import v2019.days.day17.day17b_compute
import v2019.days.day18.day18a
import v2019.days.day18.day18b
import v2019.days.day19.day19a
import v2019.days.day19.day19b
import v2019.days.day20.day20a
import v2019.days.day20.day20b
import v2019.days.day21.day21a
import v2019.days.day21.day21b
import v2019.days.day22.day22a
import v2019.days.day22.day22b
import v2019.days.day23.day23a
import v2019.days.day23.day23b
import v2019.days.day24.day24a
import v2019.days.day24.day24b
import v2019.extra.infiA
import v2019.extra.infiB
import util.readInputLine
import util.readInputLines
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("InfiA: " + infiA(readInputLines("infi", 2019))) }
    time { print("InfiB: " + infiB(readInputLines("infi", 2019))) }
    time { print("Day01a: " + day01a(readInputLines("day01", 2019))) }
    time { print("Day01b: " + day01b(readInputLines("day01", 2019))) }
    time { print("Day02a: " + day02a(readInputLine("day02", 2019))) }
    time { print("Day02b: " + day02b(readInputLine("day02", 2019))) }
    time { print("Day03a: " + day03a(readInputLines("day03", 2019))) }
    time { print("Day03b: " + day03b(readInputLines("day03", 2019))) }
    time { print("Day04a: " + day04a(readInputLine("day04", 2019))) }
    time { print("Day04b: " + day04b(readInputLine("day04", 2019))) }
    time { print("Day05a: " + day05a(readInputLine("day05", 2019))) }
    time { print("Day05b: " + day05b(readInputLine("day05", 2019))) }
    time { print("Day06a: " + day06a(readInputLines("day06", 2019))) }
    time { print("Day06b: " + day06b(readInputLines("day06", 2019))) }
    time { print("Day07a: " + day07a(readInputLine("day07", 2019))) }
    time { print("Day07b: " + day07b(readInputLine("day07", 2019))) }
    time { print("Day08a: " + day08a(readInputLine("day08", 2019))) }
    time { print("Day08b:\n" + day08b(readInputLine("day08", 2019))) }
    time { print("Day09a: " + day09a(readInputLine("day09", 2019))) }
    time { print("Day09b: " + day09b(readInputLine("day09", 2019))) }
    time { print("Day10a: " + day10a(readInputLines("day10", 2019))) }
    time { print("Day10b: " + day10b(readInputLines("day10", 2019))) }
    time { print("Day11a: " + day11a(readInputLine("day11", 2019))) }
    time { print("Day11b:\n" + day11b(readInputLine("day11", 2019))) }
    time { print("Day12a: " + day12a(readInputLines("day12", 2019))) }
    time { print("Day12b: " + day12b(readInputLines("day12", 2019))) }
    time { print("Day13a: " + day13a(readInputLine("day13", 2019))) }
    time { print("Day13b: " + day13b(readInputLine("day13", 2019))) }
    time { print("Day14a: " + day14a(readInputLines("day14", 2019))) }
    time { print("Day14b: " + day14b(readInputLines("day14", 2019))) }
    time { print("Day15a: " + day15a(readInputLine("day15", 2019))) }
    time { print("Day15b: " + day15b(readInputLine("day15", 2019))) }
    time { print("Day16a: " + day16a(readInputLine("day16", 2019))) }
    time { print("Day16b: " + day16b(readInputLine("day16", 2019))) }
    time { print("Day17a: " + day17a(readInputLine("day17", 2019))) }
    time { print("Day17b: " + day17b_compute(readInputLine("day17", 2019))) }
    //time { print("Day18a: " + day18a(readInputLines("day18", 2019))) } // Too slow
    time { print("Day18b: " + day18b(readInputLines("day18", 2019))) }
    time { print("Day19a: " + day19a(readInputLine("day19", 2019))) }
    time { print("Day19b: " + day19b(readInputLine("day19", 2019))) }
    time { print("Day20a: " + day20a(readInputLines("day20", 2019))) }
    time { print("Day20b: " + day20b(readInputLines("day20", 2019))) }
    time { print("Day21a: " + day21a(readInputLine("day21", 2019))) }
    time { print("Day21b: " + day21b(readInputLine("day21", 2019))) }
    time { print("Day22a: " + day22a(readInputLines("day22", 2019))) }
    time { print("Day22b: " + day22b(readInputLines("day22", 2019))) }
    time { print("Day23a: " + day23a(readInputLine("day23", 2019))) }
    time { print("Day23b: " + day23b(readInputLine("day23", 2019))) }
    time { print("Day24a: " + day24a(readInputLines("day24", 2019))) }
    time { print("Day24b: " + day24b(readInputLines("day24", 2019))) }
}
