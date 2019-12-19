package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day18.day18a
import v2015.days.day18.day18b
import v2015.util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day18a(listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."), 4))
    }

    @Test fun testActualInput() {
        assertEquals(814, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(17, day18b(listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####.."), 5))
    }

    @Test fun testActualInput() {
        assertEquals(924, day18b(readInputLines("day18")))
    }
}