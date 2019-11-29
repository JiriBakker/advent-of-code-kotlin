package v2018.days.day16

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

class Day16aTests {
    @Test fun testExampleInput1() {
        assertEquals(1,
            day16a(
                listOf(
                    "Before: [3, 2, 1, 1]",
                    "9 2 1 2",
                    "After:  [3, 2, 2, 1]"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(588,
            day16a(readInputLines("day16"))
        )
    }
}

class Day16bTests {
    @Test fun testActualInput() {
        assertEquals(627,
            day16b(readInputLines("day16"))
        )
    }
}
