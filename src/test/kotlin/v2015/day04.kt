package v2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day04aTests {
    @Test fun testExampleInput1() {
        assertEquals("609043", day04a("abcdef"))
    }

    @Test fun testExampleInput2() {
        assertEquals("1048970", day04a("pqrstuv"))
    }

    @Test fun testActualInput() {
        assertEquals("282749", day04a(readInputLine("day04", 2015)))
    }
}

class Day04bTests {
    @Test fun testActualInput() {
        assertEquals("9962624", day04b(readInputLine("day04", 2015)))
    }
}
