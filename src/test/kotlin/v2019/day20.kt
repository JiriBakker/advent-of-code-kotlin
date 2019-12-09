package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day20.day20a
import v2019.days.day20.day20b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day20aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day20a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day20a(readInputLines("day20")))
    }
}

class Day20bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day20b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day20b(readInputLines("day20")))
    }
}