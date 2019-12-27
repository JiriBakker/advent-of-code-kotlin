package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day21.day21a
import v2015.days.day21.day21b
import util.readInputLines

class Day21aTests {
    @Test fun testActualInput() {
        assertEquals(91, day21a(readInputLines("day21", 2015)))
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(158, day21b(readInputLines("day21", 2015)))
    }
}