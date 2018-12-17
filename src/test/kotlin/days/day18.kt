package days.day18

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day18a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day18a(readInputLines("day18"))
        )
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day18b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day18b(readInputLines("day18"))
        )
    }
}