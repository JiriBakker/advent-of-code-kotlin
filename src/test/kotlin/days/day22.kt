package days.day22

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day22aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day22a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day22a(readInputLines("day22"))
        )
    }
}

class Day22bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day22b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day22b(readInputLines("day22"))
        )
    }
}