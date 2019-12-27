package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day19.day19a
import v2015.days.day19.day19b
import util.readInputLines

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(4, day19a(listOf(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOH")))
    }

    @Test fun testExampleInput2() {
        assertEquals(7, day19a(listOf(
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOHOHO")))
    }

    @Test fun testActualInput() {
        assertEquals(509, day19a(readInputLines("day19", 2015)))
    }
}

class Day19bTests {
    @Test fun testExampleInput1() {
        assertEquals(3, day19b(listOf(
            "e => H",
            "e => O",
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOH")))
    }

    @Test fun testExampleInput2() {
        assertEquals(6, day19b(listOf(
            "e => H",
            "e => O",
            "H => HO",
            "H => OH",
            "O => HH",
            "",
            "HOHOHO")))
    }

    @Test fun testActualInput() {
        assertEquals(195, day19b(readInputLines("day19", 2015)))
    }
}