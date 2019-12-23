package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day23.day23a
import v2019.days.day23.day23b
import v2019.util.readInputLine

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(19473, day23a(readInputLine("day23")))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(12475, day23b(readInputLine("day23")))
    }
}