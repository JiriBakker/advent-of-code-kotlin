import util.sumOfLong

fun day09a(input: List<String>): Long {
    val disk = input.parseDisk()

    var reverseCursor = disk.lastIndex

    for (cursor in disk.indices) {
        if (cursor == reverseCursor) {
            break
        }

        if (disk[cursor] != -1) {
            continue
        }

        while (disk[reverseCursor] == -1) {
            reverseCursor--
        }

        disk[cursor] = disk[reverseCursor]
        disk[reverseCursor] = -1
    }

    return disk.computeChecksum()
}

fun day09b(input: List<String>): Long {
    val disk = input.parseDisk()
    val fileLengths = input.collectFileLengths()

    var reverseCursor = disk.lastIndex

    while (reverseCursor > 0) {
        if (disk[reverseCursor] == -1) {
            reverseCursor--
            continue
        }

        val fileIndex = disk[reverseCursor]
        val fileLength = fileLengths[fileIndex]!!

        val gapIndex = disk.findGapOfSize(fileLength, reverseCursor)
        if (gapIndex == null) {
            reverseCursor -= fileLength
            continue
        }

        for (i in 0 until fileLength) {
            disk[gapIndex + i] = fileIndex
            disk[reverseCursor] = -1
            reverseCursor--
        }
    }

    return disk.computeChecksum()
}

private fun List<String>.parseDisk(): IntArray {
    val diskMap = this[0].map { it.digitToInt() }

    val disk = IntArray(diskMap.sum()) { -1 }

    var cursor = 0
    var fileIndex = 0
    var writingFile = true

    diskMap
        .forEach { digit ->
            repeat(digit) {
                disk[cursor] = if (writingFile) fileIndex else -1
                cursor++
            }

            if (writingFile) {
                fileIndex++
            }
            writingFile = !writingFile
        }

    return disk
}

private fun List<String>.collectFileLengths() =
    this[0]
        .filterIndexed { index, _ ->
            index % 2 == 0
        }
        .mapIndexed { fileIndex, fileLength ->
            fileIndex to fileLength.digitToInt()
        }
        .toMap()


private fun IntArray.findGapOfSize(size: Int, maxIndex: Int): Int? {
    var cursor = 0

    while (cursor < maxIndex - size) {
        while (this[cursor] != -1) {
            cursor++
        }

        var gapLength = 0
        while (gapLength < size && cursor < maxIndex) {
            while (this[cursor + gapLength] == -1) {
                gapLength++
            }
            if (gapLength < size) {
                cursor += gapLength
                gapLength = 0
                while (this[cursor] != -1) {
                    cursor++
                }
            }
        }

        if (gapLength >= size) {
            return cursor
        }
    }

    return null
}

private fun IntArray.computeChecksum() =
    this
        .withIndex()
        .sumOfLong { (index, value) -> if (value != -1) index.toLong() * value else 0 }
