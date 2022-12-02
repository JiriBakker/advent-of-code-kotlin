package v2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLines

class Day11aTests {
    @Test
    fun testExampleInput1() {
        assertEquals(1656, day11a(listOf(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(1665, day11a(readInputLines("day11", 2021)))
    }
}

class Day11bTests {
    @Test
    fun testExampleInput1() {
        assertEquals(195, day11b(listOf(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"
        )))
    }

    @Test
    fun testActualInput() {
        assertEquals(235, day11b(readInputLines("day11", 2021)))
    }
}
