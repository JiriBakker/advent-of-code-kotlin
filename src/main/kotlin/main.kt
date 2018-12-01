import days.*
import java.io.File

fun readInputLines(fileName: String): List<String> {
    return File("input/$fileName").readLines()
}

fun main(args: Array<String>) {
    println("Day01a: " + day01a(readInputLines("day01")) )
    println("Day01b: " + day01b(readInputLines("day01")) )
}