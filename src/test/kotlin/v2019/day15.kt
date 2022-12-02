package v2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day15aTests {
    @Test fun testActualInput() {
        assertEquals(258, day15a(readInputLine("day15", 2019)))
    }
}

class Day15bTests {
    @Test fun testActualInput() {
        assertEquals(372, day15b(readInputLine("day15", 2019)))
    }
}