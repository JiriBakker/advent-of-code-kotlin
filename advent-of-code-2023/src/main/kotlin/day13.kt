import util.countLetters
import util.splitByDoubleNewLine
import util.sumOfLong
import util.transpose
import kotlin.math.abs
import kotlin.math.min
import kotlin.text.indices

fun day13a(input: List<String>) =
    input.splitByDoubleNewLine().sumOf { rows ->
        rows.findVerticalReflection(allowSmudge = false)?.let { y -> return@sumOf (y + 1) * 100 }
        rows.findHorizontalReflection(allowSmudge = false)?.let { x -> return@sumOf x + 1 }
        0
    }

fun day13b(input: List<String>) =
    input.splitByDoubleNewLine().sumOf { rows ->
        rows.findVerticalReflection(allowSmudge = true)?.let { y -> return@sumOf (y + 1) * 100 }
        rows.findHorizontalReflection(allowSmudge = true)?.let { x -> return@sumOf x + 1 }
        0
    }

private fun List<String>.findVerticalReflection(allowSmudge: Boolean) =
    (0 until this.size - 1).firstOrNull { y ->
        isReflection(this, y, allowSmudge)
    }

private fun List<String>.findHorizontalReflection(allowSmudge: Boolean): Int? {
    val columns = this.transpose().toList()
    return (0 until columns.size - 1).firstOrNull { x ->
        isReflection(columns, x, allowSmudge)
    }
}

private fun String.countDiff(other: String) =
    this.indices.count { index -> this[index] != other[index] }

private fun isReflection(pattern: List<String>, startIndex: Int, allowSmudge: Boolean): Boolean {
    var isReflection = !allowSmudge

    for (delta in 0 .. min(startIndex, pattern.size - startIndex - 2)) {
        val diffCount = pattern[startIndex - delta].countDiff(pattern[startIndex + 1 + delta])
        if (diffCount >= 2) {
            return false
        } else if (diffCount == 1) {
            if (isReflection) {
                return false
            }
            isReflection = true
        }
    }

    return isReflection
}