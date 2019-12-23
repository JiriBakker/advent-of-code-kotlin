package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day16.day16a
import v2015.days.day16.day16b
import v2015.util.readInputLines

class Day16aTests {
    @Test fun testActualInput() {
        assertEquals(213, day16a(readInputLines("day16")))
    }
}

class Day16bTests {
    @Test fun testActualInput() {
        assertEquals(323, day16b(readInputLines("day16")))
    }
}