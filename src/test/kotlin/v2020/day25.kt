package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.days.day25.day25a
import util.readInputLines

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(14897079L, day25a(listOf(
            "5764801",
            "17807724")))
    }

    @Test fun testActualInput() {
        assertEquals(3803729, day25a(readInputLines("day25", 2020)))
    }
}
