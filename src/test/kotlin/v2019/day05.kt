package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day05.day05a
import v2019.days.day05.day05b
import v2019.util.readInputLine

class Day05aTests {
    @Test fun testActualInput() {
        assertEquals(7988899, day05a(readInputLine("day05")))
    }
}

class Day05bTests {
    @Test fun testExampleInput1() {
        assertEquals(999, day05b(
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
            "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
            "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", 7))
    }
    @Test fun testExampleInput2() {
        assertEquals(1000, day05b(
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
            "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
            "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", 8))
    }
    @Test fun testExampleInput3() {
        assertEquals(1001, day05b(
            "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
            "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
            "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99", 9))
    }

    @Test fun testActualInput() {
        assertEquals(13758663, day05b(readInputLine("day05"), 5))
    }
}