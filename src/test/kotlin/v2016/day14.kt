package v2016.days.day14

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
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
    @Ignore // Too slow
    @Test fun testExampleInput1() {
        assertEquals(22551, day14b("abc"))
    }

    @Ignore // Too slow
    @Test fun testActualInput() {
       assertEquals(22429, day14b(readInputLine("day14", 2016)))
    }
}
