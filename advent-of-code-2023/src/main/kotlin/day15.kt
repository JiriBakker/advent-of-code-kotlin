import util.sumOfLong

fun day15a(input: List<String>): Int {
    return input.first().split(',').sumOf(::hash)
}

fun day15b(input: List<String>): Long {
    val boxes = List(size = 256) { mutableListOf<Pair<String, Long>>() }

    val steps = input.first().split(',')

    steps.forEach { step ->
        val label = step.split('=')[0]
        val boxIndex = hash(label)
        if ('=' in step) {
            val focalLength = step.split('=')[1].toLong()
            val existing = boxes[boxIndex].withIndex().firstOrNull { it.value.first == label }
            if (existing != null) {
                boxes[boxIndex][existing.index] = label to focalLength
            } else {
                boxes[boxIndex].add(label to focalLength)
            }
        } else {
            boxes[boxIndex].removeIf { it.first == label }
        }
    }

    return boxes.withIndex().sumOfLong { (boxIndex, box) ->
        box.withIndex().sumOfLong { (slotIndex, step) ->
            val (_, focalLength) = step
            (boxIndex + 1) * (slotIndex + 1) * focalLength
        }
    }
}

private fun hash(step: String) =
    step
        .map { it.code }
        .fold(0) { acc, code ->
            ((acc + code) * 17).mod(256)
        }
