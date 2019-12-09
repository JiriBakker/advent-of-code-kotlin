package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day23.day23a
import v2019.days.day23.day23b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day23aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day23a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day23a(readInputLines("day23")))
    }
}

class Day23bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day23b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day23b(readInputLines("day23")))
    }
}