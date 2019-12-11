package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day12.day12a
import v2019.days.day12.day12b
import v2019.util.readInputLines

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day12aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day12a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day12a(readInputLines("day12")))
    }
}

class Day12bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day12b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day12b(readInputLines("day12")))
    }
}