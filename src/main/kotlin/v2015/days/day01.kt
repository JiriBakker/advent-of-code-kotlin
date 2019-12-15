package v2015.days.day01

private fun toFloorSequence(input: String): Sequence<Long> {
    return sequence {
        input.fold(0L) { floor, it ->
            yield(floor)
            floor + if (it == '(') 1L else -1L
        }
    }
}

fun day01a(input: String): Long {
    return toFloorSequence(input).last()
}

fun day01b(input: String): Int {
    return toFloorSequence(input).takeWhile { it != -1L }.count()
}
