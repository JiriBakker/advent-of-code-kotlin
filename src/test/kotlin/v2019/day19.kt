package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day19.day19a
import v2019.days.day19.day19b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day19aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day19a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day19a(readInputLines("day19")))
    }
}

class Day19bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day19b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day19b(readInputLines("day19")))
    }
}