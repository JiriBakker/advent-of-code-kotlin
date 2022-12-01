package v2016

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day13aTests {
    @Test fun testExampleInput1() {
        assertEquals(11, day13a("10", 7, 4))
    }

    @Test fun testActualInput() {
        assertEquals(86, day13a(readInputLine("day13", 2016)))
    }
}

class Day13bTests {
    @Test fun testActualInput() {
       assertEquals(127, day13b(readInputLine("day13", 2016)))
    }
}
