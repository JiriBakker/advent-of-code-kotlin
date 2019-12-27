package util

import java.io.File

fun readInputLines(day: String, year: Int): List<String> {
    return File("input/$year/$day").readLines()
}

fun readInputLine(day: String, year: Int): String {
    return readInputLines(day, year).single()
}

fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}
