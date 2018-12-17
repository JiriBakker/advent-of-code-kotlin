package days.day19

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day19a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day19a(readInputLines("day19"))
        )
    }
}

class Day19bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day19b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day19b(readInputLines("day19"))
        )
    }
}