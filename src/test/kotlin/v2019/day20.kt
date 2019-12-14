package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day20.day20a
import v2019.days.day20.day20b
import v2019.util.readInputLines


class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day20a(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day20a(readInputLines("day20")))
    }
}

class Day20bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day20b(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day20b(readInputLines("day20")))
    }
}