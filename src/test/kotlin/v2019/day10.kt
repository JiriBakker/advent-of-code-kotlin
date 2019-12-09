package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day10.day10a
import v2019.days.day10.day10b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day10aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day10a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day10a(readInputLines("day10")))
    }
}

class Day10bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day10b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day10b(readInputLines("day10")))
    }
}