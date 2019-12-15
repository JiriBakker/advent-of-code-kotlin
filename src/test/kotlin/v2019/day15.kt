package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day15.day15a
import v2019.days.day15.day15b
import v2019.util.readInputLine

class Day15aTests {
    @Test fun testActualInput() {
        assertEquals(258, day15a(readInputLine("day15")))
    }
}

class Day15bTests {
    @Test fun testActualInput() {
        assertEquals(372, day15b(readInputLine("day15")))
    }
}