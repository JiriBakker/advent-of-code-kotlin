package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day19.day19a
import v2015.days.day19.day19b
import v2015.util.readInputLines

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day19a(listOf(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOH")))
    }

    @Test fun testExampleInput2() {
        assertEquals(7, day19a(listOf(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOHOHO")))
    }

    @Test fun testActualInput() {
        assertEquals(509, day19a(readInputLines("day19")))
    }
}

class Day19bTests {
    @Test fun testExampleInput1() {
        assertEquals(17, day19b(listOf(
            ".#.#.#",
            "...##.",
            "#....#",
            "..#...",
            "#.#..#",
            "####..")))
    }

    @Test fun testActualInput() {
        assertEquals(924, day19b(readInputLines("day19")))
    }
}