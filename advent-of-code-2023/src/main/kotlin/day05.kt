import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.first
import kotlin.text.split

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
    val seedRanges = parseSeedRanges(input.first())
    val reversedMaps = parseReverseMappings(input.drop(2))

    return findLowestReachableLocation(0 .. Long.MAX_VALUE, reversedMaps, seedRanges)!!
}

fun day05bParallel(input: List<String>): Long {
    val seedRanges = parseSeedRanges(input.first())
    val reversedMaps = parseReverseMappings(input.drop(2))

    val batchSize = 10000L
    val nrOfConcurrentBatches = 10

    Executors.newFixedThreadPool(nrOfConcurrentBatches).asCoroutineDispatcher().use { context ->
        for (location in 0L until Long.MAX_VALUE step batchSize * nrOfConcurrentBatches) {
            val minValueOrNull =
                runBlocking {
                    (0 .. nrOfConcurrentBatches)
                        .map { batchNr ->
                            async(context) {
                                val startLocation = location + batchNr * batchSize
                                val range = startLocation until startLocation + batchSize
                                findLowestReachableLocation(range, reversedMaps, seedRanges)
                            }
                        }
                        .awaitAll()
                        .filterNotNull()
                        .minOrNull()
                }

            if (minValueOrNull != null) {
                return minValueOrNull
            }
        }
    }

    throw IllegalStateException("Unable to find reachable location")
}

private fun parseSeedRanges(input: String) =
    input.split(": ")[1].split(" ").map(String::toLong).chunked(2).map { LongRange(it[0], it[0] + it[1]) }

private fun parseReverseMappings(input: List<String>): List<Map<LongRange, Long>> {
    val maps = mutableListOf<Map<LongRange, Long>>()
    var curMap = mutableMapOf<LongRange, Long>()

    for (line in input) {
        if (line.isBlank()) maps.add(curMap).also { curMap = mutableMapOf() }

        if (!(line.firstOrNull()?.isDigit() ?: false)) continue

        val (destination, sourceStart, sourceRangeLength) = line.split(" ").map(String::toLong)
        val offset = sourceStart - destination
        curMap[destination until destination + sourceRangeLength] = offset
    }
    maps.add(curMap)

    return maps.reversed()
}

private fun findLowestReachableLocation(range: LongRange, reversedMaps: List<Map<LongRange, Long>>, seedRanges: List<LongRange>): Long? {
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