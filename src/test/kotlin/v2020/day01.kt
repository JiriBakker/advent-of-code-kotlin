package v2020

import org.junit.Assert.assertEquals
import org.junit.Test
import v2020.days.day01.day01a
import v2020.days.day01.day01b
import util.readInputLines

class Day01aTests {
    @Test fun testExampleInput1() {
        assertEquals(514579, day01a(listOf("1721","979","366","299","675","1456")))
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
