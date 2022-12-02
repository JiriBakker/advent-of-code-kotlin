package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day17aTests {
    @Test fun testExampleInput1() {
        assertEquals(112, day17a(listOf(
            ".#.",
            "..#",
            "###")))
    }

    @Test fun testActualInput() {
        assertEquals(375, day17a(readInputLines("day17", 2020)))
    }
}

class Day17bTests {
    @Test fun testExampleInput1() {
        assertEquals(848, day17b(listOf(
            ".#.",
            "..#",
            "###")))
    }

    @Test fun testActualInput() {
        assertEquals(2192, day17b(readInputLines("day17", 2020)))
    }
}
