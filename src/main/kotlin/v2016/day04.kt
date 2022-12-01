package v2016

fun day04a(input: List<String>): Int {
    return input
        .filter { room ->
            val (name, checksum) = room.trimEnd(']').split("[")

            val computedChecksum =
                name
                    .filter(Char::isLetter)
                    .groupingBy { it }
                    .eachCount()
                    .toList()
                    .sortedByDescending { it.second * 1000 - it.first.code }
                    .take(5)
                    .map { it.first }
                    .joinToString("")

            checksum == computedChecksum
        }
        .sumOf { it.trimEnd(']').split("[").first().split("-").last().toInt() }
}

fun day04b(input: List<String>): Int {
    fun decrypt(char: Char, sectorId: Int): Char = 'a' + ((char - 'a' + sectorId) % 26)
    fun decrypt(string: String, sectorId: Int): String = string.map { decrypt(it, sectorId) }.joinToString("")

    return input
        .map { room ->
            val nameAndSectorIdSegments = room.split("[").first().split("-")
            val sectorId = nameAndSectorIdSegments.last().toInt()
            val nameSegments = nameAndSectorIdSegments.dropLast(1)
            sectorId to nameSegments
        }
        .first { (sectorId, nameSegments) ->
            nameSegments.joinToString(" ") { decrypt(it, sectorId) } == "northpole object storage"
        }
        .first
}