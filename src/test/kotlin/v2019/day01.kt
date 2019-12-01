package v2019

import org.junit.Assert.assertEquals
import org.junit.Test
import v2019.days.day01a
import v2019.days.day01b

private fun parseCsv(exampleInput: String): List<String> {
    return exampleInput.split(", ")
}

class Day01aTests {

    @Test fun testExampleInput1() {
        assertEquals(2, day01a(parseCsv("12")))
    }

    @Test fun testExampleInput2() {
        assertEquals(2, day01a(parseCsv("14")))
    }

    @Test fun testExampleInput3() {
        assertEquals(654, day01a(parseCsv("1969")))
    }

    @Test fun testExampleInput4() {
        assertEquals(33583, day01a(parseCsv("100756")))
    }

    @Test fun testExampleCombinedInput() {
        assertEquals(34241, day01a(parseCsv("12, 14, 1969, 100756")))
    }

    @Test fun testActualInput() {
        assertEquals(3352674, day01a(readInputLines("day01")))
    }
}

class Day01bTests {

    @Test fun testExampleInput1() {
        assertEquals(2, day01b(parseCsv("12")))
    }

    @Test fun testExampleInput2() {
        assertEquals(966, day01b(parseCsv("1969")))
    }

    @Test fun testExampleInput4() {
        assertEquals(50346, day01b(parseCsv("100756")))
    }

    @Test fun testExampleCombinedInput() {
        assertEquals(51314, day01b(parseCsv("12, 1969, 100756")))
    }

    @Test fun testActualInput() {
        assertEquals(5026151, day01b(readInputLines("day01")))
    }
}
