import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

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

fun day05b_async(input: List<String>): Long {
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

    val batchSize = 10000
    val nrOfConcurrentBatches = 10

    fun generateBatches(): Sequence<LongRange> {
        return sequence {
            var location = 0L
            while (true) {
                yield(location until location + batchSize)
                location += batchSize
            }
        }
    }

    Executors.newFixedThreadPool(nrOfConcurrentBatches).asCoroutineDispatcher().use { context ->
        val batchGenerator = generateBatches().chunked(10)

        for (batches in batchGenerator) {
            val result = runBlocking {
                batches
                    .asFlow()
                    .map { range -> async(context) { checkLocationRange(range, reversedMaps, seedRanges) } }
                    .toList()
                    .awaitAll()
            }

            val minValue = result.filterNotNull().minOrNull()

            if (minValue != null) {
                return minValue
            }
        }
    }

    throw Exception("Invalid state reached")
}

private fun checkLocationRange(range: LongRange, reversedMaps: List<Map<LongRange, Long>>, seedRanges: List<LongRange>): Long? {
    for (location in range) {
        val seed = reversedMaps.fold(location) { cur, curMap ->
            curMap.firstNotNullOfOrNull { (range, offset) ->
                if (cur in range) cur + offset
                else null
            } ?: cur
        }

        if (seedRanges.any { seed in it }) {
            return location
        }
    }

    return null
}