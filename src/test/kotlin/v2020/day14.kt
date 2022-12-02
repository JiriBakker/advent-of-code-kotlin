package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day14aTests {
    @Test fun testExampleInput1() {
        assertEquals(165, day14a(listOf(
            "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
            "mem[8] = 11",
            "mem[7] = 101",
            "mem[8] = 0")))
    }

    @Test fun testActualInput() {
        assertEquals(5055782549997L, day14a(readInputLines("day14", 2020)))
    }
}

class Day14bTests {
    @Test fun testExampleInput1() {
        assertEquals(208, day14b(listOf(
            "mask = 000000000000000000000000000000X1001X",
            "mem[42] = 100",
            "mask = 00000000000000000000000000000000X0XX",
            "mem[26] = 1")))
    }

    @Test fun testActualInput() {
        assertEquals(4795970362286, day14b(readInputLines("day14", 2020)))
    }
}
