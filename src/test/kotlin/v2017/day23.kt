package v2017.days.day23

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day23aTests {
    @Test fun testActualInput() {
        assertEquals(8281, day23a(readInputLines("day23", 2017)))
    }
}

class Day23bTests {
    @Test fun testActualInput() {
        assertEquals(911, day23b(readInputLines("day23", 2017)))
    }
}
