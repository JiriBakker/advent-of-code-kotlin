package v2016

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day11aTests {
    @Test fun testActualInput() {
        assertEquals(47, day11a(readInputLines("day11", 2016)))
    }
}

class Day11bTests {
    @Test fun testActualInput() {
        assertEquals(71, day11b(readInputLines("day11", 2016)))
    }
}
