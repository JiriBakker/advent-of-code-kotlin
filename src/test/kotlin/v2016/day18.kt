package v2016.days.day18

import org.junit.Assert.assertEquals
import org.junit.Test
import util.readInputLine

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(6, day18a("..^^.", 3))
    }

    @Test fun testExampleInput2() {
        assertEquals(38, day18a(".^^.^.^^^^", 10))
    }

    @Test fun testActualInput() {
        assertEquals(1951, day18a(readInputLine("day18", 2016)))
    }
}

class Day18bTests {
    @Test fun testActualInput() {
        assertEquals(20002936, day18b(readInputLine("day18", 2016)))
    }
}
