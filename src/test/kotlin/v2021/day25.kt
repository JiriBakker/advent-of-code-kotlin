package v2021

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLines

class Day25aTests {
    @Test
    @Ignore
    fun testExampleInput1() {
        assertEquals(0, day25a(listOf()))
    }

    @Test
    @Ignore
    fun testActualInput() {
        assertEquals(0, day25a(readInputLines("day25", 2021)))
    }
}