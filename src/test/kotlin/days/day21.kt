package days.day21

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day21aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day21a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day21a(readInputLines("day21"))
        )
    }
}

class Day21bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day21b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day21b(readInputLines("day21"))
        )
    }
}