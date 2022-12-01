package v2017

import util.sqrt

private class Block constructor(private val cells: BooleanArray) {
    val size = cells.size.sqrt()

    val nrOfLightsOn: Int
        get() = cells.count { it }

    fun split(): List<List<Block>> {
        val delta = if (size % 2 == 0) 2 else 3
        val output = List(size / delta) { List(size / delta) { BooleanArray(delta * delta) } }

        for (y in 0 until size) {
            for (x in 0 until size) {
                output[y / delta][x / delta][(y % delta) * delta + x % delta] = cells[y * size + x]
            }
        }

        return output.map { row -> row.map { Block(it) } }
    }

    private fun transform(indexSelector: (Int, Int) -> Int): Block {
        val output = BooleanArray(size * size)
        for (y in 0 until size) {
            for (x in 0 until size) {
                output[indexSelector(x, y)] = cells[y * size + x]
            }
        }
        return Block(output)
    }

    private fun inv(n: Int): Int = size - 1 - n
    fun flipHorizontal(): Block = transform { x, y -> inv(y) * size + x }
    fun flipVertical(): Block = transform { x, y -> y * size + inv(x) }
    fun rotate90(): Block = transform { x, y -> x * size + inv(y) }
    fun rotate180(): Block = transform { x, y -> inv(y) * size + inv(x) }
    fun rotate270(): Block = transform { x, y -> x * size + y }

    override fun equals(other: Any?): Boolean {
        return other is Block && cells.contentEquals(other.cells)
    }

    companion object {
        fun fromPattern(string: String): Block {
            return Block(string.split('/').flatMap { row -> row.map { it == '#' } }.toBooleanArray())
        }

        fun merge(outputBlocks: List<List<Block>>): Block {
            val delta = outputBlocks.first().first().size
            return Block(
                outputBlocks.flatMap { row ->
                    val booleans = mutableListOf<Boolean>()
                    for (y in 0 until delta) {
                        for (x in row.indices) {
                            for (ix in 0 until delta) {
                                booleans.add(row[x].cells[y * delta + ix])
                            }
                        }
                    }
                    booleans
                }.toBooleanArray()
            )
        }
    }
}

private class Rule(sourcePattern: String) {
    private val patterns: List<Block>

    init {
        val block = Block.fromPattern(sourcePattern)

        val rotated90 = block.rotate90()
        val rotated180 = block.rotate180()
        val rotated270 = block.rotate270()

        patterns = listOf(
            block,
            block.flipHorizontal(),
            block.flipVertical(),
            rotated90,
            rotated90.flipHorizontal(),
            rotated90.flipVertical(),
            rotated180,
            rotated180.flipHorizontal(),
            rotated180.flipVertical(),
            rotated270,
            rotated270.flipHorizontal(),
            rotated270.flipVertical()
        )
    }

    fun isMatch(toCompare: Block): Boolean = patterns.any { it == toCompare }
}

private fun parseRules(input: List<String>): Map<Rule, String> {
    return input.map { line -> line.split(" => ").let { Rule(it[0]) to it[1] } }.toMap()
}

private fun iterateGrid(rules: Map<Rule, String>, nrOfIterations: Int): Block {
    var grid = Block(booleanArrayOf(false, true, false, false, false, true, true, true, true))

    repeat(nrOfIterations) {
        grid = Block.merge(
            grid.split()
                .map { row -> row.map { block -> Block.fromPattern(rules.entries.first { it.key.isMatch(block) }.value) } }
        )
    }

    return grid
}

fun day21a(input: List<String>, nrOfIterations: Int = 5): Int {
    val rules = parseRules(input)
    return iterateGrid(rules, nrOfIterations).nrOfLightsOn
}

fun day21b(input: List<String>, nrOfIterations: Int = 18): Int {
    val rules = parseRules(input)
    return iterateGrid(rules, nrOfIterations).nrOfLightsOn
}
