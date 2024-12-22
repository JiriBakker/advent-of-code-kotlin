fun day22a(input: List<String>, loops: Int = 2000) =
    input
        .generateSecretNumbers(loops)
        .sumOf { it.last() }

fun day22b(input: List<String>, loops: Int = 2000) =
    input
        .generateSecretNumbers(loops)
        .generateDeltaSequences()
        .generateSequenceToBananasBought()
        .maxOf { it.value.sum() }

private data class SecretNumberSequence(val nr1: Int, val nr2: Int, val nr3: Int, val nr4: Int)

private fun List<String>.generateSecretNumbers(loops: Int): List<List<Long>> {
    return this.map { line ->
        sequence {
            var nr = line.toLong()
            yield(nr)

            repeat(loops) {
                nr = (nr * 64).mixAndPrune(nr)
                nr = (nr / 32).mixAndPrune(nr)
                nr = (nr * 2048).mixAndPrune(nr)
                yield(nr)
            }
        }.toList()
    }
}

private fun Long.mixAndPrune(nr: Long) =
    (this xor nr) % 16777216L

private fun List<List<Long>>.generateDeltaSequences() =
    this.map { secretNumbers ->
        val prices = secretNumbers.map { it % 10 }
        prices.zipWithNext { a, b -> (b - a).toInt() to b }
    }

private fun List<List<Pair<Int, Long>>>.generateSequenceToBananasBought(): Map<SecretNumberSequence, List<Int>> {
    val sequenceResults = mutableMapOf<SecretNumberSequence, MutableList<Int?>>()
    fun updateSequenceResult(sequence: SecretNumberSequence, sequenceIndex: Int, result: Int) {
        if (sequence !in sequenceResults) {
            sequenceResults[sequence] = MutableList(this.size) { null }
        }
        if (sequenceResults[sequence]!![sequenceIndex] == null) {
            // Only save if not already set (only first sequence match will be bought
            sequenceResults[sequence]!![sequenceIndex] = result
        }
    }

    for (i in this.indices) {
        val deltaSequence = this[i]

        val rollingWindow = mutableListOf<Int>()
        rollingWindow.addAll(deltaSequence.take(3).map { it.first })

        for (j in 3 .. deltaSequence.lastIndex) {
            updateSequenceResult(
                SecretNumberSequence(
                    rollingWindow[0],
                    rollingWindow[1],
                    rollingWindow[2],
                    deltaSequence[j].first
                ),
                i,
                deltaSequence[j].second.toInt()
            )

            rollingWindow.removeFirst()
            rollingWindow.add(deltaSequence[j].first)
        }
    }

    return sequenceResults.mapValues { it.value.filterNotNull() }
}