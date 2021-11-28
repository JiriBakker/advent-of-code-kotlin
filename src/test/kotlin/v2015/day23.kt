package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(184, day23a(readInputLines("day23", 2015)))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(231, day23b(readInputLines("day23", 2015)))
    }
}