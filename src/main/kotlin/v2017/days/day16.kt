package v2017.days.day16

import util.safeMod

private fun dance(moves: List<String>, nrOfPrograms: Int): Sequence<CharArray> {
    return sequence {
        var programs = (0 until nrOfPrograms).map { 'a' + it }.toCharArray()

        fun spin(delta: Int) {
            programs = programs.indices.map { index ->
                programs[(index - delta).safeMod(nrOfPrograms)]
            }.toCharArray()
        }

        fun swapPos(pos: Pair<Int, Int>) {
            val temp = programs[pos.first]
            programs[pos.first] = programs[pos.second]
            programs[pos.second] = temp
        }

        fun swapProgram(pos: Pair<Char, Char>) {
            programs.forEachIndexed { index, char ->
                when (char) {
                    pos.first -> programs[index] = pos.second
                    pos.second -> programs[index] = pos.first
                    else -> Unit
                }
            }
        }

        while (true) {
            moves.forEach { move ->
                when (move[0]) {
                    's' -> spin(move.drop(1).toInt())
                    'x' -> swapPos(move.drop(1).split('/').map(String::toInt).let { it[0] to it[1] })
                    'p' -> swapProgram(move.drop(1).split('/').let { it[0].first() to it[1].first() })
                    else -> error("Unknown move $move")
                }
            }

            yield(programs)
        }
    }
}

fun day16a(input: String, nrOfPrograms: Int = 16): String {
    val moves = input.split(',')
    return dance(moves, nrOfPrograms).first().joinToString("")
}

fun day16b(input: String, nrOfPrograms: Int = 16): String {
    val moves = input.split(',')
    val visited = mutableMapOf<String, Int>()
    val (indexOfRepeat, repeatedChars) =
        dance(moves, nrOfPrograms)
            .withIndex()
            .first { (index, chars) -> visited.putIfAbsent(chars.joinToString(""), index) != null }

    val previousIndex = visited[repeatedChars.joinToString("")]!!
    val cycleLength = indexOfRepeat - previousIndex

    val cycleAtEnd = ((1000000000 - previousIndex) % cycleLength) - 1

    return visited.filter { it.value == cycleAtEnd }.keys.first()
}
