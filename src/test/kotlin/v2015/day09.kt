package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day09.day09a
import v2015.days.day09.day09b
import v2015.util.readInputLines

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(605, day09a(listOf(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141")))
    }

    @Test fun testActualInput() {
        assertEquals(141, day09a(readInputLines("day09")))
    }
}

class Day09bTests {
    @Test fun testExampleInput1() {
        assertEquals(982, day09b(listOf(
            "London to Dublin = 464",
            "London to Belfast = 518",
            "Dublin to Belfast = 141")))
    }

    @Test fun testActualInput() {
        assertEquals(736, day09b(readInputLines("day09")))
    }
}

