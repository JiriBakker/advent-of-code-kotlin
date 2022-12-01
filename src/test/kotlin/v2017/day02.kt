package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(18, day02a(listOf(
            "5 1 9 5",
            "7 5 3",
            "2 4 6 8"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(54426, day02a(readInputLines("day02", 2017)))
    }
}

class Day02bTests {
    @Test fun testExampleInput1() {
        assertEquals(9, day02b(listOf(
            "5 9 2 8",
            "9 4 7 3",
            "3 8 6 5"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(333, day02b(readInputLines("day02", 2017)))
    }
}
