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
// import days.day11.day11b
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
    time { print("Day10a: " + day10a(readInputLines("day10"))) }
    time { print("Day10b: " + day10b(readInputLines("day10"))) }
    time { print("Day11a: " + day11a(readInputLine("day11"))) }
    // time { print("Day11b: " + day11b(readInputLine("day11"))) } // SLOW (~15s)
}
