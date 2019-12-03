package v2019.days.day03

import kotlin.math.abs

private class Wire {
    private val positions = mutableMapOf<Int, MutableMap<Int, Int>>()

    fun addPos(x: Int, y: Int, step: Int) {
        if (!positions.containsKey(x)) {
            positions[x] = mutableMapOf()
        }
        positions[x]!![y] = step
    }

    fun getStepForPos(x: Int, y: Int): Int? {
        return if (positions.containsKey(x)) positions[x]!![y] else null
    }

    fun hasPos(x: Int, y: Int): Boolean {
        return getStepForPos(x, y) != null
    }

    fun getPositions(): Map<Int, Map<Int, Int>> {
        return positions
    }
}

private fun parseWire(input: String): Wire {
    val segments = input.split(",")
    var x = 0
    var y = 0
    var step = 0

    val wire = Wire()

    segments.forEach { segment ->
        val direction = segment.first()
        val length = segment.drop(1).toInt()

        fun applyStep() {
            when (direction) {
                'U' -> { y++ }
                'D' -> { y-- }
                'L' -> { x-- }
                'R' -> { x++ }
                else -> throw Exception("Unknown direction $direction")
            }
            step++
        }

        for (i in 0 until length) {
            applyStep()
            wire.addPos(x, y, step)
        }
    }

    return wire
}

private fun parseWires(input: List<String>): Pair<Wire, Wire> {
    return Pair(parseWire(input[0]), parseWire(input[1]))
}

fun day03a(input: List<String>): Int {
    val (wire1, wire2) = parseWires(input)

    return wire1.getPositions()
        .flatMap { (x, row) ->
            row.filter { y -> wire2.hasPos(x, y.key) }
                .map { y -> Pair(x, y.key) }
        }
        .map { intersection -> abs(intersection.first) + abs(intersection.second) }
        .min()
        ?: throw Exception("No intersections found")
}

fun day03b(input: List<String>): Int {
    val (wire1, wire2) = parseWires(input)

    return wire1.getPositions()
        .flatMap { (x, row) ->
            row.map { (y, step1) ->
                val step2 =  wire2.getStepForPos(x, y)
                if (step2 != null) step1 + step2 else null
            }
                .filterNotNull()
        }
        .min()
        ?: throw Exception("No intersections found")
}

