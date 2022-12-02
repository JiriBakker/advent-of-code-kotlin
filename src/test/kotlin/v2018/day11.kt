package v2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day11aTests {
    @Test fun testComputePowerLevel1() {
        assertEquals(4, computePowerLevel(3, 5, 8))
    }

    @Test fun testComputePowerLevel2() {
        assertEquals(-5, computePowerLevel(122, 79, 57))
    }

    @Test fun testComputePowerLevel3() {
        assertEquals(0, computePowerLevel(217, 196, 39))
    }

    @Test fun testComputePowerLevel4() {
        assertEquals(4, computePowerLevel(101, 153, 71))
    }

    @Test fun testExampleInput1() {
        assertEquals(Pair(33, 45),
            day11a("18")
        )
    }

    @Test fun testExampleInput2() {
        assertEquals(Pair(21, 61),
            day11a("42")
        )
    }

    @Test fun testActualInput() {
        assertEquals(Pair(235, 60),
            day11a(readInputLine("day11", 2018))
        )
    }
}

class Day11bTests {
    @Test fun testExampleInput1() {
        assertEquals(Triple(90, 269, 16),
            day11b("18")
        )
    }

    @Test fun testExampleInput2() {
        assertEquals(Triple(232, 251, 12),
            day11b("42")
        )
    }

    @Test fun testActualInput() {
        assertEquals(Triple(233, 282, 11),
            day11b(readInputLine("day11", 2018))
        )
    }
}
