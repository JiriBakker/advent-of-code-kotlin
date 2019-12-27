package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day19.day19a
import v2019.days.day19.day19b
import util.readInputLine

class Day19aTests {
    @Test fun testActualInput() {
        assertEquals(164, day19a(readInputLine("day19", 2019)))
    }
}

class Day19bTests {
    @Test fun testActualInput() {
        assertEquals(13081049, day19b(readInputLine("day19", 2019)))
    }
}