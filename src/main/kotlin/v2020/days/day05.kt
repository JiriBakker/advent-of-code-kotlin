package v2020.days.day05

private fun String.toSeatId(): Int {
    return Integer.parseInt(
            this.replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1')
        , 2)
}

fun day05a(input: List<String>): Int {
    return input.map(String::toSeatId).max()!!
}

fun day05b(input: List<String>): Int {
    val seatIds = input.map(String::toSeatId).sorted()

    return seatIds
        .zipWithNext()
        .first { (cur, next) -> cur != next - 1 }
        .let { it.first + 1 }
}

