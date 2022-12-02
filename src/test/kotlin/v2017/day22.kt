package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day22aTests {
     @Test fun testExampleInput1() {
        assertEquals(41, day22a(listOf(
            "..#",
            "#..",
            "..."
        ), 70))
    }

    @Test fun testExampleInput1b() {
        assertEquals(5587, day22a(listOf(
            "..#",
            "#..",
            "..."
        )))
    }

    @Test fun testActualInput() {
        assertEquals(5348, day22a(readInputLines("day22", 2017)))
    }
}

class Day22bTests {
    @Test fun testExampleInput1() {
        assertEquals(26, day22b(listOf(
            "..#",
            "#..",
            "..."
        ), 100))
    }

    @Test fun testExampleInput1b() {
        assertEquals(2511944, day22b(listOf(
            "..#",
            "#..",
            "..."
        )))
    }

    @Test fun testActualInput() {
        assertEquals(2512225, day22b(readInputLines("day22", 2017)))
    }
}
