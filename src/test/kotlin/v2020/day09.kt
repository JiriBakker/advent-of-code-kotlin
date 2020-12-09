package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.days.day09.day09a
import v2020.days.day09.day09b
import util.readInputLines

class Day09aTests {
    @Test fun testExampleInput1() {
        assertEquals(127, day09a(listOf(
            "35",
            "20",
            "15",
            "25",
            "47",
            "40",
            "62",
            "55",
            "65",
            "95",
            "102",
            "117",
            "150",
            "182",
            "127",
            "219",
            "299",
            "277",
            "309",
            "576"), 5))
    }

    @Test fun testActualInput() {
        assertEquals(248131121, day09a(readInputLines("day09", 2020)))
    }
}

class Day09bTests {
    @Test fun testExampleInput1() {
        assertEquals(62, day09b(listOf(
            "35",
            "20",
            "15",
            "25",
            "47",
            "40",
            "62",
            "55",
            "65",
            "95",
            "102",
            "117",
            "150",
            "182",
            "127",
            "219",
            "299",
            "277",
            "309",
            "576"), 5))
    }

    @Test fun testActualInput() {
        assertEquals(31580383, day09b(readInputLines("day09", 2020)))
    }
}
