package v2016

private fun decompress(input: String, recursive: Boolean = false): Long {
    var sum = 0L
    var pointer = 0

    fun collectMarker(): List<Int> {
        return sequence {
            while (input[++pointer] != ')') {
                yield(input[pointer])
            }
        }.joinToString("").split('x').map(String::toInt)
    }

    do {
        if (input[pointer] == '(') {
            val (take, repeats) = collectMarker()

            sum += if (recursive) {
                val toRepeat = input.subSequence(pointer + 1, pointer + 1 + take).toString()
                val decompressedLength = decompress(toRepeat, true)
                decompressedLength * repeats
            } else {
                take.toLong() * repeats
            }
            pointer += take
        } else {
            sum++
        }
    } while (++pointer < input.length)

    return sum
}

fun day09a(input: String): Long {
    return decompress(input)
}

fun day09b(input: String): Long {
    return decompress(input, true)
}