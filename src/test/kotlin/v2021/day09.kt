package v2021

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day09aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(15, day09a(listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(439, day09a(readInputLines("day09", 2021)))
    }
}

class Day09bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1134, day09b(listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(900900, day09b(readInputLines("day09", 2021)))
    }
}
