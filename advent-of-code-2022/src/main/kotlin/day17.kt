import Day17.rocks
import util.max

fun day17a(input: List<String>): Int {
    val jetPattern = sequence {
        while (true) {
            input[0].forEach { yield(it) }
        }
    }.iterator()

    fun print(str: String) {
        //kotlin.io.print(str)
    }
    fun println(str: String) {
        //kotlin.io.println(str)
    }

    val tower = mutableMapOf<Int, MutableSet<Int>>()

    fun MutableMap<Int, MutableSet<Int>>.height() =
        this.keys.maxOrNull() ?: 0

    repeat(2022) {
        val rock = rocks[it % rocks.size]
        var rockX = 2
        var rockY = tower.height() + 3 + rock.size

        fun printTower() {
            //return
            (max(rockY, tower.height()) downTo 1).forEach { y ->
                print("|")
                (0 until 7).forEach { x ->
                    if (tower[y]?.contains(x) == true) print("#")
                    else if (rock.getOrNull(rockY - y)?.getOrNull(x - rockX) == '#') print("@")
                    else print(".")
                }
                println("|")
            }
            println("+-------+")
        }

        fun canMoveDown(): Boolean {
            rock.forEachIndexed { yOffset, rockLine ->
                val y = rockY - yOffset - 1
                if (y == 0) {
                    return false
                }
                rockLine.forEachIndexed { xOffset, char ->
                    if (char == '#') {
                        val x = rockX + xOffset
                        if (tower[y]?.contains(x) == true) {
                            return false
                        }
                    }
                }
            }
            return true
        }

        fun applyJet() {
            val jetDirection = jetPattern.next()

            print("Applying jet $jetDirection ")

            if (jetDirection == '<') {
                if (rockX <= 0) {
                    println(" Blocked!")
                    return
                }

                rock.forEachIndexed { yOffset, rockLine ->
                    rockLine.forEachIndexed { xOffset, char ->
                        if (char == '#' && tower[rockY - yOffset]?.contains(rockX + xOffset - 1) == true) {
                            println(" Blocked!")
                            return
                        }
                    }
                }

                rockX--
            } else {
                val rockWidth = rock[0].length
                if (rockX + rockWidth >= 7) {
                    println(" Blocked!")
                    return
                }
                rock.forEachIndexed { yOffset, rockLine ->
                    rockLine.forEachIndexed { xOffset, char ->
                        if (char == '#' && tower[rockY - yOffset]?.contains(rockX + xOffset + 1) == true) {
                            println(" Blocked!")
                            return
                        }
                    }
                }

                rockX++
            }

            println(" Success!")
        }

        printTower()
        while (true) {
            applyJet()
            printTower()
            if (!canMoveDown()) {
                break
            }
            rockY--
            println("Moved rock")
            printTower()
        }

        rock.forEachIndexed { yOffset, rockLine ->
            val y = rockY - yOffset
            rockLine.forEachIndexed { xOffset, char ->
                if (char == '#') {
                    val x = rockX + xOffset
                    tower.getOrPut(y) { mutableSetOf() }.add(x)
                }
            }
        }

        println("Tower height: ${tower.height()}")
    }

    return tower.height()
}

