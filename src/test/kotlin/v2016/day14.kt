package v2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import util.readInputLine

class Day14aTests {
    @Test fun testExampleInput1() {
        assertEquals(22728, day14a("abc"))
    }

    @Test fun testActualInput() {
        assertEquals(35186, day14a(readInputLine("day14", 2016)))
    }
}

class Day14bTests {
    @Disabled // Too slow
    @Test fun testExampleInput1() {
        assertEquals(22551, day14b("abc"))
    }

    @Disabled // Too slow
    @Test fun testActualInput() {
       assertEquals(22429, day14b(readInputLine("day14", 2016)))
    }
}
