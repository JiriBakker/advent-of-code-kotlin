fun day01a(input: List<String>) =
    input.sumOf { line ->
        val digits = line.filter { it.isDigit() }
        "${digits.first()}${digits.last()}".toInt()
    }

fun day01b(input: List<String>): Int {
    val numberWords = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

    return input.sumOf { line ->
        val numbers = line.indices.mapNotNull { i ->
            if (line[i].isDigit())
                return@mapNotNull line[i].toString()
            else
                numberWords.entries.firstOrNull { (word, _) -> line.substring(i).startsWith(word) }?.value
        }

        "${numbers.first()}${numbers.last()}".toInt()
    }
}