package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day13.day13a
import v2019.days.day13.day13b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day13aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day13a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day13a(readInputLines("day13")))
    }
}

class Day13bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day13b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day13b(readInputLines("day13")))
    }
}