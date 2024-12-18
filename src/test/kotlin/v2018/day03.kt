package v2018.days.day03

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

class Day03aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day03a(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")))
    }

    @Test fun testTripleOverlap() {
        assertEquals(4, day03a(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 3,3: 2x2")))
    }

    @Test fun testActualInput() {
        assertEquals(98005, day03a(readInputLines("day03")))
    }
}

class Day03bTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day03b(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")))
    }

    @Test fun testTripleOverlap() {
        assertEquals(null, day03b(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 3,3: 2x2")))
    }

    @Test fun testActualInput() {
        assertEquals(331, day03b(readInputLines("day03")))
    }
}