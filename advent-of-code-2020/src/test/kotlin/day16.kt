import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLines

class Day16aTests {
    @Test fun testExampleInput1() {
        assertEquals(71, day16a(listOf(
            "class: 1-3 or 5-7",
            "row: 6-11 or 33-44",
            "seat: 13-40 or 45-50",
            "",
            "your ticket:",
            "7,1,14",
            "",
            "nearby tickets:",
            "7,3,47",
            "40,4,50",
            "55,2,20",
            "38,6,12")))
    }

    @Test fun testActualInput() {
        assertEquals(21996, day16a(readInputLines("day16")))
    }
}

class Day16bTests {
     @Test fun testActualInput() {
        assertEquals(650080463519, day16b(readInputLines("day16")))
    }
}
