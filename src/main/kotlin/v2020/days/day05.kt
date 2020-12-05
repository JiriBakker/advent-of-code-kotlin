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

    for (i in seatIds.indices) {
        if (seatIds[i] != seatIds[i + 1] - 1) {
            return seatIds[i] + 1
        }
    }

    error("No empty seat found")
}

