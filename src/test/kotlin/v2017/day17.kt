package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day17aTests {
    @Test fun testExampleInput1() {
        assertEquals(638, day17a("3"))
    }

    @Test fun testActualInput() {
        assertEquals(600, day17a(readInputLine("day17", 2017)))
    }
}

class Day17bTests {
    @Test fun testExampleInput1() {
        assertEquals(1222153, day17b("3"))
    }

    @Test fun testActualInput() {
        assertEquals(31220910, day17b(readInputLine("day17", 2017)))
    }
}
