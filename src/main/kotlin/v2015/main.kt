package v2015

import util.readInputLine
import util.readInputLines
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main() {
    time { print("Day01a: " + day01a(readInputLine("day01", 2015))) }
    time { print("Day01b: " + day01b(readInputLine("day01", 2015))) }
    time { print("Day02a: " + day02a(readInputLines("day02", 2015))) }
    time { print("Day02b: " + day02b(readInputLines("day02", 2015))) }
    time { print("Day03a: " + day03a(readInputLine("day03", 2015))) }
    time { print("Day03b: " + day03b(readInputLine("day03", 2015))) }
    time { print("Day04a: " + day04a(readInputLine("day04", 2015))) }
    time { print("Day04b: " + day04b(readInputLine("day04", 2015))) }
    time { print("Day05a: " + day05a(readInputLines("day05", 2015))) }
    time { print("Day05b: " + day05b(readInputLines("day05", 2015))) }
    time { print("Day06a: " + day06a(readInputLines("day06", 2015))) }
    time { print("Day06b: " + day06b(readInputLines("day06", 2015))) }
    time { print("Day07a: " + day07a(readInputLines("day07", 2015))) }
    time { print("Day07b: " + day07b(readInputLines("day07", 2015))) }
    time { print("Day08a: " + day08a(readInputLines("day08", 2015))) }
    time { print("Day08b: " + day08b(readInputLines("day08", 2015))) }
    time { print("Day09a: " + day09a(readInputLines("day09", 2015))) }
    time { print("Day09b: " + day09b(readInputLines("day09", 2015))) }
    time { print("Day10a: " + day10a(readInputLine("day10", 2015))) }
    time { print("Day10b: " + day10b(readInputLine("day10", 2015))) }
    time { print("Day11a: " + day11a(readInputLine("day11", 2015))) }
    time { print("Day11b: " + day11b(readInputLine("day11", 2015))) }
    time { print("Day12a: " + day12a(readInputLine("day12", 2015))) }
    time { print("Day12b: " + day12b(readInputLine("day12", 2015))) }
    time { print("Day13a: " + day13a(readInputLines("day13", 2015))) }
    time { print("Day13b: " + day13b(readInputLines("day13", 2015))) }
    time { print("Day14a: " + day14a(readInputLines("day14", 2015))) }
    time { print("Day14b: " + day14b(readInputLines("day14", 2015))) }
    time { print("Day15a: " + day15a(readInputLines("day15", 2015))) }
    time { print("Day15b: " + day15b(readInputLines("day15", 2015))) }
    time { print("Day16a: " + day16a(readInputLines("day16", 2015))) }
    time { print("Day16b: " + day16b(readInputLines("day16", 2015))) }
    time { print("Day17a: " + day17a(readInputLines("day17", 2015))) }
    time { print("Day17b: " + day17b(readInputLines("day17", 2015))) }
    time { print("Day18a: " + day18a(readInputLines("day18", 2015))) }
    time { print("Day18b: " + day18b(readInputLines("day18", 2015))) }
    time { print("Day19a: " + day19a(readInputLines("day19", 2015))) }
    time { print("Day19b: " + day19b(readInputLines("day19", 2015))) }
    // time { print("Day20a: " + day20a(readInputLine("day20", 2015))) } // Too slow
    // time { print("Day20b: " + day20b(readInputLine("day20", 2015))) } // Too slow
    time { print("Day21a: " + day21a(readInputLines("day21", 2015))) }
    time { print("Day21b: " + day21b(readInputLines("day21", 2015))) }
    time { print("Day22a: " + day22a(readInputLines("day22", 2015))) }
    time { print("Day22b: " + day22b(readInputLines("day22", 2015))) }
    time { print("Day23a: " + day23a(readInputLines("day23", 2015))) }
    time { print("Day23b: " + day23b(readInputLines("day23", 2015))) }
    time { print("Day24a: " + day24a(readInputLines("day24", 2015))) }
    time { print("Day24b: " + day24b(readInputLines("day24", 2015))) }
    time { print("Day25a: " + day25a(readInputLine("day25", 2015))) }
}
