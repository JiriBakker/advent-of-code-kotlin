package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
