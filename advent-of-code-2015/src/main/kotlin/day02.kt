import util.max
import util.min
import util.sumOfLong

private data class Dimensions(val l: Int, val w: Int, val h: Int)

private fun parseDimensions(input: List<String>): List<Dimensions> {
    return input.map { line ->
        val (l, w, h) = line.split("x").map(String::toInt)
        Dimensions(l, w, h)
    }
}

private fun computeRequiredPaper(dimensions: Dimensions): Long {
    val (l, w, h) = dimensions
    return 2L * (l*w + w*h + l*h) + min(l*w, w*h, l*h)
}

private fun computeRequiredRibbon(dimensions: Dimensions): Long {
    val (l, w, h) = dimensions
    return 2L * ((l + w + h) - max(l, w, h)) + l*w*h
}

fun day02a(input: List<String>): Long {
    val dimensions = parseDimensions(input)

    return dimensions.sumOfLong(::computeRequiredPaper)
}

fun day02b(input: List<String>): Long {
    val dimensions = parseDimensions(input)

    return dimensions.sumOfLong(::computeRequiredRibbon)
}
