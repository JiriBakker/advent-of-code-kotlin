package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day11aTests {
    @Test fun testExampleInput1() {
        assertEquals(37, day11a(listOf(
            "L.LL.LL.LL",
            "LLLLLLL.LL",
            "L.L.L..L..",
            "LLLL.LL.LL",
            "L.LL.LL.LL",
            "L.LLLLL.LL",
            "..L.L.....",
            "LLLLLLLLLL",
            "L.LLLLLL.L",
            "L.LLLLL.LL")))
    }

    @Test fun testActualInput() {
        assertEquals(2427, day11a(readInputLines("day11", 2020)))
    }
}

class Day11bTests {
    @Test fun testExampleInput1() {
        assertEquals(26, day11b(listOf(
            "L.LL.LL.LL",
            "LLLLLLL.LL",
            "L.L.L..L..",
            "LLLL.LL.LL",
            "L.LL.LL.LL",
            "L.LLLLL.LL",
            "..L.L.....",
            "LLLLLLLLLL",
            "L.LLLLLL.L",
            "L.LLLLL.LL")))
    }

    @Test fun testActualInput() {
        assertEquals(2199, day11b(readInputLines("day11", 2020)))
    }
}
