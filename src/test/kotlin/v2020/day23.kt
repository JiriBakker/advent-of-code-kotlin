package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day23aTests {
    @Test fun testExampleInput1() {
        assertEquals(92658374, day23a(listOf(
            "389125467"), 10))
    }

    @Test fun testExampleInput2() {
        assertEquals(67384529, day23a(listOf(
            "389125467")))
    }

    @Test fun testActualInput() {
        assertEquals(38756249, day23a(readInputLines("day23", 2020)))
    }
}

class Day23bTests {
    @Test fun testExampleInput1() {
        assertEquals(149245887792L, day23b(listOf(
            "389125467")))
    }

    @Test fun testActualInput() {
        assertEquals(21986479838L, day23b(readInputLines("day23", 2020)))
    }
}
