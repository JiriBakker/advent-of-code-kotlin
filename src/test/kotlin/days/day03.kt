package days

import org.junit.Assert.assertEquals
import org.junit.Test

class Day03aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day03a(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")))
    }

    @Test fun testTripleOverlap() {
        assertEquals(4, day03a(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 3,3: 2x2")))
    }
}

class Day03bTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day03b(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")))
    }

    @Test fun testTripleOverlap() {
        assertEquals(null, day03b(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 3,3: 2x2")))
    }
}