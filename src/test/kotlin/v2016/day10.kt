package v2016.days.day10

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day10aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day10a(listOf(
            "value 5 goes to bot 2",
            "bot 2 gives low to bot 1 and high to bot 0",
            "value 3 goes to bot 1",
            "bot 1 gives low to output 1 and high to bot 0",
            "bot 0 gives low to output 2 and high to output 0",
            "value 2 goes to bot 2"
        ), 3, 2))
    }

    @Test fun testActualInput() {
        assertEquals(181, day10a(readInputLines("day10", 2016)))
    }
}

class Day10bTests {
    @Test fun testActualInput() {
        assertEquals(12567, day10b(readInputLines("day10", 2016)))
    }
}
