package v2021

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day24aTests {
    @Test
    fun testActualInput() {
        assertEquals(92928914999991L, day24a(readInputLines("day24", 2021)))
    }
}

class Day24bTests {

    @Test
    fun testActualInput() {
        assertEquals(91811211611981L, day24b(readInputLines("day24", 2021)))
    }
}
