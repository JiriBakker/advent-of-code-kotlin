package v2021

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLines

class Day01aTests {
    @Test
    @Ignore
    fun testExampleInput1() {
        assertEquals(0, day01a(listOf()))
    }

    @Test
    @Ignore
    fun testActualInput() {
        assertEquals(0, day01a(readInputLines("day01", 2021)))
    }
}

class Day01bTests {
    @Test
    @Ignore
    fun testExampleInput1() {
        assertEquals(0, day01b(listOf()))
    }

    @Test
    @Ignore
    fun testActualInput() {
        assertEquals(0, day01b(readInputLines("day01", 2021)))
    }
}
