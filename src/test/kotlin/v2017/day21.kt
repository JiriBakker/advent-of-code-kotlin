package v2017.days.day21

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day21aTests {
    @Test fun testExampleInput1() {
        assertEquals(12, day21a(listOf(
            "../.# => ##./#../...",
            ".#./..#/### => #..#/..../..../#..#"
        ), 2))
    }

    @Test fun testActualInput() {
        assertEquals(173, day21a(readInputLines("day21", 2017)))
    }
}

class Day21bTests {
    @Test fun testActualInput() {
        assertEquals(2456178, day21b(readInputLines("day21", 2017)))
    }
}
