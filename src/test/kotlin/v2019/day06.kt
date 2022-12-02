package v2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.parseCsv
import util.readInputLines

class Day06aTests {
    @Test fun testExampleInput1() {
        assertEquals(42, day06a(parseCsv("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L")))
    }

    @Test fun testActualInput() {
        assertEquals(135690, day06a(readInputLines("day06", 2019)))
    }
}

class Day06bTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day06b(parseCsv("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L,K)YOU,I)SAN")))
    }

    @Test fun testActualInput() {
        assertEquals(298, day06b(readInputLines("day06", 2019)))
    }
}
