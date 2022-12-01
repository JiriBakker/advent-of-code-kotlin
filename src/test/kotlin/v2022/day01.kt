package v2022

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day01aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(24000, day01a(listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000",
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(68467, day01a(readInputLines("day01", 2022)))
    }
}

class Day01bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(45000, day01b(listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000",
            "")))
    }

    @Test
    fun testActualInput() {
        assertEquals(203420, day01b(readInputLines("day01", 2022)))
    }
}
