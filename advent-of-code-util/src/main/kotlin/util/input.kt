package util

import java.io.File

fun readInputLines(day: String): List<String> {
    return File("input/$day").let {
        if (it.exists()) it.readLines()
        else emptyList()
    }
}

fun readInputLine(day: String): String {
    return readInputLines(day).single()
}

fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

fun List<String>.splitByDoubleNewLine(): List<List<String>> {
    val groups = mutableListOf<List<String>>()
    var group = mutableListOf<String>()

    this.forEach { line ->
        if (line.isEmpty()) {
            groups.add(group)
            group = mutableListOf()
        } else {
            group.add(line)
        }
    }

    if (group.isNotEmpty()) {
        groups.add(group)
    }

    return groups
}

fun String.trimInput() =
    this.split("\n")
        .dropWhile { it.isNotEmpty() }
        .map { it.trim() }
