package v2016.days.day05

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import util.readInputLine

class Day05aTests {
    @Ignore // Too slow
    @Test fun testExampleInput1() {
        assertEquals("18f47a30", day05a("abc"))
    }

    @Test fun testActualInput() {
        assertEquals("f97c354d", day05a(readInputLine("day05", 2016)))
    }
}

class Day05bTests {
    @Test fun testActualInput() {
        assertEquals("863dde27", day05b(readInputLine("day05", 2016)))
    }
}
