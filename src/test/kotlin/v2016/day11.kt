package v2016.days.day11

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day11aTests {
    @Test fun testExampleInput1() {
        assertEquals(11, day11a(listOf(
            "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.",
            "The second floor contains a hydrogen generator.",
            "The third floor contains a lithium generator.",
            "The fourth floor contains nothing relevant."
        )))
    }

    @Test fun testActualInput() {
        assertEquals(47, day11a(readInputLines("day11", 2016)))
    }
}

class Day11bTests {
    @Test fun testActualInput() {
        assertEquals(12567, day11b(readInputLines("day11", 2016)))
    }
}
