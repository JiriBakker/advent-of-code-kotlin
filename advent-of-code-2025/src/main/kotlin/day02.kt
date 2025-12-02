import util.toPair

fun day02a(input: String): Long {
    val ranges = parseInput(input)

    var invalidSum = 0L
    for (range in ranges) {
        val (start, end) = range.map(String::toLong)
        for (nr in start..end) {
            val nrString = nr.toString()
            if (nrString.length % 2 == 0) {
                val part1 = nrString.take(nrString.length / 2)
                val part2 = nrString.drop(nrString.length / 2)
                if (part1 == part2) {
                    invalidSum += nrString.toLong()
                }
            }
        }
    }
    return invalidSum
}

fun day02b(input: String): Long {
    val ranges = parseInput(input)

    var invalidSum = 0L
    for (range in ranges) {
        println("--- Range $range")
        val (start, end) = range.map(String::toLong)
        for (nr in start..end) {
            val nrString = nr.toString()
            if (nrString.length % 7 == 0) {
                val part1 = nrString.take(nrString.length / 7)
                val part2 = nrString.drop(nrString.length / 7).take(nrString.length / 7)
                val part3 = nrString.drop((nrString.length / 7) * 2).take(nrString.length / 7)
                val part4 = nrString.drop((nrString.length / 7) * 3).take(nrString.length / 7)
                val part5 = nrString.drop((nrString.length / 7) * 4).take(nrString.length / 7)
                val part6 = nrString.drop((nrString.length / 7) * 5).take(nrString.length / 7)
                val part7 = nrString.drop((nrString.length / 7) * 6).take(nrString.length / 7)

                if (part1 == part2 && part1 == part3 && part1 == part4 && part1 == part5 && part1 == part6 && part1 == part7) {
                    invalidSum += nrString.toLong()
                    println("Found $nrString")
                    continue
                }
            }
            if (nrString.length % 6 == 0) {
                val part1 = nrString.take(nrString.length / 6)
                val part2 = nrString.drop(nrString.length / 6).take(nrString.length / 6)
                val part3 = nrString.drop((nrString.length / 6) * 2).take(nrString.length / 6)
                val part4 = nrString.drop((nrString.length / 6) * 3).take(nrString.length / 6)
                val part5 = nrString.drop((nrString.length / 6) * 4).take(nrString.length / 6)
                val part6 = nrString.drop((nrString.length / 6) * 5).take(nrString.length / 6)

                if (part1 == part2 && part1 == part3 && part1 == part4 && part1 == part5 && part1 == part6) {
                    invalidSum += nrString.toLong()
                    println("Found $nrString")
                    continue
                }
            }
            if (nrString.length % 5 == 0) {
                val part1 = nrString.take(nrString.length / 5)
                val part2 = nrString.drop(nrString.length / 5).take(nrString.length / 5)
                val part3 = nrString.drop((nrString.length / 5) * 2).take(nrString.length / 5)
                val part4 = nrString.drop((nrString.length / 5) * 3).take(nrString.length / 5)
                val part5 = nrString.drop((nrString.length / 5) * 4).take(nrString.length / 5)

                if (part1 == part2 && part1 == part3 && part1 == part4 && part1 == part5) {
                    invalidSum += nrString.toLong()
                    println("Found $nrString")
                    continue
                }
            }
            if (nrString.length % 4 == 0) {
                val part1 = nrString.take(nrString.length / 4)
                val part2 = nrString.drop(nrString.length / 4).take(nrString.length / 4)
                val part3 = nrString.drop((nrString.length / 4) * 2).take(nrString.length / 4)
                val part4 = nrString.drop((nrString.length / 4) * 3).take(nrString.length / 4)

                if (part1 == part2 && part1 == part3 && part1 == part4) {
                    invalidSum += nrString.toLong()
                    println("Found $nrString")
                    continue
                }
            }
            if (nrString.length % 3 == 0) {
                val part1 = nrString.take(nrString.length / 3)
                val part2 = nrString.drop(nrString.length / 3).take(nrString.length / 3)
                val part3 = nrString.drop((nrString.length / 3) * 2)
                if (part1 == part2 && part1 == part3) {
                    invalidSum += nrString.toLong()
                    println("Found $nrString")
                    continue
                }
            }
            if (nrString.length % 2 == 0) {
                val part1 = nrString.take(nrString.length / 2)
                val part2 = nrString.drop(nrString.length / 2)
                if (part1 == part2) {
                    invalidSum += nrString.toLong()
                    println("Found $nrString")
                    continue
                }
            }
        }
    }
    return invalidSum
}

private fun parseInput(input: String) =
    input
        .split(",")
        .map { it.split("-") }
