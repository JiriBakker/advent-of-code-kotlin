package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day12a(listOf(
            "0 <-> 2",
            "1 <-> 1",
            "2 <-> 0, 3, 4",
            "3 <-> 2, 4",
            "4 <-> 2, 3, 6",
            "5 <-> 6",
            "6 <-> 4, 5"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(134, day12a(readInputLines("day12", 2017)))
    }
}

class Day12bTests {
    @Test fun testActualInput() {
        assertEquals(193, day12b(readInputLines("day12", 2017)))
    }
}
