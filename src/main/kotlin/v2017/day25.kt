package v2017

private class StateSection(val writeValue: Long, val delta: Int, val nextState: String)

private class State(val id: String, val when0: StateSection, val when1: StateSection)

private fun parseInput(input: List<String>): Triple<State, Int, Map<String, State>> {
    val startStateId = input[0].trimEnd('.').split(' ').last()
    val nrOfSteps = input[1].split(' ')[5].toInt()

    fun parseStateSection(lines: List<List<String>>): StateSection {
        val writeValue = lines[0].last().toLong()
        val delta = if (lines[1].last() == "right") 1 else -1
        val nextState = lines[2].last()
        return StateSection(writeValue, delta, nextState)
    }

    val states = input
        .drop(3)
        .map { it.trimEnd('.', ':').split(' ') }
        .chunked(10)
        .associate { lines ->
            val id = lines[0].last()
            val when0 = parseStateSection(lines.drop(2).take(3))
            val when1 = parseStateSection(lines.drop(6).take(3))
            id to State(id, when0, when1)
        }

    return Triple(states[startStateId]!!, nrOfSteps, states)
}

fun day25a(input: List<String>): Int {
    val (startState, nrOfSteps, states) = parseInput(input)

    val tapeIndicesOfOnes = mutableSetOf<Long>()
    var state = startState
    var index = 0L

    repeat(nrOfSteps) {
        val stateSectionToApply = if (tapeIndicesOfOnes.contains(index)) state.when1 else state.when0
        if (stateSectionToApply.writeValue == 1L) {
            tapeIndicesOfOnes.add(index)
        } else {
            tapeIndicesOfOnes.remove(index)
        }
        index += stateSectionToApply.delta
        state = states[stateSectionToApply.nextState]!!
    }

    return tapeIndicesOfOnes.size
}