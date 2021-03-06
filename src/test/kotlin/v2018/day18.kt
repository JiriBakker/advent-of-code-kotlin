package v2018.days.day18

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(1147,
            day18a(
                listOf(
                    ".#.#...|#.",
                    ".....#|##|",
                    ".|..|...#.",
                    "..|#.....#",
                    "#.#|||#|#|",
                    "...#.||...",
                    ".|....|...",
                    "||...#|.#|",
                    "|.||||..|.",
                    "...#.|..|."
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(574200,
            day18a(readInputLines("day18", 2018))
        )
    }
}

class Day18bTests {
    @Test fun testActualInput() {
        assertEquals(211653,
            day18b(readInputLines("day18", 2018))
        )
    }
}