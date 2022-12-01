package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day05aTests {

    @Test fun testExampleInput1() {
        assertEquals(357, day05a(listOf(
            "FBFBBFFRLR")))
    }

    @Test fun testExampleInput2() {
        assertEquals(820, day05a(listOf(
            "BFFFBBFRRR",
            "FFFBBBFRRR",
            "BBFFBBFRLL")))
    }

    @Test fun testActualInput() {
        assertEquals(991, day05a(readInputLines("day05", 2020)))
    }
}

class Day05bTests {

    @Test fun testActualInput() {
        assertEquals(534, day05b(readInputLines("day05", 2020)))
    }
}