fun day17b(input: List<String>): Long {
     val jetPattern = sequence {
        while (true) {
            input[0].forEach { yield(it) }
        }
    }.iterator()

    fun print(str: String) {
        //kotlin.io.print(str)
    }
    fun println(str: String) {
        //kotlin.io.println(str)
    }

    val tower = mutableMapOf<Int, MutableSet<Int>>()

    fun MutableMap<Int, MutableSet<Int>>.height() =
        this.keys.maxOrNull() ?: 0

    val heightsReached = mutableListOf<Int>()

    repeat(6000) {
        val rock = rocks[it % rocks.size]
        var rockX = 2
        var rockY = tower.height() + 3 + rock.size

        fun printTower() {
            //return
            (max(rockY, tower.height()) downTo 1).forEach { y ->
                print("|")
                (0 until 7).forEach { x ->
                    if (tower[y]?.contains(x) == true) print("#")
                    else if (rock.getOrNull(rockY - y)?.getOrNull(x - rockX) == '#') print("@")
                    else print(".")
                }
                println("|")
            }
            println("+-------+")
        }

        fun canMoveDown(): Boolean {
            rock.forEachIndexed { yOffset, rockLine ->
                val y = rockY - yOffset - 1
                if (y == 0) {
                    return false
                }
                rockLine.forEachIndexed { xOffset, char ->
                    if (char == '#') {
                        val x = rockX + xOffset
                        if (tower[y]?.contains(x) == true) {
                            return false
                        }
                    }
                }
            }
            return true
        }

        fun applyJet() {
            val jetDirection = jetPattern.next()

            print("Applying jet $jetDirection ")

            if (jetDirection == '<') {
                if (rockX <= 0) {
                    println(" Blocked!")
                    return
                }

                rock.forEachIndexed { yOffset, rockLine ->
                    rockLine.forEachIndexed { xOffset, char ->
                        if (char == '#' && tower[rockY - yOffset]?.contains(rockX + xOffset - 1) == true) {
                            println(" Blocked!")
                            return
                        }
                    }
                }

                rockX--
            } else {
                val rockWidth = rock[0].length
                if (rockX + rockWidth >= 7) {
                    println(" Blocked!")
                    return
                }
                rock.forEachIndexed { yOffset, rockLine ->
                    rockLine.forEachIndexed { xOffset, char ->
                        if (char == '#' && tower[rockY - yOffset]?.contains(rockX + xOffset + 1) == true) {
                            println(" Blocked!")
                            return
                        }
                    }
                }

                rockX++
            }

            println(" Success!")
        }

        printTower()
        while (true) {
            applyJet()
            printTower()
            if (!canMoveDown()) {
                break
            }
            rockY--
            println("Moved rock")
            printTower()
        }

        rock.forEachIndexed { yOffset, rockLine ->
            val y = rockY - yOffset
            rockLine.forEachIndexed { xOffset, char ->
                if (char == '#') {
                    val x = rockX + xOffset
                    tower.getOrPut(y) { mutableSetOf() }.add(x)
                }
            }
        }

        println("Tower height: ${tower.height()}")

        heightsReached.add(tower.height())

        //kotlin.io.println("Rock $it -> ${tower.height()}")
    }

    fun Map<Int, Set<Int>>.rowToString(y: Int) =
        (0 until 7).joinToString { if (this[y]?.contains(it) == true) "#" else "." }


    kotlin.io.println("Finding matches")

    val maxRockNr = 1000000000000L

    var y = 1
    while (true) {
        val curRow = tower.rowToString(y)
        var cycleSize = 1
        do {
            val firstMatch = tower.rowToString(y + cycleSize)
            if (curRow == firstMatch) {
                var cycleNr = 2
                while (y + (cycleSize * cycleNr) < tower.height()) {
                    val match = tower.rowToString(y + (cycleSize * cycleNr))
                    if (curRow != match) {
                        break
                    }
                    cycleNr++
                }

                if (y + (cycleSize * cycleNr) >= tower.height()) {
                    var yOffset = 1
                    while (tower.rowToString(y + yOffset) == tower.rowToString(y + cycleSize + yOffset) && yOffset < cycleSize) {
                        val y2 = y + yOffset
                        val rockNr1 = heightsReached.indexOfFirst { it >= y2 }
                        val rockNr2 = heightsReached.indexOfFirst { it >= y2 + cycleSize }
                        if (heightsReached[rockNr1] == y2 && heightsReached[rockNr2] == y2 + cycleSize) {
                            val rockCycleSize = rockNr2 - rockNr1
                            if ((maxRockNr - rockNr1) % rockCycleSize == 0L) {
                                return ((maxRockNr - rockNr1) / rockCycleSize) * cycleSize + y2 - 1
                            }
                        }
                        yOffset++
                    }
                }
            }
            cycleSize++
        } while (cycleSize < 3000)
        y++
    }

    //((1000000000000 - 15)/35)*53+26-1
    //((1000000000000 - 1600)/1725)*2734+2552-1
    // 1584927536247


    return tower.height().toLong()
}

object Day17 {
    val rocks = listOf(
        listOf(
            "####"
        ),

        listOf(
            ".#.",
            "###",
            ".#."
        ),

        listOf(
            "..#",
            "..#",
            "###"
        ),

        listOf(
            "#",
            "#",
            "#",
            "#"
        ),

        listOf(
            "##",
            "##"
        )
    )
}