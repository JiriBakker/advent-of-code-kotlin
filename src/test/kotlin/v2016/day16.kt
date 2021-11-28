package v2016

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day16aTests {
    @Test fun testExampleInput1() {
        assertEquals("01100", day16a("10000", 20))
    }

    @Test fun testActualInput() {
        assertEquals("11101010111100010", day16a(readInputLine("day16", 2016)))
    }
}

class Day16bTests {
    @Test fun testActualInput() {
        assertEquals("01001101001000101", day16b(readInputLine("day16", 2016)))
    }
}
