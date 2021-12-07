package v2021

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLines

class Day07aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(37, day07a(listOf("16,1,2,0,4,2,7,1,2,14")))
    }

    @Test
    fun testActualInput() {
        assertEquals(340987, day07a(readInputLines("day07", 2021)))
    }
}

class Day07bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(168, day07b(listOf("16,1,2,0,4,2,7,1,2,14")))
    }

    @Test
    fun testActualInput() {
        assertEquals(96987874, day07b(readInputLines("day07", 2021)))
    }
}
