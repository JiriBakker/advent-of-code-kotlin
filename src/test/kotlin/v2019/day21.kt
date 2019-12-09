package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day21.day21a
import v2019.days.day21.day21b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day21aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day21a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day21a(readInputLines("day21")))
    }
}

class Day21bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day21b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day21b(readInputLines("day21")))
    }
}