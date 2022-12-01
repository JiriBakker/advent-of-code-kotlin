package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day10aTests {
    @Test fun testExampleInput1() {
        assertEquals(35, day10a(listOf(
            "16",
            "10",
            "15",
            "5",
            "1",
            "11",
            "7",
            "19",
            "6",
            "12",
            "4")))
    }

    @Test fun testExampleInput2() {
        assertEquals(220, day10a(listOf(
            "28",
            "33",
            "18",
            "42",
            "31",
            "14",
            "46",
            "20",
            "48",
            "47",
            "24",
            "23",
            "49",
            "45",
            "19",
            "38",
            "39",
            "11",
            "1",
            "32",
            "25",
            "35",
            "8",
            "17",
            "7",
            "9",
            "4",
            "2",
            "34",
            "10",
            "3")))
    }

    @Test fun testActualInput() {
        assertEquals(2240, day10a(readInputLines("day10", 2020)))
    }
}

class Day10bTests {
    @Test fun testExampleInput1() {
        assertEquals(8, day10b(listOf(
            "16",
            "10",
            "15",
            "5",
            "1",
            "11",
            "7",
            "19",
            "6",
            "12",
            "4")))
    }

    @Test fun testExampleInput2() {
        assertEquals(19208, day10b(listOf(
            "28",
            "33",
            "18",
            "42",
            "31",
            "14",
            "46",
            "20",
            "48",
            "47",
            "24",
            "23",
            "49",
            "45",
            "19",
            "38",
            "39",
            "11",
            "1",
            "32",
            "25",
            "35",
            "8",
            "17",
            "7",
            "9",
            "4",
            "2",
            "34",
            "10",
            "3")))
    }

    @Test fun testCustomInput1_withDelta2() {
        assertEquals(9, day10b(listOf(
            "1",
            "2",
            "4",
            "7",
            "8",
            "10",
            "11",
            "14"
        )))
    }

    @Test fun testActualInput() {
        assertEquals(99214346656768L, day10b(readInputLines("day10", 2020)))
    }
}
