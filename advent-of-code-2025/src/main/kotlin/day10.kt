import util.pow

import com.microsoft.z3.*

fun day10a(input: List<String>): Int {
    return input.sumOf { line ->
        val desiredState = line.drop(1).split(']').first().map { if (it == '#') '1' else '0' }.joinToString("")
        val desiredStateNr = desiredState.toInt(2)
        val nrOfLights = desiredState.length
        val maxNr = 2.pow(nrOfLights) - 1

        val buttons = line.split("] ").drop(1).first().split(" {").first().split(' ').map { it.drop(1).dropLast(1).split(',').map(String::toInt)}

        val buttonNrs = buttons.map { button ->
            val bits = MutableList(nrOfLights) { '0' }
            for (index in button) {
                bits[index] = '1'
            }
            bits.joinToString("").toInt(2)
        }

        val transitions = (0 .. maxNr).associateWith { nr ->
            buttonNrs.associateWith { (nr xor it) }
        }

        val toVisit = mutableListOf<Pair<Int, Int>>()
        toVisit.add(Pair(desiredStateNr, 0))

        val visited = mutableSetOf<Int>()

        while (toVisit.isNotEmpty()) {
            val (curNr, pressCount) = toVisit.removeFirst()

            if (!visited.add(curNr)) continue

            for (transition in transitions[curNr]!!.entries) {
                if (transition.value == 0) {
                    return@sumOf pressCount + 1
                }
                toVisit.add(Pair(transition.value, pressCount + 1))
            }
        }

        throw Exception("No solution found")
    }
}

fun day10b(input: List<String>): Int {
    return input.sumOf { line ->
        val buttons = line.split("] ").drop(1).first().split(" {").first().split(' ').map { it.drop(1).dropLast(1).split(',').map(String::toInt)}
        val targetJoltageLevels = line.split("{").drop(1).first().dropLast(1).split(',').map(String::toInt)

        Context().use { ctx ->
            val solver = ctx.mkOptimize()

            val buttonEquations = buttons.indices
                .map { ctx.mkIntConst("button#$it") }
                .onEach { button -> solver.Add(ctx.mkGe(button, ctx.mkInt(0))) }
                .toTypedArray()

            targetJoltageLevels.forEachIndexed { joltageIndex, targetJoltageLevel ->
                val increments = buttons
                    .withIndex()
                    .filter { (_, buttonIndex) -> joltageIndex in buttonIndex }
                    .map { buttonEquations[it.index] }
                    .toTypedArray()

                solver.Add(
                    ctx.mkEq(
                        ctx.mkAdd(*increments),
                        ctx.mkInt(targetJoltageLevel)
                    )
                )
            }

            val pressCount = ctx.mkIntConst("pressCount")
            solver.Add(ctx.mkEq(pressCount, ctx.mkAdd(*buttonEquations)))
            solver.MkMinimize(pressCount)

            if (solver.Check() != Status.SATISFIABLE) throw Exception("No solution found for $line")
            solver.model.evaluate(pressCount, false).let { result -> result as IntNum }.int
        }
    }
}