package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day18.day18a
import v2019.days.day18.day18b
import v2019.util.readInputLines

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day18aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day18a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day18a(readInputLines("day18")))
    }
}

class Day18bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day18b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day18b(readInputLines("day18")))
    }
}