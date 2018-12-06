import days.*
import extra.*
import java.io.File
import kotlin.system.measureTimeMillis

private fun readInputLines(fileName: String): List<String> {
    return File("input/$fileName").readLines()
}

private fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}

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
    time { print("Day04a: " + day04a(readInputLines("day04_sorted"))) }
    time { print("Day04b: " + day04b(readInputLines("day04_sorted"))) }
    time { print("Day05a: " + day05a(readInputLine("day05"))) }
 //   time { print("Day05b: " + day05b(readInputLine("day05"))) } // SLOW
    time { print("Day06a: " + day06a(readInputLines("day06"))) }
    time { print("Day06b: " + day06b(readInputLines("day06"))) }

}