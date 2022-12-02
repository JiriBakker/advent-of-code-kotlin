package v2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day01aTests {
    @Test fun testExampleInput1() {
        assertEquals(514579, day01a(listOf("1721","979","366","299","675","1456")))
    }

    @Test fun testCustomInput1() {
        assertEquals(1020000, day01a(listOf("1010","1000","1020")))
    }

    @Test fun testActualInput() {
        assertEquals(651651, day01a(readInputLines("day01", 2020)))
    }
}

class Day01bTests {
    @Test fun testExampleInput1() {
        assertEquals(241861950, day01b(listOf("1721","979","366","299","675","1456")))
    }

    @Test fun testActualInput() {
        assertEquals(214486272, day01b(readInputLines("day01", 2020)))
    }
}
