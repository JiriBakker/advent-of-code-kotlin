fun day05a(input: List<String>): Long {
    val seeds = input.first().split(": ")[1].split(" ").map(String::toLong)

    val maps = mutableListOf<Map<LongRange, Long>>()
    var curMap = mutableMapOf<LongRange, Long>()

    for (line in input.drop(2)) {
        if (line.isBlank()) maps.add(curMap).also { curMap = mutableMapOf() }

        if (!(line.firstOrNull()?.isDigit() ?: false)) continue

        val (destination, sourceStart, sourceRangeLength) = line.split(" ").map(String::toLong)
        val offset = destination - sourceStart
        curMap[sourceStart until sourceStart + sourceRangeLength] = offset
    }
    maps.add(curMap)

    return seeds.minOf { seed ->
        maps.fold(seed) { cur, curMap ->
            curMap.firstNotNullOfOrNull { (range, offset) ->
                if (cur in range) cur + offset
                else null
            } ?: cur
        }
    }
}

fun day05b(input: List<String>): Long {
    val seedRanges = input.first().split(": ")[1].split(" ").map(String::toLong).chunked(2).map { LongRange(it[0], it[0] + it[1]) }

    val maps = mutableListOf<Map<LongRange, Long>>()
    var curMap = mutableMapOf<LongRange, Long>()

    for (line in input.drop(2)) {
        if (line.isBlank()) maps.add(curMap).also { curMap = mutableMapOf() }

        if (!(line.firstOrNull()?.isDigit() ?: false)) continue

        val (destination, sourceStart, sourceRangeLength) = line.split(" ").map(String::toLong)
        val offset = sourceStart - destination
        curMap[destination until destination + sourceRangeLength] = offset
    }
    maps.add(curMap)

    val reversedMaps = maps.reversed()

    var location = 0L
    while (true) {
        val seed = reversedMaps.fold(location) { cur, curMap ->
            curMap.firstNotNullOfOrNull { (range, offset) ->
                if (cur in range) cur + offset
                else null
            } ?: cur
        }

        if (seedRanges.any { seed in it }) {
            return location
        }

        location++
    }
}