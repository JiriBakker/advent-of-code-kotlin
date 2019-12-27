package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day04.day04a
import v2019.days.day04.day04b
import util.readInputLine

class Day04aTests {
    @Test fun testActualInput() {
        assertEquals(1665, day04a(readInputLine("day04", 2019)))
    }
}

class Day04bTests {
    @Test fun testActualInput() {
        assertEquals(1131, day04b(readInputLine("day04", 2019)))
    }
}
