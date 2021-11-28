package v2015

private fun toFloorSequence(input: String): Sequence<Int> {
    return sequence {
        yield(
            input.fold(0) { floor, it ->
                yield(floor)
                floor + if (it == '(') 1 else -1
            }
        )
    }
}

fun day01a(input: String): Int {
    return toFloorSequence(input).last()
}

fun day01b(input: String): Int {
    return toFloorSequence(input).takeWhile { it != -1 }.count()
}
