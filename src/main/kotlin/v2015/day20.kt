package v2015

import util.DoNotAutoExecute

private fun countDivisors(nr: Long): Long {
    var count = 0L
    for (i in nr / 2 downTo 2) {
        count += if (nr % i == 0L) 1 else 0
    }
    return count
}

fun nrOfPresentsElfLeavesAtHouse(houseNr: Long, elfNr: Long, maxHouses: Long): Long {
    if (houseNr % elfNr != 0L || houseNr / elfNr > maxHouses) {
        return 0L
    }
    return elfNr
}

private fun totalNrOfPresentsAtHouse(houseNr: Long, maxHouses: Long): Long {
    if (houseNr < 2) {
        return houseNr
    }

    var divisor = 2L
    var max = houseNr / divisor
    while (houseNr % max != 0L) {
        divisor++
        max = houseNr / divisor
    }

    var presents = houseNr
    for (elfNr in 1..max) {
        presents += nrOfPresentsElfLeavesAtHouse(houseNr, elfNr, maxHouses)
    }
    return presents
}

private fun findLowestHouseNr(target: Long, maxHouses: Long = Long.MAX_VALUE): Long {
     val divisorCounts = sequence {
        for (i in target / 10 until Long.MAX_VALUE) {
            yield(i to countDivisors(i))
        }
    }

    val minDivisorsToCheck = target / 150000
    divisorCounts.forEach {
        if (it.second >= minDivisorsToCheck) {
            val presents = totalNrOfPresentsAtHouse(it.first, maxHouses)
            if (presents >= target) {
                return it.first
            }
        }
    }

    error("Unable to find house with more than $target presents")
}

@DoNotAutoExecute
fun day20a(input: String): Long {
    val target = input.toLong() / 10

    return findLowestHouseNr(target)
}

@DoNotAutoExecute
fun day20b(input: String): Long {
    val target = input.toLong() / 11

    return findLowestHouseNr(target, 50)
}
