package v2016.days.day25

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day25aTests {
    @Test fun testActualInput() {
        assertEquals(180, day25a(readInputLines("day25", 2016)))
    }
}
