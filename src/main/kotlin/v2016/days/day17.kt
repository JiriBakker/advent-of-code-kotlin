package v2016.days.day17

import java.security.MessageDigest
import java.util.PriorityQueue

private fun md5Hash(string: String): String {
    return MessageDigest.getInstance("MD5").digest(string.toByteArray())
        .joinToString("")
        { String.format("%02x", it) }
}

private data class Pos(val x: Int, val y: Int)

private data class Path(val pos: Pos, val movements: String)

private fun findPathsToVault(passcode: String): Sequence<String> {
    val toVisit = PriorityQueue<Path> { a, b -> a.movements.length.compareTo(b.movements.length) }
    toVisit.add(Path(Pos(0, 0), ""))

    return sequence {
        while (toVisit.isNotEmpty()) {
            val path = toVisit.poll()

            if (path.pos.x == 3 && path.pos.y == 3) {
                yield(path.movements)
                continue
            }

            val md5 = md5Hash("$passcode${path.movements}").take(4)

            val neighbours = listOf(
                Pos(path.pos.x, path.pos.y - 1) to 'U',
                Pos(path.pos.x, path.pos.y + 1) to 'D',
                Pos(path.pos.x - 1, path.pos.y) to 'L',
                Pos(path.pos.x + 1, path.pos.y) to 'R'
            )

            neighbours
                .zip(md5.toList())
                .filter { (move, char) -> char in 'b'..'f' && move.first.x in 0..3 && move.first.y in 0..3 }
                .forEach { (move, _) -> toVisit.add(Path(move.first, path.movements + move.second)) }
        }
    }
}

fun day17a(input: String): String {
    return findPathsToVault(input).first()
}

fun day17b(input: String): Int {
    return findPathsToVault(input).map { it.length }.max()!!
}