package v2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day02aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(150, day02a(listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1690020, day02a(readInputLines("day02", 2021)))
    }
}

class Day02bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(900, day02b(listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2")))
    }

    @Test
    fun testActualInput() {
        assertEquals(1408487760, day02b(readInputLines("day02", 2021)))
    }
}
