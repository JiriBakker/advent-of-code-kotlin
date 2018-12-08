package days.day05

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLine

class Day05aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day05a("aA"))
    }

    @Test fun testExampleInput2() {
        assertEquals(0, day05a("abBA"))
    }

    @Test fun testExampleInput3() {
        assertEquals(4, day05a("abAB"))
    }

    @Test fun testExampleInput4() {
        assertEquals(6, day05a("aabAAB"))
    }

    @Test fun testExampleInput5() {
        assertEquals(10, day05a("dabAcCaCBAcCcaDA"))
    }

    @Test fun testActualInput() {
        assertEquals(11264, day05a(readInputLine("day05")))
    }
}

class Day05bTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day05b("dabAcCaCBAcCcaDA"))
    }

    // SLOW
    // @Test fun testActualInput() {
    //     assertEquals(4552, day05b(readInputLine("day05")))
    // }
}
