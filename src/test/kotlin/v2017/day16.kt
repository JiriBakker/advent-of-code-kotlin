package v2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLine

class Day16aTests {
    @Test fun testExampleInput1() {
        assertEquals("baedc", day16a("s1,x3/4,pe/b", 5))
    }

    @Test fun testActualInput() {
        assertEquals("iabmedjhclofgknp", day16a(readInputLine("day16", 2017)))
    }
}

class Day16bTests {
    @Test fun testActualInput() {
        assertEquals("oildcmfeajhbpngk", day16b(readInputLine("day16", 2017)))
    }
}
