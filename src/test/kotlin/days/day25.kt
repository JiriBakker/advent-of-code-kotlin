package days.day25

import org.junit.Assert.assertEquals
import org.junit.Test
import readInputLines

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day25a(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day25a(readInputLines("day25"))
        )
    }
}

class Day25bTests {
    @Test fun testExampleInput1() {
        assertEquals(null,
            day25b(
                listOf(
                    ""
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(null,
            day25b(readInputLines("day25"))
        )
    }
}