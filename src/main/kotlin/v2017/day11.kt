package v2017

import util.min

private fun countSteps(steps: List<String>): Sequence<Int> {
    return sequence {
        val stepCounts = mutableMapOf<String, Int>()

        steps.forEach { step ->
            stepCounts[step] = (stepCounts[step] ?: 0) + 1

            fun flatten(dir1: String, dir2: String) {
                val min = min(stepCounts[dir1] ?: 0, stepCounts[dir2] ?: 0)
                stepCounts[dir1] = (stepCounts[dir1] ?: 0) - min
                stepCounts[dir2] = (stepCounts[dir2] ?: 0) - min
            }
            flatten("sw", "ne")
            flatten("se", "nw")
            flatten("s", "n")

            fun flatten(dir1: String, dir2: String, dir3: String) {
                val min = min(stepCounts[dir1] ?: 0, stepCounts[dir2] ?: 0)
                stepCounts[dir1] = (stepCounts[dir1] ?: 0) - min
                stepCounts[dir2] = (stepCounts[dir2] ?: 0) - min
                stepCounts[dir3] = (stepCounts[dir3] ?: 0) + min
            }
            flatten("sw", "se", "s")
            flatten("sw", "n", "nw")
            flatten("se", "n", "ne")
            flatten("nw", "ne", "n")
            flatten("nw", "s", "sw")
            flatten("ne", "s", "se")

            yield(stepCounts.values.sum())
        }
    }
}

fun day11a(input: String): Int {
    return countSteps(input.split(',')).last()
}

fun day11b(input: String): Int {
    return countSteps(input.split(',')).maxOrNull()!!
}
