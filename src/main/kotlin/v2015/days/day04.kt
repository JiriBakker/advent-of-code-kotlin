package v2015.days.day04

import java.security.MessageDigest

private fun md5tartsWithNZeroes(input: String, numberOfZeroes: Int): Boolean {
    val hashBytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())

    for (i in 0 until (numberOfZeroes / 2)) {
        if (hashBytes[i] != 0.toByte()) {
            return false
        }
    }

    return numberOfZeroes % 2 == 0 || hashBytes[numberOfZeroes / 2] in 0..15
}

private fun findSuffix(input: String, numberOfZeroes: Int): String {
    var suffix = 0
    while (!md5tartsWithNZeroes("$input$suffix", numberOfZeroes)) { suffix++ }
    return suffix.toString()
}

fun day04a(input: String): String {
    return findSuffix(input, 5)
}

fun day04b(input: String): String {
    return findSuffix(input, 6)
}
