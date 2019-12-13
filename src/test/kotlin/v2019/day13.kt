package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day13.day13a
import v2019.days.day13.day13b
import v2019.util.readInputLine


class Day13aTests {
    @Test fun testActualInput() {
        assertEquals(306, day13a(readInputLine("day13")))
    }
}

class Day13bTests {
    @Test fun testActualInput() {
        assertEquals(15328, day13b(readInputLine("day13")))
    }
}