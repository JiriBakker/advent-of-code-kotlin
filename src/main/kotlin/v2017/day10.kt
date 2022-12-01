package v2017

import util.safeMod

private fun hash(elements: IntArray, lengths: List<Int>, startIndex: Int = 0, skipSize: Int = 0): Pair<Int, Int> {
    fun wrap(index: Int) = index.safeMod(elements.size)

    var curStartIndex = startIndex
    var curSkipSize = skipSize
    lengths.forEach { length ->
        for (i in 0 until length / 2) {
            val index1 = wrap(curStartIndex + i)
            val index2 = wrap(curStartIndex + length - 1 - i)
            val oldValue = elements[index2]
            elements[index2] = elements[index1]
            elements[index1] = oldValue
        }
        curStartIndex = wrap(curStartIndex + length + curSkipSize)
        curSkipSize++
    }

    return curStartIndex to curSkipSize
}

fun knotHash(string: String, nrOfElements: Int = 256): String {
    val lengths = string.map(Char::code).plus(listOf(17, 31, 73, 47, 23))

    val elements = (0 until nrOfElements).toList().toIntArray()

    (0 until 64).fold(0 to 0) { (startIndex, skipSize), _ ->
        hash(elements, lengths, startIndex, skipSize)
    }

    return elements
        .toList()
        .chunked(16)
        .joinToString("") {
            it.reduce(Int::xor)
                .toString(16)
                .padStart(2, '0')
        }
}

fun day10a(input: String, nrOfElements: Int = 256): Int {
    val lengths = input.split(',').map(String::toInt)

    val elements = (0 until nrOfElements).toList().toIntArray()

    hash(elements, lengths)

    return elements[0] * elements[1]
}

fun day10b(input: String, nrOfElements: Int = 256): String {
    return knotHash(input, nrOfElements)
}
