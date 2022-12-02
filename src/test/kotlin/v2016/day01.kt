package v2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day01aTests {
    @Test fun testExampleInput1() {
        assertEquals(5, day01a("R2, L3"))
    }

    @Test fun testExampleInput2() {
        assertEquals(2, day01a("R2, R2, R2"))
    }

    @Test fun testExampleInput3() {
        assertEquals(12, day01a("R5, L5, R5, R3"))
    }

    @Test fun testActualInput() {
        assertEquals(278, day01a(readInputLine("day01", 2016)))
    }
}

class Day01bTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day01b("R8, R4, R4, R8"))
    }

    @Test fun testActualInput() {
        assertEquals(161, day01b(readInputLine("day01", 2016)))
    }
}
