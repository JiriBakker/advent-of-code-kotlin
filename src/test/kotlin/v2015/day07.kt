package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day07.day07a
import v2015.days.day07.day07b
import v2015.util.readInputLines

class Day07aTests {
    @Test fun testExampleInput1() {
        assertEquals(65079, day07a(listOf(
            "123 -> x",
            "456 -> y",
            "x AND y -> d",
            "x OR y -> e",
            "x LSHIFT 2 -> f",
            "y RSHIFT 2 -> g",
            "NOT x -> h",
            "NOT y -> i"), "i"))
    }

    @Test fun testActualInput() {
        assertEquals(46065, day07a(readInputLines("day07")))
    }
}

class Day07bTests {
    @Test fun testActualInput() {
        assertEquals(14134, day07b(readInputLines("day07")))
    }
}

