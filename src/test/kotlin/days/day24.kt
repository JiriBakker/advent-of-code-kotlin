package days.day24

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day24a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day24a(readInputLines("day24"))
        )
    }
}

class Day24bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day24b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day24b(readInputLines("day24"))
        )
    }
}