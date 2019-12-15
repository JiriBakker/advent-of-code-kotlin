package v2015.util

import java.io.File

fun readInputLines(fileName: String): List<String> {
    return File("input/2015/$fileName").readLines()
}

fun readInputLine(fileName: String): String {
    return readInputLines(fileName).single()
}

fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}
