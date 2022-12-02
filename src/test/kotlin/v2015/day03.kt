package v2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day03aTests {
    @Test fun testExampleInput1() {
        assertEquals(2, day03a(">"))
    }

    @Test fun testExampleInput2() {
        assertEquals(4, day03a("^>v<"))
    }

    @Test fun testExampleInput3() {
        assertEquals(2, day03a("^v^v^v^v^v"))
    }

    @Test fun testActualInput() {
        assertEquals(2565, day03a(readInputLine("day03", 2015)))
    }
}

class Day03bTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day03b("^v"))
    }

    @Test fun testExampleInput2() {
        assertEquals(3, day03b("^>v<"))
    }

    @Test fun testExampleInput3() {
        assertEquals(11, day03b("^v^v^v^v^v"))
    }

    @Test fun testActualInput() {
        assertEquals(2639, day03b(readInputLine("day03", 2015)))
    }
}
