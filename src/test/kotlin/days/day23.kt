package days.day23

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day23aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day23a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day23a(readInputLines("day23"))
        )
    }
}

class Day23bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day23b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day23b(readInputLines("day23"))
        )
    }
}