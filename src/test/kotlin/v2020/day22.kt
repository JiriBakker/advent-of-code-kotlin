package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.days.day22.day22a
import v2020.days.day22.day22b
import util.readInputLines

class Day22aTests {
    @Test fun testExampleInput1() {
        assertEquals(306, day22a(listOf(
            "Player 1:",
            "9",
            "2",
            "6",
            "3",
            "1",
            "",
            "Player 2:",
            "5",
            "8",
            "4",
            "7",
            "10")))
    }

    @Test fun testActualInput() {
        assertEquals(35562, day22a(readInputLines("day22", 2020)))
    }
}

class Day22bTests {
    @Test fun testExampleInput1() {
        assertEquals(291, day22b(listOf(
            "Player 1:",
            "9",
            "2",
            "6",
            "3",
            "1",
            "",
            "Player 2:",
            "5",
            "8",
            "4",
            "7",
            "10")))
    }

    @Test fun testActualInput() {
        assertEquals(34424, day22b(readInputLines("day22", 2020)))
    }
}
