package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day08aTests {
    @Test fun testExampleInput1() {
        assertEquals(12, day08a(listOf("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"")))
    }

    @Test fun testActualInput() {
        assertEquals(1350, day08a(readInputLines("day08", 2015)))
    }
}

class Day08bTests {
    @Test fun testExampleInput1() {
        assertEquals(19, day08b(listOf("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"")))
    }

    @Test fun testActualInput() {
        assertEquals(2085, day08b(readInputLines("day08", 2015)))
    }
}

