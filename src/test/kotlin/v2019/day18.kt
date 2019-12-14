package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day18.day18a
import v2019.days.day18.day18b
import v2019.util.readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day18a(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day18b(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day18b(readInputLines("day18")))
    }
}