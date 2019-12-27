package v2016.days.day03

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day03aTests {
    @Test fun testActualInput() {
        assertEquals(869, day03a(readInputLines("day03", 2016)))
    }
}

class Day03bTests {
    @Test fun testActualInput() {
        assertEquals(1544, day03b(readInputLines("day03", 2016)))
    }
}
