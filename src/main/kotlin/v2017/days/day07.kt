package v2017.days.day07

import util.allEqual

private class Program(val name: String) {
    var weight: Int? = null
    var below: Program? = null

    private var totalWeight: Int? = null

    fun getTotalWeight(): Int {
        if (totalWeight == null) {
            totalWeight = weight!! + programsAbove.sumBy { it.getTotalWeight() }
        }
        return totalWeight!!
    }

    private val programsAbove = mutableListOf<Program>()

    fun addAbove(above: Program) {
        programsAbove.add(above)
    }

    fun getAbove(): Iterable<Program> {
        return programsAbove
    }

    fun isBalanced(): Boolean {
        return programsAbove.map { it.getTotalWeight() }.allEqual()
    }
}

private fun parseProgramTree(input: List<String>): Map<String, Program> {
    val programs = mutableMapOf<String, Program>()

    input.forEach { line ->
        val parts = line.split(" -> ")

        val (name, weight) = parts[0].split(" ")

        val program = programs[name] ?: Program(name)
        program.weight = weight.trimStart('(').trimEnd(')').toInt()

        if (parts.size > 1) {
            parts[1].split(", ").forEach {
                val programAbove = programs[it] ?: Program(it)
                programAbove.below = program
                program.addAbove(programAbove)
                programs[it] = programAbove
            }
        }

        programs[name] = program
    }

    return programs
}

fun day07a(input: List<String>): String {
    val programs = parseProgramTree(input)
    return programs.values.first { it.below == null }.name
}

fun day07b(input: List<String>): Int {
    val programs = parseProgramTree(input)

    val rootProgram = programs.values.first { it.below == null }

    var program = rootProgram
    while (!program.isBalanced()) {
        val unbalancedAbove = program.getAbove().firstOrNull { !it.isBalanced() }
        if (unbalancedAbove == null) {
            val weightsAbove = program.getAbove().groupingBy { it.getTotalWeight() }
            val (mostFrequentWeight, _) = weightsAbove.eachCount().maxBy { it.value }!!
            val programToAdjust = program.getAbove().first { it.getTotalWeight() != mostFrequentWeight }
            return programToAdjust.weight!! + (mostFrequentWeight - programToAdjust.getTotalWeight())
        }

        program = unbalancedAbove
    }

    error("Could not find block to balance")
}
