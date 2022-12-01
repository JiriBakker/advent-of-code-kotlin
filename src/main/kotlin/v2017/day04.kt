package v2017

fun day04a(input: List<String>): Int {
    return input.count { words ->
        words
            .split(' ')
            .groupingBy { it }
            .eachCount()
            .none { it.value > 1 }
    }
}

fun day04b(input: List<String>): Int {
    return input.count { words ->
        words
            .split(' ')
            .map { it.toCharArray().sorted().joinToString("") }
            .groupingBy { it }
            .eachCount()
            .none { it.value > 1 }
    }
}
