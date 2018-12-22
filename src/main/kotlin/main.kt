import days.day01.day01a
import days.day01.day01b
import days.day02.day02a
import days.day02.day02b
import days.day03.day03a
import days.day03.day03b
import days.day04.day04a
import days.day04.day04b
import days.day05.day05a
import days.day05.day05b
import days.day06.day06a
import days.day06.day06b
import days.day07.day07a
import days.day07.day07b
import days.day08.day08a
import days.day08.day08b
import days.day09.day09a
import days.day09.day09b
import days.day10.day10a
import days.day10.day10b
import days.day11.day11a
import days.day11.day11b
import days.day12.day12a
import days.day12.day12b
import days.day13.day13a
import days.day13.day13b
import days.day14.day14a
import days.day14.day14b
import days.day15.day15a
import days.day15.day15b
import days.day16.day16a
import days.day16.day16b
import days.day17.day17a
import days.day17.day17b
import days.day18.day18a
import days.day18.day18b
import days.day19.day19a
import days.day19.day19b
import days.day20.day20a
import days.day20.day20b
import days.day21.day21a
import days.day21.day21b
import days.day22.day22a
import days.day22.day22b
// import days.day23.day23a
// import days.day23.day23b
// import days.day24.day24a
// import days.day24.day24b
// import days.day25.day25a
// import days.day25.day25b
import extra.infi.infiA
import extra.infi.infiB
import kotlin.system.measureTimeMillis

private fun time(func: () -> Unit) {
    val millis = measureTimeMillis { func() }
    println(" (took $millis ms)")
}

fun main(args: Array<String>) {
    time { print("InfiA: " + infiA(readInputLines("infi"))) }
    time { print("InfiB: " + infiB(readInputLines("infi"))) }
    time { print("Day01a: " + day01a(readInputLines("day01"))) }
    time { print("Day01b: " + day01b(readInputLines("day01"))) }
    time { print("Day02a: " + day02a(readInputLines("day02"))) }
    time { print("Day02b: " + day02b(readInputLines("day02"))) }
    time { print("Day03a: " + day03a(readInputLines("day03"))) }
    time { print("Day03b: " + day03b(readInputLines("day03"))) }
    time { print("Day04a: " + day04a(readInputLines("day04"))) }
    time { print("Day04b: " + day04b(readInputLines("day04"))) }
    time { print("Day05a: " + day05a(readInputLine("day05"))) }
    time { print("Day05b: " + day05b(readInputLine("day05"))) }
    time { print("Day06a: " + day06a(readInputLines("day06"))) }
    time { print("Day06b: " + day06b(readInputLines("day06"))) }
    time { print("Day07a: " + day07a(readInputLines("day07"))) }
    time { print("Day07b: " + day07b(readInputLines("day07"))) }
    time { print("Day08a: " + day08a(readInputLine("day08"))) }
    time { print("Day08b: " + day08b(readInputLine("day08"))) }
    time { print("Day09a: " + day09a(readInputLine("day09"))) }
    time { print("Day09b: " + day09b(readInputLine("day09"))) }
    time { print("Day10a:\n" + day10a(readInputLines("day10"))) }
    time { print("Day10b: " + day10b(readInputLines("day10"))) }
    time { print("Day11a: " + day11a(readInputLine("day11"))) }
    time { print("Day11b: " + day11b(readInputLine("day11"))) }
    time { print("Day12a: " + day12a(readInputLines("day12"))) }
    time { print("Day12b: " + day12b(readInputLines("day12"))) }
    time { print("Day13a: " + day13a(readInputLines("day13"))) }
    time { print("Day13b: " + day13b(readInputLines("day13"))) }
    time { print("Day14a: " + day14a(readInputLine("day14"))) }
    time { print("Day14b: " + day14b(readInputLine("day14"))) }
    time { print("Day15a: " + day15a(readInputLines("day15"))) }
    time { print("Day15b: " + day15b(readInputLines("day15"))) }
    time { print("Day16a: " + day16a(readInputLines("day16"))) }
    time { print("Day16b: " + day16b(readInputLines("day16"))) }
    time { print("Day17a: " + day17a(readInputLines("day17"))) }
    time { print("Day17b: " + day17b(readInputLines("day17"))) }
    time { print("Day18a: " + day18a(readInputLines("day18"))) }
    time { print("Day18b: " + day18b(readInputLines("day18"))) }
    time { print("Day19a: " + day19a(readInputLines("day19"))) }
    time { print("Day19b: " + day19b(readInputLines("day19"))) }
    time { print("Day20a: " + day20a(readInputLine("day20"))) }
    time { print("Day20b: " + day20b(readInputLine("day20"))) }
    time { print("Day21a: " + day21a(readInputLines("day21"))) }
    time { print("Day21b: " + day21b()) }
    time { print("Day22a: " + day22a(readInputLines("day22"))) }
    time { print("Day22b: " + day22b(readInputLines("day22"))) }
//    time { print("Day23a: " + day23a(readInputLines("day23"))) }
//    time { print("Day23b: " + day23b(readInputLines("day23"))) }
//    time { print("Day24a: " + day24a(readInputLines("day24"))) }
//    time { print("Day24b: " + day24b(readInputLines("day24"))) }
//    time { print("Day25a: " + day25a(readInputLines("day25"))) }
//    time { print("Day25b: " + day25b(readInputLines("day25"))) }
}
