package v2016

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(1985, day02a(listOf(
            "ULL",
            "RRDDD",
            "LURDL",
            "UUUUD"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(99332, day02a(readInputLines("day02", 2016)))
    }
}

class Day02bTests {
    @Test fun testExampleInput1() {
        assertEquals("5DB3", day02b(listOf(
            "ULL",
            "RRDDD",
            "LURDL",
            "UUUUD"
        )))
    }

    @Test fun testActualInput() {
        assertEquals("DD483", day02b(readInputLines("day02", 2016)))
    }
}
