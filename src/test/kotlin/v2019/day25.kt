package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day25.day25a
import v2019.util.readInputLines

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day25a(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day25a(readInputLines("day25")))
    }
}