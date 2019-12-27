package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day25.day25a
import util.readInputLine

class Day25aTests {
    @Test fun testActualInput() {
        assertEquals(2650453, day25a(readInputLine("day25", 2015)))
    }
}
