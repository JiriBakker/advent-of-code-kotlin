package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day23.day23a
import v2015.days.day23.day23b
import v2015.util.readInputLines

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(184, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(231, day23b(readInputLines("day23")))
    }
}