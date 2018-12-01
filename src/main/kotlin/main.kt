import days.*
import extra.*
import java.io.File

fun readInputLines(fileName: String): List<String> {
    return File("input/$fileName").readLines()
}

fun main(args: Array<String>) {
    println("InfiA: " + infiA(readInputLines("infi")) )
    println("InfiB: " + infiB(readInputLines("infi")) )
    println("Day01a: " + day01a(readInputLines("day01")) )
    println("Day01b: " + day01b(readInputLines("day01")) )
}