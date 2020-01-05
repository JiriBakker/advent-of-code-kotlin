package v2017.days.day03

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day03aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day03a("1"))
    }

    @Test fun testExampleInput2() {
        assertEquals(3, day03a("12"))
    }

    @Test fun testExampleInput3() {
        assertEquals(2, day03a("23"))
    }

    @Test fun testExampleInput4() {
        assertEquals(31, day03a("1024"))
    }

    @Test fun testCustomInput1() {
        assertEquals(3, day03a("10"))
    }

    @Test fun testCustomInput2() {
        assertEquals(4, day03a("25"))
    }

    @Test fun testActualInput() {
        assertEquals(475, day03a(readInputLine("day03", 2017)))
    }
}

class Day03bTests {
    @Test fun testActualInput() {
        assertEquals(279138, day03b(readInputLine("day03", 2017)))
    }
}
