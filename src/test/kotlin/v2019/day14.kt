package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day14.day14a
import v2019.days.day14.day14b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day14aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day14a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day14a(readInputLines("day14")))
    }
}

class Day14bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day14b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day14b(readInputLines("day14")))
    }
}