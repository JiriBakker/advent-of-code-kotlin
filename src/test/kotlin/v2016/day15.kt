package v2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test fun testExampleInput1() {
        assertEquals(5, day15a(listOf(
            "Disc #1 has 5 positions; at time=0, it is at position 4.",
            "Disc #2 has 2 positions; at time=0, it is at position 1."
        )))
    }

    @Test fun testActualInput() {
        assertEquals(376777, day15a(readInputLines("day15", 2016)))
    }
}

class Day15bTests {
    @Test fun testActualInput() {
        assertEquals(3903937, day15b(readInputLines("day15", 2016)))
    }
}
