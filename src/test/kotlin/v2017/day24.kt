package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(31, day24a(listOf(
            "0/2",
            "2/2",
            "2/3",
            "3/4",
            "3/5",
            "0/1",
            "10/1",
            "9/10"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(1511, day24a(readInputLines("day24", 2017)))
    }
}

class Day24bTests {
    @Test fun testActualInput() {
        assertEquals(1471, day24b(readInputLines("day24", 2017)))
    }
}
