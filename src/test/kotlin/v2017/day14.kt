package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day14aTests {
    @Test fun testExampleInput1() {
        assertEquals(8108, day14a("flqrgnkx"))
    }

    @Test fun testActualInput() {
        assertEquals(8222, day14a(readInputLine("day14", 2017)))
    }
}

class Day14bTests {
    @Test fun testActualInput() {
        assertEquals(1086, day14b(readInputLine("day14", 2017)))
    }
}
