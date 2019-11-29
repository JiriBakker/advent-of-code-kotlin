package v2018.days.day22

import org.junit.Assert.assertEquals
import org.junit.Test
import v2018.readInputLines

class Day22aTests {
    @Test fun testExampleInput1() {
        assertEquals(114,
            day22a(
                listOf(
                    "depth: 510",
                    "target: 10,10"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(7402,
            day22a(readInputLines("day22"))
        )
    }
}

class Day22bTests {
    @Test fun testExampleInput1() {
        assertEquals(45,
            day22b(
                listOf(
                    "depth: 510",
                    "target: 10,10"
                )
            )
        )
    }

    @Test fun testActualInput() {
        assertEquals(1025,
            day22b(readInputLines("day22"))
        )
    }
}