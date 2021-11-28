package v2016

import java.security.MessageDigest

private fun md5Hash(input: String): ByteArray {
    return MessageDigest.getInstance("MD5").digest(input.toByteArray())
}

private fun md5tartsWithNZeroes(input: String, numberOfZeroes: Int): Boolean {
    val hashBytes = md5Hash(input)
    for (i in 0 until (numberOfZeroes / 2)) {
        if (hashBytes[i] != 0.toByte()) {
            return false
        }
    }
    return numberOfZeroes % 2 == 0 || hashBytes[numberOfZeroes / 2] in 0..15
}

private fun generateMatchingMd5Hashes(input: String): Sequence<String> {
    return sequence {
        var suffix = 0
        while (true) {
            while (!md5tartsWithNZeroes("$input$suffix", 5)) {
                suffix++
            }
            yield(
                md5Hash("$input$suffix")
                    .joinToString("")
                    { String.format("%02x", it) }
            )
            suffix++
        }
    }
}

fun day05a(input: String): String {
    return generateMatchingMd5Hashes(input)
        .take(8)
        .map { it[5] }
        .joinToString("")
}

fun day05b(input: String): String {
    val chars = mutableMapOf<Int, Char>()
    generateMatchingMd5Hashes(input)
        .takeWhile { hash ->
            val position = hash[5]
            val char = hash[6]
            if (position.isDigit()) {
                val positionInt = position.toString().toInt()
                if (positionInt in 0..7 && !chars.containsKey(positionInt)) {
                    chars[positionInt] = char
                }
            }
            chars.size < 8
        }
        .toList()

    return chars.toList().sortedBy { it.first }.map { it.second }.joinToString("")
}