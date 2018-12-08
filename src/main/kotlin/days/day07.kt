package days

import java.util.PriorityQueue

private class Step(val letter: Char) : Comparable<Step> {
    var dependsOn: MutableSet<Step> = mutableSetOf()
        private set

    var isDependedOnBy: MutableSet<Step> = mutableSetOf()
        private set

    override fun compareTo(other: Step): Int {
        return this.letter.compareTo(other.letter)
    }
}

private fun buildDependencyGraph(instructionLines: List<String>): List<Step> {
    val steps: MutableMap<Char, Step> = mutableMapOf()
    instructionLines.forEach { instruction ->
        val instructionParts = instruction.split(' ')
        val firstStepLetter = instructionParts[1][0]
        val secondStepLetter = instructionParts[7][0]

        val firstStep = steps.getOrPut(firstStepLetter) { Step(firstStepLetter) }
        val secondStep = steps.getOrPut(secondStepLetter) { Step(secondStepLetter) }

        secondStep.dependsOn.add(firstStep)
        firstStep.isDependedOnBy.add(secondStep)
    }

    return steps.values.toList()
}

private fun doIt(instructionLines: List<String>, nrOfWorkers: Int, baseDuration: Int): Pair<Int, List<Char>> {
    val steps = buildDependencyGraph(instructionLines)

    val workers: MutableMap<Char, Int> = mutableMapOf()

    val stepsFinished = mutableListOf<Char>()
    val stepsQueued = mutableListOf<Char>()
    val stepQueue = PriorityQueue<Char>()

    fun queueAvailableSteps(predicate: (Step) -> Boolean) {
        val stepsToQueue = steps.filter { predicate(it) }
        stepQueue.addAll(stepsToQueue.map { it.letter })
        stepsQueued.addAll(stepsToQueue.map { it.letter })
    }
    queueAvailableSteps { step -> step.dependsOn.isEmpty() }

    var second = 0

    while (stepsFinished.size < steps.size) {
        workers.forEach { workers[it.key] = it.value - 1 }
        val finishedSteps = workers.filter { it.value == 0 }.keys.sorted()
        for (step in finishedSteps) {
            workers.remove(step)
            stepsFinished.add(step)
        }

        if (finishedSteps.isNotEmpty()) {
            queueAvailableSteps { step -> !stepsQueued.contains(step.letter) && step.dependsOn.all { stepsFinished.contains(it.letter) } }
        }

        while (workers.size < nrOfWorkers && !stepQueue.isEmpty()) {
            val step = stepQueue.poll()
            workers[step] = step.minus('A') + 1 + baseDuration
            stepsQueued.add(step)
        }

        second++
    }

    return Pair(second, stepsFinished)
}

fun day07a(instructionLines: List<String>): String {
    val (_, stepsFinished) = doIt(instructionLines, 1, 0)
    return String(stepsFinished.toCharArray())
}

fun day07b(instructionLines: List<String>, nrOfWorkers: Int = 5, baseDuration: Int = 60): Int {
    val (second, _) = doIt(instructionLines, nrOfWorkers, baseDuration)
    return second - 1
}
