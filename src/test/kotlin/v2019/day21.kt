package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day21.day21a
import v2019.days.day21.day21b
import v2019.util.readInputLine

class Day21aTests {
    @Test fun testActualInput() {
        assertEquals(19347995, day21a(readInputLine("day21")))
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(1141826552, day21b(readInputLine("day21")))
    }
}