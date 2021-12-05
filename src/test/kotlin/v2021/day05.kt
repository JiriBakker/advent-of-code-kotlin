package v2021

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLines

class Day05aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(5, day05a(listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(5147, day05a(readInputLines("day05", 2021)))
    }
}

class Day05bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(12, day05b(listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(16925, day05b(readInputLines("day05", 2021)))
    }
}
