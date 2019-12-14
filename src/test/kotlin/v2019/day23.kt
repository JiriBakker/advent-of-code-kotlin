package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day23.day23a
import v2019.days.day23.day23b
import v2019.util.readInputLines

class Day23aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day23a(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day23b(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day23b(readInputLines("day23")))
    }
}