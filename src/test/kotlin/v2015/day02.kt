package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day02.day02a
import v2015.days.day02.day02b
import v2015.util.readInputLines

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(58, day02a(listOf("2x3x4")))
    }

    @Test fun testExampleInput2() {
        assertEquals(43, day02a(listOf("1x1x10")))
    }

    @Test fun testActualInput() {
        assertEquals(1598415, day02a(readInputLines("day02")))
    }
}

class Day02bTests {
    @Test fun testExampleInput1() {
        assertEquals(34, day02b(listOf("2x3x4")))
    }

    @Test fun testExampleInput2() {
        assertEquals(14, day02b(listOf("1x1x10")))
    }

    @Test fun testActualInput() {
        assertEquals(3812909, day02b(readInputLines("day02")))
    }
}