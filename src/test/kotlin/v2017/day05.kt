package v2017.days.day05

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day05aTests {
    @Test fun testExampleInput1() {
        assertEquals(5, day05a(listOf(
            "0",
            "3",
            "0",
            "1",
            "-3")))
    }

    @Test fun testActualInput() {
        assertEquals(315613, day05a(readInputLines("day05", 2017)))
    }
}

class Day05bTests {
    @Test fun testExampleInput1() {
        assertEquals(10, day05b(listOf(
            "0",
            "3",
            "0",
            "1",
            "-3")))
    }

    @Test fun testActualInput() {
        assertEquals(22570529, day05b(readInputLines("day05", 2017)))
    }
}
