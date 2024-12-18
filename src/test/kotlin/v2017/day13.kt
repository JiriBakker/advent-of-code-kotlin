package v2017.days.day13

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day13aTests {
    @Test fun testExampleInput1() {
        assertEquals(24, day13a(listOf(
            "0: 3",
            "1: 2",
            "4: 4",
            "6: 4"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(2160, day13a(readInputLines("day13", 2017)))
    }
}

class Day13bTests {
    @Test fun testActualInput() {
        assertEquals(3907470, day13b(readInputLines("day13", 2017)))
    }
}
