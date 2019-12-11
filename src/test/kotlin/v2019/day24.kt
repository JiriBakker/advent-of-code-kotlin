package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day24.day24a
import v2019.days.day24.day24b
import v2019.util.readInputLines

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(",")
}

class Day24aTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day24a(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day24a(readInputLines("day24")))
    }
}

class Day24bTests {
    @Test fun testExampleInput1() {
        assertEquals(0, day24b(parseCsv("")))
    }

    @Test fun testActualInput() {
        assertEquals(0, day24b(readInputLines("day24")))
    }
}