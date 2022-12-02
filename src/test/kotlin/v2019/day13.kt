package v2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day13aTests {
    @Test fun testActualInput() {
        assertEquals(306, day13a(readInputLine("day13", 2019)))
    }
}

class Day13bTests {
    @Test fun testActualInput() {
        assertEquals(15328, day13b(readInputLine("day13", 2019)))
    }
}