private fun expandDisk(source: BooleanArray, diskSize: Int): BooleanArray {
    var output = source
    do {
        val cur = BooleanArray(output.size * 2 + 1)
        output.forEachIndexed { index, value ->
            cur[index] = value
            cur[cur.size - 1 - index] = !value
        }
        cur[output.size] = false
        output = cur
    } while (output.size < diskSize)
    return output.take(diskSize).toBooleanArray()
}

private fun computeCheckSum(disk: BooleanArray): BooleanArray {
    var cur = disk
    do {
        val checksum = BooleanArray(cur.size / 2)
        for (i in checksum.indices) {
            checksum[i] = cur[i * 2] == cur[i * 2 + 1]
        }
        cur = checksum
    } while (checksum.size % 2 == 0)
    return cur
}

private fun BooleanArray.toDigits(): String = this.map { if (it) '1' else '0' }.joinToString("")

fun day16a(input: String, diskSize: Int = 272): String {
    val disk = input.map { it == '1' }.toBooleanArray()
    val expandedDisk = expandDisk(disk, diskSize)
    return computeCheckSum(expandedDisk).toDigits()
}

fun day16b(input: String, diskSize: Int = 35651584): String {
    val disk = input.map { it == '1' }.toBooleanArray()
    val expandedDisk = expandDisk(disk, diskSize)
    return computeCheckSum(expandedDisk).toDigits()
}