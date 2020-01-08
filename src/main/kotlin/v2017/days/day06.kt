package v2017.days.day06

import util.safeMod

private fun parseRegisters(input: String): IntArray = input.split(' ', '\t').map(String::toInt).toIntArray()

private fun findRepeatedDistribution(registers: IntArray): Triple<IntArray, Int, Int> {
    val visited = mutableMapOf<String, Int>()
    var cycle = 0
    while (true) {
        val hash = registers.joinToString("-")
        if (visited.containsKey(hash)) {
            return Triple(registers, cycle, visited[hash]!!)
        }
        visited[hash] = cycle

        val nrOfBlocksToRedistribute = registers.max()!!
        val registerIndexToRedistribute = registers.indexOfFirst { it == nrOfBlocksToRedistribute }

        registers[registerIndexToRedistribute] = 0

        var index = registerIndexToRedistribute
        repeat(nrOfBlocksToRedistribute) {
            index = (index + 1).safeMod(registers.size)
            registers[index]++
        }

        cycle++
    }
}

fun day06a(input: String): Int {
    val registers = parseRegisters(input)
    return findRepeatedDistribution(registers).second
}

fun day06b(input: String): Int {
    val registers = parseRegisters(input)
    return findRepeatedDistribution(registers).let { it.second - it.third }
}
