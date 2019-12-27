package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day17.day17a
import v2015.days.day17.day17b
import util.readInputLines

class Day17aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day17a(listOf("20", "15", "10", "5", "5"), 25))
    }

    @Test fun testActualInput() {
        assertEquals(1638, day17a(readInputLines("day17", 2015)))
    }
}

class Day17bTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day17b(listOf("20", "15", "10", "5", "5"), 25))
    }

    @Test fun testActualInput() {
        assertEquals(17, day17b(readInputLines("day17", 2015)))
    }
}