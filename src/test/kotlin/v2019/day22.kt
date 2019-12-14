package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day22.day22a
import v2019.days.day22.day22b
import v2019.util.readInputLines

class Day22aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day22a(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day22a(readInputLines("day22")))
    }
}

class Day22bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day22b(listOf("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day22b(readInputLines("day22")))
    }
}