package days.day12

private class Pots(pots: Map<Int, Boolean>) {
    var pots: MutableMap<Int, Boolean>
        private set

    init {
        this.pots = pots.toMutableMap()
    }

    fun applyNotesTrie(notesTrie: SubTrie) {
        val nextPots = pots.map { Pair(it.key, false) }.toMap().toMutableMap()

        for (i in (pots.keys.min()!! - 2)..(pots.keys.max()!! + 2)) {
            val curPots = sequence {
                for (offset in -2..2) {
                    val potIndex = i + offset
                    yield(pots.getOrDefault(potIndex, false))
                }
            }.toList()

            val maybeHasPot = notesTrie.match(curPots, 0)

            if (maybeHasPot != null && maybeHasPot) {
                // println("Match at $i -> ${String(note.kernel.map {if (it) '#' else '.'}.toCharArray())}")
                if ((!nextPots.containsKey(i) && maybeHasPot) || nextPots.containsKey(i)) {
                    nextPots[i] = maybeHasPot
                }
            }
        }

        this.pots = nextPots
    }

    fun applyNotes(notes: List<Note>) {
        val nextPots = pots.map { Pair(it.key, false) }.toMap().toMutableMap()

        notes.forEach { note ->
            for (i in (pots.keys.min()!! - 2)..(pots.keys.max()!! + 2)) {
                var isKernelMatch = true
                for (offset in -2..2) {
                    val potIndex = i + offset
                    val hasPot = pots.getOrDefault(potIndex, false)

                    if (note.kernel[offset + 2] != hasPot) {
                        isKernelMatch = false
                        break
                    }
                }
                if (isKernelMatch) {
                    // println("Match at $i -> ${String(note.kernel.map {if (it) '#' else '.'}.toCharArray())}")
                    if ((!nextPots.containsKey(i) && note.resultsInPot) || nextPots.containsKey(i)) {
                        nextPots[i] = note.resultsInPot
                    }
                }
            }
        }

        this.pots = nextPots
    }
}

private class Note(val kernel: List<Boolean>, val resultsInPot: Boolean)

private class SubTrie {
    var withPots: SubTrie? = null
    var withoutPots: SubTrie? = null
    var resultsInPot: Boolean? = null

    fun add(note: Note, index: Int) {
        when {
            index == 5 -> this.resultsInPot = note.resultsInPot
            note.kernel[index] -> {
                if (withPots == null) {
                    withPots = SubTrie()
                }
                withPots!!.add(note, index + 1)
            }
            else -> {
                if (withoutPots == null) {
                    withoutPots = SubTrie()
                }
                withoutPots!!.add(note, index + 1)
            }
        }
    }

    fun match(pots: List<Boolean>, index: Int): Boolean? {
        if (index == 5) {
            return resultsInPot
        }
        return if (pots[index]) {
            if (withPots == null) {
                return null
            }
            withPots!!.match(pots, index + 1)
        } else {
            if (withoutPots == null) {
                return null
            }
            withoutPots!!.match(pots, index + 1)
        }
    }
}

private fun notesToTrie(notes: List<Note>): SubTrie {
    var root = SubTrie()
    notes.forEach { root.add(it, 0) }
    return root
}

private fun parse(inputLines: List<String>): Pair<Pots, SubTrie> {
    val initialPots = Pots(inputLines[0].substring(15).mapIndexed { index, char -> Pair(index, char == '#') }.toMap())

    val notes = sequence {
        for (i in 2 until inputLines.size) {
            val kernel = inputLines[i].substring(0, 5).map { it == '#' }
            val potOrNot = inputLines[i][9] == '#'
            yield(Note(kernel, potOrNot))
        }
    }.toList()

    val trie = notesToTrie(notes)

    return Pair(initialPots, trie)
}

private fun iterate(pots: Pots, trie: SubTrie, nrOfGenerations: Long): Long {
    var prevPots = pots.pots.toMap()

    var i = 0L
    while (i < nrOfGenerations) {
        pots.applyNotesTrie(trie)

        if (pots.pots.all { it.value == prevPots.getOrDefault(it.key - 1, false) }) {
            break
        }
        prevPots = pots.pots

        i++
    }

    val a = nrOfGenerations - i - 1
    return pots.pots.map { if (it.value) it.key + a else 0L }.sum()
}

fun day12a(inputLines: List<String>, nrOfGenerations: Long = 20L): Long {
    val (pots, trie) = parse(inputLines)

    repeat(nrOfGenerations.toInt()) {
        pots.applyNotesTrie(trie)
    }

    return pots.pots.map { if (it.value) it.key.toLong() else 0L }.sum()
}

fun day12b(inputLines: List<String>, nrOfGenerations: Long = 50000000000L): Long {
    val (pots, trie) = parse(inputLines)
    var prevPots = pots.pots.toMap()

    var i = 0L
    while (i < nrOfGenerations) {
        pots.applyNotesTrie(trie)

        if (pots.pots.all { it.value == prevPots.getOrDefault(it.key - 1, false) }) {
            break
        }
        prevPots = pots.pots

        i++
    }

    val a = nrOfGenerations - i - 1
    return pots.pots.map { if (it.value) it.key + a else 0L }.sum()
}
