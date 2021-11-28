package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day21aTests {
    @Test fun testActualInput() {
        assertEquals(19347995, day21a(readInputLine("day21", 2019)))
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(1141826552, day21b(readInputLine("day21", 2019)))
    }
}