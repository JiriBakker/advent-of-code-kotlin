package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day15.day15a
import v2019.days.day15.day15b
import v2019.util.readInputLines

class Day15aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day15a(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day15a(readInputLines("day15")))
    }
}

class Day15bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day15b(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day15b(readInputLines("day15")))
    }
}