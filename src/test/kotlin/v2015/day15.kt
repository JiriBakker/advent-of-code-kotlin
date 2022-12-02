package v2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day15aTests {
    @Test fun testExampleInput1() {
        assertEquals(62842880, day15a(listOf(
            "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
                "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3")))
    }

    @Test fun testActualInput() {
        assertEquals(21367368, day15a(readInputLines("day15", 2015)))
    }
}

class Day15bTests {
    @Test fun testExampleInput1() {
        assertEquals(57600000, day15b(listOf(
            "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8",
                "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3")))
    }

    @Test fun testActualInput() {
        assertEquals(1766400, day15b(readInputLines("day15", 2015)))
    }
}