package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.days.day02.day02a
import v2020.days.day02.day02b
import util.readInputLines

class Day02aTests {
    @Test fun testExampleInput1() {
        assertEquals(2, day02a(listOf("1-3 a: abcde","1-3 b: cdefg","2-9 c: ccccccccc")))
    }

    @Test fun testActualInput() {
        assertEquals(383, day02a(readInputLines("day02", 2020)))
    }
}

class Day02bTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day02b(listOf("1-3 a: abcde","1-3 b: cdefg","2-9 c: ccccccccc")))
    }

    @Test fun testActualInput() {
        assertEquals(272, day02b(readInputLines("day02", 2020)))
    }
}
