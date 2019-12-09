package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day11.day11a
import v2019.days.day11.day11b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day11aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day11a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day11a(readInputLines("day11")))
    }
}

class Day11bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day11b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day11b(readInputLines("day11")))
    }
}