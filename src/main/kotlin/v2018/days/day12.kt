package v2018.days.day12

private class Tunnel(pots: Map<Int, Boolean>) {
    var pots: MutableMap<Int, Boolean> = pots.toMutableMap()
        private set

    private fun getPotSegment(index: Int): List<Boolean> {
        return sequence {
            for (offset in -2..2) {
                val potIndex = index + offset
                yield(pots.getOrDefault(potIndex, false))
            }
        }.toList()
    }

    fun applyNotes(notesTrie: NotesTrie) {
        val nextPots = pots.mapValues { false }.toMutableMap()
        val minPotIndex = pots.keys.minOrNull()!! - 2
        val maxPotIndex = pots.keys.maxOrNull()!! + 2

        for (i in minPotIndex..maxPotIndex) {
            val curPots = getPotSegment(i)

            val maybeHasPot: Boolean? = notesTrie.match(curPots, 0)
            if (maybeHasPot != null && maybeHasPot) {
                nextPots[i] = maybeHasPot
            }
        }

        this.pots = nextPots
    }
}

private class Note(val kernel: List<Boolean>, val resultsInPot: Boolean)

private class NotesTrie private constructor() {
    var withPots: NotesTrie? = null
    var withoutPots: NotesTrie? = null
    var resultsInPot: Boolean? = null

    companion object {
        fun of(notes: List<Note>): NotesTrie {
            var root = NotesTrie()
            notes.forEach { root.add(it, 0) }
            return root
        }
    }

    fun add(note: Note, index: Int) {
        when {
            index == 5 -> this.resultsInPot = note.resultsInPot
            note.kernel[index] -> {
                if (withPots == null) {
                    withPots = NotesTrie()
                }
                withPots!!.add(note, index + 1)
            }
            else -> {
                if (withoutPots == null) {
                    withoutPots = NotesTrie()
                }
                withoutPots!!.add(note, index + 1)
            }
        }
    }

    fun match(pots: List<Boolean>, index: Int): Boolean? {
        return when {
            index == 5 -> resultsInPot
            pots[index] -> withPots!!.match(pots, index + 1)
            else -> withoutPots!!.match(pots, index + 1)
        }
    }
}

private fun parse(inputLines: List<String>): Pair<Tunnel, NotesTrie> {
    val initialPots =
        Tunnel(inputLines[0].substring(15).mapIndexed { index, char -> Pair(index, char == '#') }.toMap())

    val notes = sequence {
        for (i in 2 until inputLines.size) {
            val kernel = inputLines[i].substring(0, 5).map { it == '#' }
            val potOrNot = inputLines[i][9] == '#'
            yield(Note(kernel, potOrNot))
        }
    }.toList()

    val trie = NotesTrie.of(notes)
    return Pair(initialPots, trie)
}

private fun hasConverged(pots: Map<Int, Boolean>, prevPots: Map<Int, Boolean>): Boolean {
    return pots.all { pot ->
        val prevPotValue = prevPots.getOrDefault(pot.key - 1, false)
        pot.value == prevPotValue
    }
}

private fun evolvePots(pots: Tunnel, notesTrieNotes: NotesTrie, nrOfGenerations: Long): Long {
    var i = 0L
    do {
        val prevPots = pots.pots
        pots.applyNotes(notesTrieNotes)
        i++
    } while (i < nrOfGenerations && !hasConverged(pots.pots, prevPots))

    val convergenceOffset = nrOfGenerations - i
    return pots.pots.map { if (it.value) it.key + convergenceOffset else 0L }.sum()
}

fun day12a(inputLines: List<String>, nrOfGenerations: Long = 20L): Long {
    val (pots, notesTrie) = parse(inputLines)
    return evolvePots(pots, notesTrie, nrOfGenerations)
}

fun day12b(inputLines: List<String>, nrOfGenerations: Long = 50000000000L): Long {
    val (pots, notesTrie) = parse(inputLines)
    return evolvePots(pots, notesTrie, nrOfGenerations)
}
