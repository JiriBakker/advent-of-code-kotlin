package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day16.day16a
import v2019.days.day16.day16b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day16aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day16a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day16a(readInputLines("day16")))
    }
}

class Day16bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day16b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day16b(readInputLines("day16")))
    }
}