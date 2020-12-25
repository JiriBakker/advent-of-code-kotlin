package v2020.days.day25

private fun loopSize(publicKey: Long, initialSubjectNr: Long): Int {
    var loopSize = 0
    var value = 1L
    while (value != publicKey) {
        value = (value * initialSubjectNr) % 20201227L
        loopSize++
    }
    return loopSize
}

private fun computeEncryptionKey(loopSize: Int, publicKey: Long): Long {
    var value = 1L
    repeat(loopSize) {
        value = (value * publicKey) % 20201227L
    }
    return value
}

fun day25a(input: List<String>): Long {
    val (cardPublicKey, doorPublicKey) = input.map { it.toLong() }

    val cardLoopSize = loopSize(cardPublicKey, 7)

    return computeEncryptionKey(cardLoopSize, doorPublicKey)
}
