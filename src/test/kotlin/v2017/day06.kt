package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals(5, day06a("0\t2\t7\t0"))
    }

    @Test fun testActualInput() {
        assertEquals(11137, day06a(readInputLine("day06", 2017)))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day06b("0\t2\t7\t0"))
    }

    @Test fun testActualInput() {
        assertEquals(1037, day06b(readInputLine("day06", 2017)))
    }
}
