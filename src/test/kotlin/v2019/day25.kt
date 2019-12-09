package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day25.day25a

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day25aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day25a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day25a(readInputLines("day25")))
    }
}