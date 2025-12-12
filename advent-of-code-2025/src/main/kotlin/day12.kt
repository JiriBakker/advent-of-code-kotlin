fun day12a(input: List<String>): Int {
    val areaPerBlock = input.take(30).chunked(5).map { block ->
        block.sumOf { line -> line.count { it == '#' }}
    }

    val regions = input.drop(30).map { line ->
        val dimensions = line.split(':').first().split('x').map(String::toInt)
        val blockCounts = line.split(": ").drop(1).first().split(' ').map(String::toInt)
        dimensions to blockCounts
    }

    return regions.count { (dimensions, blockCounts) ->
        val (width, height) = dimensions
        val area = width * height

        val blocksArea = blockCounts.withIndex().sumOf { it.value * areaPerBlock[it.index] }

        area >= blocksArea
    }
}