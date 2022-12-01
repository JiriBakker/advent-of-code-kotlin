package v2017

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLines

class Day08aTests {
    @Test fun testExampleInput1() {
        assertEquals(1, day08a(listOf(
            "b inc 5 if a > 1",
            "a inc 1 if b < 5",
            "c dec -10 if a >= 1",
            "c inc -20 if c == 10")))
    }

    @Test fun testActualInput() {
        assertEquals(6061, day08a(readInputLines("day08", 2017)))
    }
}

class Day08bTests {
    @Test fun testExampleInput1() {
        assertEquals(10, day08b(listOf(
            "b inc 5 if a > 1",
            "a inc 1 if b < 5",
            "c dec -10 if a >= 1",
            "c inc -20 if c == 10")))
    }

    @Test fun testActualInput() {
        assertEquals(6696, day08b(readInputLines("day08", 2017)))
    }
}
