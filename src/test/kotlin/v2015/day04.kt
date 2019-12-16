package v2015

import org.junit.Assert.assertEquals
import org.junit.Test
import v2015.days.day04.day04a
import v2015.days.day04.day04b
import v2015.util.readInputLine

class Day04aTests {
    @Test fun testExampleInput1() {
        assertEquals("609043", day04a("abcdef"))
    }

    @Test fun testExampleInput2() {
        assertEquals("1048970", day04a("pqrstuv"))
    }

    @Test fun testActualInput() {
        assertEquals("282749", day04a(readInputLine("day04")))
    }
}

class Day04bTests {
    @Test fun testActualInput() {
        assertEquals("9962624", day04b(readInputLine("day04")))
    }
}
