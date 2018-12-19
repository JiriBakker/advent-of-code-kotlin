package days.day19

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(6,
            day19a(
                listOf(
                    "#ip 0",
                    "seti 5 0 1",
                    "seti 6 0 2",
                    "addi 0 1 0",
                    "addr 1 2 3",
                    "setr 1 0 0",
                    "seti 8 0 4",
                    "seti 9 0 5"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(1620,
            day19a(readInputLines("day19"))
        )
    }
}

class Day19bTests {
    @Test fun testActualInput() {
        assertEquals(15827082,
            day19b(readInputLines("day19"))
        )
    }
}