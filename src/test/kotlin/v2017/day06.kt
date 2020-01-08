package v2017.days.day06

import org.junit.Assert.assertEquals
import org.junit.Test
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
