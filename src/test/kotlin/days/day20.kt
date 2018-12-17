package days.day20

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day20a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day20a(readInputLines("day20"))
        )
    }
}

class Day20bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day20b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day20b(readInputLines("day20"))
        )
    }
}