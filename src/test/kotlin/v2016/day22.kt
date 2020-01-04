package v2016.days.day22

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day22aTests {
    @Test fun testActualInput() {
        assertEquals(1003, day22a(readInputLines("day22", 2016)))
    }
}

class Day22bTests {
    @Test fun testActualInput() {
        assertEquals(192, day22b(readInputLines("day22", 2016)))
    }
}
