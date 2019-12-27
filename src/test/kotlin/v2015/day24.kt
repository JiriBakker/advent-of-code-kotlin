package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day24.day24a
import v2015.days.day24.day24b
import util.readInputLines

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(99, day24a(listOf("1", "2", "3", "4", "5", "7", "8", "9", "10", "11")))
    }

    @Test fun testActualInput() {
        assertEquals(10439961859, day24a(readInputLines("day24", 2015)))
    }
}

class Day24bTests {
    @Test fun testExampleInput1() {
        assertEquals(44, day24b(listOf("1", "2", "3", "4", "5", "7", "8", "9", "10", "11")))
    }

    @Test fun testActualInput() {
        assertEquals(72050269, day24b(readInputLines("day24", 2015)))
    }
}