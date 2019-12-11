package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day17.day17a
import v2019.days.day17.day17b
import v2019.util.readInputLines

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day17aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day17a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day17a(readInputLines("day17")))
    }
}

class Day17bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day17b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day17b(readInputLines("day17")))
    }
}