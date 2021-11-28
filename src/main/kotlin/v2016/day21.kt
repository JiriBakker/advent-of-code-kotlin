package v2016

import util.permute
import util.safeMod
import kotlin.math.max
import kotlin.math.min

private fun swapPos(input: String, index1: Int, index2: Int): String {
    return input.mapIndexed { index, char ->
        when (index) {
            index1 -> input[index2]
            index2 -> input[index1]
            else -> char
        }
    }.joinToString("")
}

private fun swapChar(input: String, char1: Char, char2: Char): String {
    return input.mapIndexed { _, char ->
        when (char) {
            char1 -> char2
            char2 -> char1
            else -> char
        }
    }.joinToString("")
}

private fun rotateOnLetter(input: String, letter: Char): String {
    val indexOfLetter = input.indexOf(letter)
    val delta = 1 + indexOfLetter + if (indexOfLetter >= 4) 1 else 0
    return rotate(input, delta)
}

private fun rotate(input: String, delta: Int): String {
    return input.indices.map { index ->
        input[(index - delta).safeMod(input.length)]
    }.joinToString("")
}

private fun reverse(input: String, index1: Int, index2: Int): String {
    return input.mapIndexed { index, char ->
        when (index) {
            in index1..index2 -> input[index2 - (index - index1)]
            else -> char
        }
    }.joinToString("")
}

private fun move(input: String, index1: Int, index2: Int): String {
    val minIndex = min(index1, index2)
    val maxIndex = max(index1, index2)
    val delta = index2.compareTo(index1)
    val offset = if (index1 > index2) 1 else 0

    return input.mapIndexed { index, char ->
        when (index) {
            index2 -> input[index1]
            in (minIndex + offset) until (maxIndex + offset) -> input[(index + delta).safeMod(input.length)]
            else -> char
        }
    }.joinToString("")
}

private fun applyOperations(initialString: String, operations: List<String>): String {
    return operations.fold(initialString) { string, operation ->
        val segments = operation.split(' ')
        when (segments[0]) {
            "swap" -> {
                if (segments[1] == "position") {
                    swapPos(string, segments[2].toInt(), segments[5].toInt())
                } else {
                    swapChar(string, segments[2][0], segments[5][0])
                }
            }
            "rotate" -> {
                if (segments[1] == "based") {
                    rotateOnLetter(string, segments[6][0])
                } else {
                    rotate(string, segments[2].toInt() * if (segments[1] == "left") -1 else 1)
                }
            }
            "reverse" -> reverse(string, segments[2].toInt(), segments[4].toInt())
            "move" -> move(string, segments[2].toInt(), segments[5].toInt())
            else -> error("Unknown operation: $operation")
        }
    }
}

fun day21a(input: List<String>, initialString: String = "abcdefgh"): String {
    return applyOperations(initialString, input)
}

fun day21b(input: List<String>, initialString: String = "fbgdceah"): String {
    return initialString.toList().permute().first {
        applyOperations(it.joinToString(""), input) == initialString
    }.joinToString("")
}