import util.toPair
import kotlin.math.abs
import javax.imageio.ImageIO
import java.io.File
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.math.max
import kotlin.math.min

fun day09a(input: List<String>): Long {
    val redTilePositions = input.map { it.split(",").map(String::toLong).toPair() }
    val rectangleSizes = redTilePositions.indices.flatMap { i ->
        ((i + 1) until redTilePositions.size).map { j ->
            val rectangleSize = (abs(redTilePositions[i].first - redTilePositions[j].first) + 1) * (abs(redTilePositions[i].second - redTilePositions[j].second) + 1)
            (redTilePositions[i] to redTilePositions[j]) to rectangleSize
        }
    }.associate { it.first to it.second }

    return rectangleSizes.maxOf { it.value }
}

fun day09b(input: List<String>, startPos: Pair<Int, Int> = (4 to 4)): Long {
    val redTilePositions = input.map { it.split(",").map(String::toInt).toPair() }

//    val grid = mutableSetOf<Pair<Int, Int>>()
//
//    var prevPos = redTilePositions.first()
//    grid.add(prevPos)
//    for (pos in redTilePositions.drop(1).plus(prevPos)) {
//        val dx = if (prevPos.first > pos.first) -1 else if (prevPos.first < pos.first) 1 else 0
//        val dy = if (prevPos.second > pos.second) -1 else if (prevPos.second < pos.second) 1 else 0
//
//        var curPos = prevPos
//        while (curPos != pos) {
//            curPos = (curPos.first + dx) to (curPos.second + dy)
//            grid.add(curPos)
//        }
//
//        grid.add(pos)
//
//        prevPos = pos
//    }

//    println("Marked ${redTilePositions.size} red tiles and ${grid.size - redTilePositions.size} borders")

    // 94671,50270
    //94671,48487

    val rectangleSizes = redTilePositions.indices.flatMap { i ->
        ((i + 1) until redTilePositions.size).map { j ->
            val rectangleSize = (abs(redTilePositions[i].first - redTilePositions[j].first) + 1) * (abs(redTilePositions[i].second - redTilePositions[j].second) + 1)
            (redTilePositions[i] to redTilePositions[j]) to rectangleSize
        }
    }.associate { it.first to it.second }

    val possibles = rectangleSizes.filter {
        val (x1, y1) = it.key.first
        val (x2, y2) = it.key.second

        (x1 == 94671 || x2 == 94671) && abs(y2 - y1) < 20000
    }

    val zoomFactor = 0.01


    for (entry in possibles.entries.sortedByDescending { it.value }.drop(1)) {
        val minX = min(entry.key.first.first, entry.key.second.first)
        val minY = min(entry.key.first.second, entry.key.second.second)
        val maxX = max(entry.key.first.first, entry.key.second.first)
        val maxY = max(entry.key.first.second, entry.key.second.second)
        val width = maxX - minX
        val height = maxY - minY
        val image = BufferedImage((100000*zoomFactor).toInt(), (100000*zoomFactor).toInt(), BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, image.width, image.height)
        graphics.color = Color.BLUE
        graphics.fillRect((minX * zoomFactor).toInt(), (minY * zoomFactor).toInt(), (width * zoomFactor).toInt(), (height * zoomFactor).toInt())
        drawRing(graphics, redTilePositions, zoomFactor)
        ImageIO.write(image, "png", File("myimage.png"))
        val size = width * height
    }

    // 1613198862 low
    // 1613305596

//    val toVisit = mutableListOf<Pair<Int, Int>>()
//    toVisit.add(startPos)
//
//    var counter = 0L
//
//    while (toVisit.isNotEmpty()) {
//        val pos = toVisit.removeFirst()
//        if (grid.contains(pos)) continue
//
//        counter++
//        if (counter % 100000L == 0L) {
//            println("Visited $counter tiles, grid size: ${grid.size}, current tile: $pos")
//        }
//
//        grid.add(pos)
//
//        if (!grid.contains((pos.first - 1) to pos.second))
//            toVisit.add((pos.first - 1) to pos.second)
//
//        if (!grid.contains((pos.first + 1) to pos.second))
//            toVisit.add((pos.first + 1) to pos.second)
//
//        if (!grid.contains(pos.first to (pos.second - 1)))
//            toVisit.add(pos.first to (pos.second - 1))
//
//        if (!grid.contains(pos.first to (pos.second + 1)))
//            toVisit.add(pos.first to (pos.second + 1))
//    }
//
//    println("Marked ${grid.size - redTilePositions.size} green tiles")
//
//    val rectangles = redTilePositions.indices.flatMap { i ->
//        ((i + 1) until redTilePositions.size).map { j ->
//            val pos1 = redTilePositions[i]
//            val pos2 = redTilePositions[j]
//
//            val rectangleSize = (abs(redTilePositions[i].first - redTilePositions[j].first) + 1).toLong() * (abs(redTilePositions[i].second - redTilePositions[j].second) + 1).toLong()
//
//            val topLeft = (min(pos1.first, pos2.first) to min(pos1.second, pos2.second))
//            val bottomRight = (max(pos1.first, pos2.first) to max(pos1.second, pos2.second))
//
//            (topLeft to bottomRight) to rectangleSize
//        }
//    }.sortedByDescending { it.second }
//
//    println("Searching for covered rectangle...")
//
//    for ((corners, size) in rectangles) {
//        val (topLeft, bottomRight) = corners
//        if (isCovered(topLeft, bottomRight, grid)) return size
//    }
//
//    throw Exception("No valid rectangle found")
    return 0L
}

private fun drawRing(graphics: Graphics2D, redTilePositions: List<Pair<Int, Int>>, zoomFactor: Double): Unit {


    graphics.color = Color.GREEN

    var prevPos = redTilePositions.first()
//    grid.add(prevPos)
    for (pos in redTilePositions.drop(1).plus(prevPos)) {

        graphics.drawLine((prevPos.first * zoomFactor).toInt(), (prevPos.second * zoomFactor).toInt(), (pos.first * zoomFactor).toInt(), (pos.second * zoomFactor).toInt())
//        val dx = if (prevPos.first > pos.first) -1 else if (prevPos.first < pos.first) 1 else 0
//        val dy = if (prevPos.second > pos.second) -1 else if (prevPos.second < pos.second) 1 else 0

//        var curPos = prevPos
//        while (curPos != pos) {
//            curPos = (curPos.first + dx) to (curPos.second + dy)
//            grid.add(curPos)
//        }
//
//        grid.add(pos)

        prevPos = pos
    }
    graphics.color = Color.RED
    for (pos in redTilePositions) {
        graphics.drawLine((pos.first * zoomFactor).toInt(), (pos.second * zoomFactor).toInt(), (pos.first * zoomFactor).toInt(), (pos.second * zoomFactor).toInt())
    }

//    return image to graphics
}

private fun isCovered(topLeft: Pair<Int, Int>, bottomRight: Pair<Int, Int>, grid: Set<Pair<Int, Int>>): Boolean {
    for (y in topLeft.second..bottomRight.second) {
        for (x in topLeft.first.. bottomRight.first) {
            if (!grid.contains(Pair(x, y))) return false
        }
    }
    return true
}
